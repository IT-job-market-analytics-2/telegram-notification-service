package by.asckarugin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RateLimiterService {

    private final RestTemplate template = new RestTemplate();

    @Value("${quota.url}")
    private String url;

    public void requestFromQuota(){

        ResponseEntity<String> responseEntity = template.getForEntity(url, String.class);

        if(responseEntity.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("return "+responseEntity.getStatusCode()+" status code");
        }
    }
}
