package skbaek.shorteningurl.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import skbaek.shorteningurl.entity.ShorteningUrl;
import skbaek.shorteningurl.repository.ShorteningUrlRepository;
import skbaek.shorteningurl.util.Base62;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class RepositoryTest {

    @Autowired
    ShorteningUrlRepository shorteningUrlRepository;

    private ShorteningUrl generateShorteningUrl(){
        String testUrl = "https://github.com/100sg";
        ShorteningUrl.ShorteningUrlBuilder builder = ShorteningUrl.builder();
        builder.longUrl(testUrl);
        builder.shortUrl(Base62.encodeToLong(10000L));
        return builder.build();
    }

    @Test
    public void findByLongUrl(){
        ShorteningUrl generateEntity = generateShorteningUrl();
        shorteningUrlRepository.save(generateEntity);

        ShorteningUrl result = shorteningUrlRepository.findByLongUrl(generateEntity.getLongUrl());

        assertEquals(result.getShortUrl(), generateEntity.getShortUrl());
    }

    @Test
    public void successEncodeToDecodeEqualsCheck() {

        assertEquals(10000L, Base62.decodeToString("SlC"));
        assertEquals("SlC", Base62.encodeToLong(10000L));
    }


}
