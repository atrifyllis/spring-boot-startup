package gr.alx.startup.config;

import gr.alx.startup.user.User;
import gr.alx.startup.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Add custom fields (i.e. email) inside JWT token
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2AccessToken enhance(
            OAuth2AccessToken accessToken,
            OAuth2Authentication authentication) {
        String authenticationName = authentication.getName();
        Optional<User> user = userRepository.findByUsername(authenticationName);
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("email", user.map(User::getEmail).orElseThrow(IllegalStateException::new));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
