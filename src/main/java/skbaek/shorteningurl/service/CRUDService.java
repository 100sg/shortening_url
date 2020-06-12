package skbaek.shorteningurl.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CRUDService<T> {

    T save(T data) throws Exception;

    T detail(long id) throws Exception;

    void del(long id) throws Exception;

}
