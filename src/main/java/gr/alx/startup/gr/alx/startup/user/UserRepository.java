package gr.alx.startup.gr.alx.startup.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
}
