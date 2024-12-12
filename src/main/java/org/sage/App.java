package org.sage;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";

        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        System.out.println(result);
        System.out.println("_____________________________________");

        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_JSON);
        headers1.add("Cookie", result.getHeaders().get("Set-Cookie").get(0));
        System.out.println(headers1);

        System.out.println("__________________________");

        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 21);
        HttpEntity<User> entity = new HttpEntity<>(user, headers1);
        String stringResponseEntity = restTemplate.postForObject(url, entity, String.class);
        System.out.println(stringResponseEntity);

       user.setName("Thomas");
       user.setLastName("Shelby");

       entity = new HttpEntity<>(user, headers1);

        ResponseEntity<String> exchange1 = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        System.out.println(exchange1.getBody());

        ResponseEntity<String> exchange2 = restTemplate.exchange(url + "/{id}",
                HttpMethod.DELETE, entity, String.class, user.getId());
        System.out.println(exchange2.getBody());
    }
}
