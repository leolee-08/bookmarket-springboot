package com.market.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialUserInfo userInfo = extractUserInfo(registrationId, oauth2User.getAttributes());
        validateEmail(userInfo.email());

        User user = userRepository.findByEmail(userInfo.email())
                .map(existingUser -> updateSocialUser(existingUser, registrationId, userInfo.name()))
                .orElseGet(() -> createSocialUser(registrationId, userInfo));

        return new AuthUserPrincipal(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole())),
                oauth2User.getAttributes()
        );
    }

    private SocialUserInfo extractUserInfo(String registrationId, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return new SocialUserInfo(
                    (String) attributes.get("email"),
                    normalizeName(attributes.get("name"), "Google 사용자")
            );
        }

        if ("naver".equals(registrationId)) {
            Object response = attributes.get("response");
            if (response instanceof Map<?, ?> responseMap) {
                return new SocialUserInfo(
                        (String) responseMap.get("email"),
                        normalizeName(responseMap.get("name"), "Naver 사용자")
                );
            }
        }

        throw new OAuth2AuthenticationException(new OAuth2Error("unsupported_provider"),
                "지원하지 않는 소셜 로그인입니다: " + registrationId);
    }

    private User createSocialUser(String provider, SocialUserInfo userInfo) {
        User user = new User();
        user.setEmail(userInfo.email());
        user.setPassword(passwordEncoder.encode(provider + "_oauth2_user"));
        user.setName(userInfo.name());
        user.setProvider(provider);

        return userRepository.save(user);
    }

    // 수정안
    private User updateSocialUser(User user, String provider, String name) {
        if (user.getProvider() == null) {
            // 자체 가입 유저가 소셜 로그인 시도 → 차단
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("email_already_exists"),
                    "이미 자체 가입된 이메일입니다. 일반 로그인을 이용해주세요."
            );
        }
        if (!user.getProvider().equals(provider)) {
            // 다른 소셜로 가입된 유저 → 차단
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("provider_mismatch"),
                    "이미 " + user.getProvider() + "로 가입된 계정입니다."
            );
        }
        if (name != null && !name.isBlank()) {
            user.setName(name);
        }
        return userRepository.save(user);
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException(new OAuth2Error("email_not_found"),
                    "소셜 계정에서 이메일을 가져올 수 없습니다.");
        }
    }

    private String normalizeName(Object value, String defaultName) {
        String name = Objects.toString(value, "");
        return name.isBlank() ? defaultName : name;
    }

    private record SocialUserInfo(String email, String name) {
    }
}
