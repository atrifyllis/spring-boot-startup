package gr.alx.startup.config;

import gr.alx.startup.user.User;
import gr.alx.startup.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomTokenEnhancerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    OAuth2Authentication authentication;

    @InjectMocks
    CustomTokenEnhancer customTokenEnhancer;

    @Before
    public void setUp() {
        when(authentication.getName()).thenReturn("admin");
        User user = new User("1", "admin@admin.com", "", "", new HashSet<>());
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
    }

    @Test
    public void shouldAddEmailOfValidUser() {
        OAuth2AccessToken oAuth2AccessToken = DefaultOAuth2AccessToken.valueOf(new HashMap<>());

        OAuth2AccessToken enhancedToken = customTokenEnhancer.enhance(oAuth2AccessToken, authentication);

        assertThat(enhancedToken.getAdditionalInformation().get("email")).isEqualTo("admin@admin.com");
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowForInvalidUser() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.ofNullable(null));
        OAuth2AccessToken oAuth2AccessToken = DefaultOAuth2AccessToken.valueOf(new HashMap<>());

        customTokenEnhancer.enhance(oAuth2AccessToken, authentication);
    }
}