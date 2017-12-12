package gr.alx.startup.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.io.Serializable;

/**
 * Custom repository which is used to extend the rest repos with methods to retrieve all enabled records (not the soft deleted ones)
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {


    Page findByEnabledTrue(Pageable p);


    @RestResource(path = "findAllEnabled", rel = "findAllEnabled")
    Iterable<T> findByEnabledTrue();
}
