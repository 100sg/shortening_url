package skbaek.shorteningurl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import skbaek.shorteningurl.entity.ShorteningUrl;
import skbaek.shorteningurl.service.impl.ShorteningService;
import skbaek.shorteningurl.vo.ApiResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


@Slf4j
@RestController
public class ShorteningContorller {

    private ShorteningService shorteningService;

    public ShorteningContorller(ShorteningService service){
        this.shorteningService = service;
    }

    @PostMapping("/short/job")
    public ResponseEntity getShorteningUrl(@RequestBody ShorteningUrl param) {
        ApiResponse<ShorteningUrl> res = new ApiResponse<>();

        log.info("entity : {}", param.toString());
        try {
            res.setData(shorteningService.encodingToUrl(param));
        } catch (IOException e) {
            e.printStackTrace();
            res.setResult("FAIL");
            res.setStatus("404");
        } catch(Exception e) {
            e.printStackTrace();
            res.setResult("FAIL");
            res.setStatus(e.getMessage());
        }
        log.info("controller check : {}",res);
        return ResponseEntity.ok(res);
    }

    @GetMapping("{shortUrl}")
    public RedirectView moveToLink(@PathVariable String shortUrl) throws Exception {
        String HTTP = "http://";
        ShorteningUrl shorteningUrl = null;
        RedirectView redirectView = new RedirectView();
        shorteningUrl = shorteningService.decodeToUrl(shortUrl);
        redirectView.setUrl(HTTP.concat(shorteningUrl.getLongUrl()));
        return redirectView;
    }

}
