package com.psk.bank.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.psk.bank.model.User;

//@Ignore // uncoment to show how restTemplate works with localhost server
public class ExampleClientTest {

    private static String url;
    private static RestTemplate restTemplate = new RestTemplate();

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/";
    }

    @Test
    public void getWithPathVariableExampleShouldReturnUserWithId() {

        ResponseEntity<User> response = restTemplate.exchange(url + "getUserWithGivenId/{id}", HttpMethod.GET, null,
                User.class, 2);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getId()).isEqualTo(2);
    }

    @Test
    public void getWithPathVariableExampleShouldReturnUserWithDate() {

        ResponseEntity<User> response = restTemplate.getForEntity(url + "getUserWithGivenId/{id}", User.class, "2");

        response.getStatusCodeValue();
        response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getDate()).isEqualTo(LocalDateTime.parse("2017-03-02T21:32:00"));
    }

    @Test
    public void postMethodExampleShouldReturnString() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("id", "1");
        map.add("name", "NewUser");
        map.add("date", "2017-01-02T21:32:00");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url + "addUser", request, String.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("User added successfully");
    }

    @Test
    public void deleteWithPathVariableExampleShouldReturnDeletedUser() {
        ResponseEntity<User> response = restTemplate.exchange(url + "deleteUserWithGivenId/{id}", HttpMethod.DELETE,
                null, User.class, 1);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getDate()).isEqualTo(LocalDateTime.parse("2017-02-02T21:32:00"));
    }
    
    
    @Test
    public void getWithPathVariableExampleShouldReturnDeletedUser() {
        ResponseEntity<User> response = restTemplate.exchange(url + "getUserWithGivenId/{id}", HttpMethod.GET,
                null, User.class, "1");

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getDate()).isEqualTo(LocalDateTime.parse("2017-02-02T21:32:00"));
    }
    
    
    
/*    @Test
    public void givenConsumingXml_whenReadingTheFoo_thenCorrect() {
        restTemplate.setMessageConverters(getMessageConverters());
     
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
     
        ResponseEntity<User> response = restTemplate.exchange(url + "getUserWithGivenId/{id}", HttpMethod.GET,
                null, User.class, "1");
        User resource = response.getBody();
     
        assertThat(resource).isNotNull();
    }*/
    
    
    
	private List<HttpMessageConverter<?>> getMessageConverters() {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        MarshallingHttpMessageConverter marshallingConverter = 
          new MarshallingHttpMessageConverter(marshaller);
         
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        
        converters.add(marshallingConverter);
        return converters; 
    }
    
    
    @Test
    public void givenConsumingJson_whenReadingTheFoo_thenCorrect() {
        restTemplate.setMessageConverters(getMessageConvertersForJson());
     
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
     
        ResponseEntity<User> response = restTemplate.exchange(url + "getUserWithGivenId/{id}", HttpMethod.GET,
                null, User.class, "1");
        User resource = response.getBody();
     
        assertThat(resource).isNotNull();
    }
	
    private List<HttpMessageConverter<?>> getMessageConvertersForJson() {
        List<HttpMessageConverter<?>> converters = 
          new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        return converters;
    }
	
    
    

    @Test
    public void postMethodExampleShouldReturnStringUsingRealObject() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        User newUser = new User(4L, "newUser4", LocalDateTime.now());
        HttpEntity<User> request = new HttpEntity<User>(newUser, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url + "addUserWithRequestBody", request,
                String.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("User added successfully");
    }

}
