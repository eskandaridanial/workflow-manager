package com.workflow.util;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class HttpUtil {

    public static Map<String , Object> matchCommonParams(Map<String , Object> map1 , Map<String , Object> map2){
        TreeSet<String> keySet1 = new TreeSet<>(map1.keySet());
        TreeSet<String> keySet2 = new TreeSet<>(map2.keySet());
        keySet1.retainAll(keySet2);
        List<String> result = new ArrayList<>();
        result.addAll(keySet1);
        for (int i = 0 ; i < result.size() ; i++){
            String common = result.get(i);
            map2.put(common , map1.get(common));
        }
        return map2;
    }

    public static Map<String , Object> post(Map<String , Object> params , String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getDefaultHeaders();
        HttpEntity httpRequest = new HttpEntity(params , httpHeaders);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url , HttpMethod.POST, httpRequest, Map.class);
        return responseEntity.getBody();
    }

    public static Map<String , String> get(Map<String , Object> params , String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getDefaultHeaders();
        HttpEntity httpRequest = new HttpEntity(params , httpHeaders);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET , httpRequest, Map.class);
        return responseEntity.getBody();
    }

    private static HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setConnection("keep-alive");
        headers.setPragma("no-cache");
        headers.setCacheControl("no-cache");
        ArrayList<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON);
        headers.setAccept(accept);
        headers.add("Set-Fetch-Site", "same-origin");
        headers.add("Set-Fetch-Mode", "cors");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        return headers;
    }
}
