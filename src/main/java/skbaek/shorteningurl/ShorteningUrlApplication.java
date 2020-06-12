package skbaek.shorteningurl;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.scan.UrlJar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import skbaek.shorteningurl.util.Base62;

import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootApplication
public class ShorteningUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShorteningUrlApplication.class, args);

    }

}
