package com.shenjiahuan.wordladder.login;

import com.alibaba.fastjson.JSON;
import org.codehaus.jackson.JsonNode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ApiController {
    @Value("${wordladder.ip}")
    private String wordLadderIP;

    @Value("${wordladder.port}")
    private String wordladderPort;

    @RequestMapping("/wordladders")
    public Result callWordLadder(@RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + wordLadderIP + ":" + wordladderPort + "/wordladders";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("from", from)
                .queryParam("to", to);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        Result response = restTemplate.getForObject(
                builder.toUriString(),
                Result.class);
        return response;
    }
}
