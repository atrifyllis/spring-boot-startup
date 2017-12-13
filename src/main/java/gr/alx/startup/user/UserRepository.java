package gr.alx.startup.user;

import gr.alx.startup.common.BaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface UserRepository extends BaseRepository<User, UUID> {

    /**
     * This method should be allowed to be called by an authenticated user (not admin) because it is used in JWT token enhancement!
     *
     * @param username
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    Optional<User> findByUsername(String username);
}
