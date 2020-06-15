package skbaek.shorteningurl.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import skbaek.shorteningurl.controller.ShorteningContorller;
import skbaek.shorteningurl.entity.ShorteningUrl;
import skbaek.shorteningurl.repository.ShorteningUrlRepository;
import skbaek.shorteningurl.service.impl.ShorteningService;
import skbaek.shorteningurl.util.Base62;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ServiceTest {

    @MockBean
    private ShorteningUrlRepository repository;

    private final String TEST_URL = "https://github.com/100sg";
    private String BASE_URL = "http://localhost:8080/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ShorteningService shortenUrlService;

    @Autowired
    private ShorteningContorller redirectController;

    @Autowired
    private ShorteningUrlRepository shortenUrlRepository;

    private ShorteningUrl generateShorteningUrl(){
        ShorteningUrl.ShorteningUrlBuilder builder = ShorteningUrl.builder();
        builder.longUrl(TEST_URL);
        builder.shortUrl(Base62.encodeToLong(10000L));
        return builder.build();
    }

    @Test
    public void failShorteningUrlNotExist() {
        System.out.println("TestRestTemplate : " + BASE_URL.concat("1234"));
        assertThat(restTemplate.getForObject(BASE_URL.concat("1234"), String.class)); //, containsString("<h1>Url not found</h1>"));
    }

    @Test
    public void successShorteningUrlExist() throws Exception {
        ShorteningUrl shorteningUrl = generateShorteningUrl();
        shortenUrlRepository.save(shorteningUrl);

        ShorteningUrl result = shortenUrlRepository.findByShortUrl(Base62.encodeToLong(10000L));

        assertThat(redirectController.moveToLink(result.getShortUrl()).getUrl().endsWith("redirect:" + TEST_URL)).toString();
    }
}
