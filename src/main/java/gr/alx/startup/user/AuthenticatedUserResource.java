package gr.alx.startup.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.util.stream.Collectors.toSet;

// TODO: Tried to use the @org.springframework.data.rest.webmvc.BasePathAwareController but ti wouldn't work
@RestController
public class AuthenticatedUserResource {

    @GetMapping(value = "/api/auth/me")
    public ResponseEntity<Resource<User>> getAuthenticatedUser(Authentication authentication) {
        // Retrieve also email somehow?
        User user = User.builder()
                .username(authentication.getName())
                .roles(authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(Role::valueOf)
                        .collect(toSet())
                )
                .build();
        return ResponseEntity.ok(getUserResource(user));
    }

    private Resource<User> getUserResource(User user) {
        Resource<User> userResource = new Resource<>(user);
        URI self = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        userResource.add(new Link(self.toString(), "self"));
        return userResource;
    }
}