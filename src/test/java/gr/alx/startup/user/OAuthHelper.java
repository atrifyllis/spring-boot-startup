package gr.alx.startup.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.alx.startup.config.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Component
public class OAuthHelper {
    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    DefaultTokenServices tokenservice;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    @Qualifier("accessTokenConverter")
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private CustomTokenEnhancer customTokenEnhancer;

    // For use with MockMvc
    public RequestPostProcessor bearerToken(final String clientid, final User user) {
        return mockRequest -> {
            OAuth2AccessToken token = createAccessToken(clientid, user);
            mockRequest.addHeader("Authorization", "Bearer " + token.getValue());
            return mockRequest;
        };
    }

    private OAuth2AccessToken createAccessToken(final String clientId, User user) {
        // Look up authorities, resourceIds and scopes based on clientId and user
        ClientDetails client = clientDetailsService.loadClientByClientId(clientId);
        Collection<GrantedAuthority> authorities = retrieveAuthoritiesFromUser(user);
        Set<String> resourceIds = client.getResourceIds();
        Set<String> scopes = client.getScope();

        // Default values for other parameters
        Map<String, String> requestParameters = Collections.emptyMap();
        boolean approved = true;
        String redirectUrl = null;
        Set<String> responseTypes = Collections.emptySet();
        Map<String, Serializable> extensionProperties = Collections.emptyMap();

        // Create request
        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId, authorities, approved, scopes,
                resourceIds, redirectUrl, responseTypes, extensionProperties);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);
        OAuth2AccessToken oAuth2AccessToken = tokenservice.createAccessToken(auth);
        // TODO: enhance token automatically?
        OAuth2AccessToken jwtToken = jwtAccessTokenConverter.enhance(oAuth2AccessToken, auth);
        return customTokenEnhancer.enhance(jwtToken, auth);
    }

    private Collection<GrantedAuthority> retrieveAuthoritiesFromUser(User user) {
        String[] roles = user.getRoles().stream().map(Enum::name).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(roles);
    }

    /**
     * Clone user using Jackson
     *
     * @param user the User object to be cloned
     * @return the cloned User
     * @throws java.io.IOException in case of serialization error
     */
    public User cloneUser(User user) throws java.io.IOException {
        String stringUser = objectMapper.writeValueAsString(user);
        return objectMapper.readValue(stringUser, User.class);
    }

    public String serializeObject(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
