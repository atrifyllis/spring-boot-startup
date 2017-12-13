package gr.alx.startup.user;

import gr.alx.startup.common.BaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface UserRepository extends BaseRepository<User, UUID> {

    Optional<User> findByUsername(String username);
}
