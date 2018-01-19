package com.psk.bank.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.psk.bank.model.Account;
import com.psk.bank.model.AccountEntity;
import com.psk.bank.model.User;

//@Ignore // uncoment to show how restTemplate works with localhost server
public class AccountControllerTest {

    private static String url;
    private static RestTemplate restTemplate = new RestTemplate();

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/";
    }

    @Test
    public void shouldGetAccount() {

        ResponseEntity<AccountEntity> response = restTemplate.getForEntity(url + "getAccount/{id}", AccountEntity.class, "ABC1");

        response.getStatusCodeValue();
        response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getAccountNumber()).isEqualTo("ABC1");
    }
    
    @Test
    public void shouldPostAccount() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("accountNumber", "TEST3");
        map.add("firstName", "Patryk");
        map.add("lastName", "Kowalski");
        map.add("active", "true");
        map.add("creationTs", "2017-01-02T21:32:00");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url + "addAccount", request, String.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("dodano");
    }

    @Test
    public void deleteWithPathVariableExampleShouldReturnDeletedUser() {
        ResponseEntity<AccountEntity> response = restTemplate.exchange(url + "deleteAccount/{id}", HttpMethod.DELETE,
                null, AccountEntity.class, "ABC1");

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getAccountNumber()).isEqualTo("ABC1");
    }

}

