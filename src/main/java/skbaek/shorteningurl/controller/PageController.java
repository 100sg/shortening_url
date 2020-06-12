package skbaek.shorteningurl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
import skbaek.shorteningurl.entity.ShorteningUrl;
import skbaek.shorteningurl.service.impl.ShorteningService;

import java.net.URI;

@Slf4j
@Controller
public class PageController {

    private ShorteningService shorteningService;

    public PageController(ShorteningService service){
        this.shorteningService = service;
    }

    @GetMapping(value = "/main")
    public String main(Model model){
        return "index";
    }

    @GetMapping(value = "/error")
    public String error(Model model){
        return "error";
    }

}
