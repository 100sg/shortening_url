package skbaek.shorteningurl.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import skbaek.shorteningurl.entity.ShorteningUrl;
import skbaek.shorteningurl.util.Base62;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class ShorteningUrlTest {

    @Autowired
    EntityManager em;

    @Test
    public void getId(){

        ShorteningUrl su = new ShorteningUrl();
        su.setLongUrl("http://www.daum.net");
        su.setShortUrl(Base62.encodeToLong(10000L));

        System.out.println("ShorteningUrl : " + su.toString());
        em.persist(su);

        Assertions.assertThat(su);

    }
}
