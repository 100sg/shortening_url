package skbaek.shorteningurl.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import skbaek.shorteningurl.entity.ShorteningUrl;
import skbaek.shorteningurl.repository.ShorteningUrlRepository;
import skbaek.shorteningurl.service.impl.ShorteningService;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        properties = {"longUrl=https://github.com/100sg"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@Transactional
class ShorteningUrlApplicationTests {

    @Value("${longUrl}")
    private String longUrl;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

//    @MockBean
//    private ShorteningUrlRepository shorteningUrlRepository;

    @Autowired
    private ShorteningService shorteningService;

    @Autowired
    private WebApplicationContext ctx;

    private ShorteningUrl generateShorteningUrl() {
        ShorteningUrl.ShorteningUrlBuilder builder = ShorteningUrl.builder();
        builder.longUrl(longUrl);
        return builder.build();
    }

    @BeforeEach()
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    public void getShorteningUrl() throws Exception {
        ShorteningUrl tmpData = generateShorteningUrl();
//        shorteningUrlRepository.save(tmpData);

        mockMvc.perform(
                post("/short/job")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"longUrl\":\"https://github.com/100sg\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<ShorteningUrl> request = new HttpEntity<>(tmpData, headers);

        ResponseEntity<ShorteningUrl> response = restTemplate.postForEntity("/short/job", request, ShorteningUrl.class);
        System.out.println("object check : " + response.getBody());
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isNotNull();

        ShorteningUrl shorteningUrl = shorteningService.detail(1L);
        System.out.println("entity : " + shorteningUrl.toString());
//        if(shorteningUrl.isPresent()){
//            then("B").isEqualTo(shorteningUrl.get().getShortUrl());
//            then(longUrl).isEqualTo(shorteningUrl.get().getLongUrl());
//        }

    }

}
