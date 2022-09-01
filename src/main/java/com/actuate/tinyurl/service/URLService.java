package com.actuate.tinyurl.service;

import com.actuate.tinyurl.exception.URLNotFoundException;
import com.actuate.tinyurl.model.URL;
import com.actuate.tinyurl.repository.URLRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class URLService {
    private final URLRepository urlRepository;

    public URLService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public List<URL> getURLs() {
        return urlRepository.findAll();
    }

    public URL createURL(URL url) throws Exception{
        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if(!urlValidator.isValid(url.getOriginalURL())) {
            throw new Exception("URL is not valid");
        }
        return urlRepository.save(url);
    }

    public URL findOriginalURL(String hash) throws URLNotFoundException{
        return urlRepository.findById(hash)
                .orElseThrow(() -> new URLNotFoundException(hash));
    }

    public void increaseClickCount(URL url) {
        url.increaseClickCount();
        urlRepository.save(url);
    }
}
