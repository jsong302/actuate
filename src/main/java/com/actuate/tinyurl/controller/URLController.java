package com.actuate.tinyurl.controller;


import com.actuate.tinyurl.exception.URLNotFoundException;
import com.actuate.tinyurl.model.URL;
import com.actuate.tinyurl.service.URLService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
class URLController {

    private final URLService urlService;

    public URLController(URLService urlService) {
        this.urlService = urlService;
    }


    @RequestMapping(value = "/url/{hash}", method = RequestMethod.GET)
    public String getUrls(Model model, @PathVariable("hash") String hash) {
        URL url;
        try{
            url = urlService.findOriginalURL(hash);
            model.addAttribute("url", url);
            final String baseUrl =
                    ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            model.addAttribute("baseUrl", baseUrl);
        } catch(URLNotFoundException e) {
            return "redirect:/error/urlnotfound";
        }
        return "url";
    }

    @RequestMapping(value = "/url", method = RequestMethod.POST)
    String newURL(Model model, @ModelAttribute URL url) {
        try {
            urlService.createURL(url);
        } catch(Exception e) {
            return "redirect:/error/notvalidurl";
        }
        return "redirect:/url/" + url.getHash();
    }

    @RequestMapping(value = "/error/urlnotfound", method = RequestMethod.GET)
    String urlNotFound(Model model, @ModelAttribute String error) {
        return "error/urlnotfound";
    }

    @RequestMapping(value = "/error/notvalidurl", method = RequestMethod.GET)
    String notValidUrl(Model model, @ModelAttribute String error) {
        return "error/notvalidurl";
    }


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("urls", urlService.getURLs());
        model.addAttribute("url", new URL());
        final String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        model.addAttribute("baseUrl", baseUrl);
        return "home";
    }

    @GetMapping(value = "/{hash}")
    public RedirectView redirect(Model model, @PathVariable("hash") String hash, RedirectAttributes attributes){

        URL url;
        RedirectView redirectView = new RedirectView();
        try{
            url = urlService.findOriginalURL(hash);
            redirectView.setUrl(url.getOriginalURL());
            urlService.increaseClickCount(url);
        } catch(URLNotFoundException e) {
            redirectView.setUrl("error/urlnotfound");
        }

        return redirectView;

    }
}