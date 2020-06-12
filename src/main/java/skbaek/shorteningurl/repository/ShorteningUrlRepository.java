package skbaek.shorteningurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skbaek.shorteningurl.entity.ShorteningUrl;

@Repository
public interface ShorteningUrlRepository extends JpaRepository<ShorteningUrl, Long>  {

    ShorteningUrl findByLongUrl(String data);
    ShorteningUrl findByShortUrl(String data);

}
