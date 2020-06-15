package skbaek.shorteningurl.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import skbaek.shorteningurl.controller.ShorteningContorller;
import skbaek.shorteningurl.entity.ShorteningUrl;
import skbaek.shorteningurl.service.impl.ShorteningService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShorteningContorller.class)
public class ControllerTest {

    private final String TEST_URL = "https://github.com/100sg";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean
    private ShorteningService shorteningService;

    @BeforeEach()
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    private ShorteningUrl generateShorteningUrl() {
        ShorteningUrl.ShorteningUrlBuilder builder = ShorteningUrl.builder();
        builder.longUrl(TEST_URL);
        return builder.build();
    }

    @Test
    public void successShorteningUrlTest() throws Exception {
        ShorteningUrl result = generateShorteningUrl();
        given(shorteningService.encodingToUrl(result)).willReturn(result);

        final ResultActions actions = mockMvc
                .perform(post("/short/job")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"longUrl\":\"https://github.com/100sg\"}") )
                .andDo(print());

        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

}
