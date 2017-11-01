package gr.alx.startup.gr.alx.startup.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toSet;

// TODO: Tried to use the @org.springframework.data.rest.webmvc.BasePathAwareController but ti wouldn't work
@RestController
public class AuthenticatedUserResource {

    @GetMapping(value = "/api/auth/me")
    public User getAuthenticatedUser(Authentication authentication) {
        // Retrieve also email somehow?
        return User.builder()
                .username(authentication.getName())
                .roles(authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(Role::valueOf)
                        .collect(toSet())
                )
                .build();
    }
}