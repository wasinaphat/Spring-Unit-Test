package com.example.restservice;

import com.example.restservice.entity.Library;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest
public class testsIT {

    // mvn test
    // TestRestTemplate Rest Assured

    @Test
    public void getAuthorNameBooksTest() throws JSONException {

        String expected = "[\n" +
                "    {\n" +
                "        \"book_name\": \"DevOps\",\n" +
                "        \"id\": \"123456\",\n" +
                "        \"isbn\": \"202107\",\n" +
                "        \"aisle\": 123,\n" +
                "        \"author\": \"wasinapl\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"book_name\": \"Spring\",\n" +
                "        \"id\": \"123457\",\n" +
                "        \"isbn\": \"202107\",\n" +
                "        \"aisle\": 123,\n" +
                "        \"author\": \"wasinapl\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"book_name\": \"NodeJS\",\n" +
                "        \"id\": \"123458\",\n" +
                "        \"isbn\": \"202106\",\n" +
                "        \"aisle\": 123,\n" +
                "        \"author\": \"wasinapl\"\n" +
                "    }\n" +
                "]";

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getBooks/author?authorname=wasinapl",String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        JSONAssert.assertEquals(expected,response.getBody(),false);


    }

    @Test
    public  void addBookIntegrationTest(){
        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders headers  = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Library> request = new HttpEntity<Library>(buildLibrary(),headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/addBook",request,String.class);

        Assert.assertEquals(HttpStatus.CREATED,response.getStatusCode());

        Assert.assertEquals(buildLibrary().getId(),response.getHeaders().get("unique").get(0));




    }
    public Library buildLibrary(){
        Library lib = new Library();
        lib.setAisle(322);
        lib.setBook_name("Srping");
        lib.setIsbn("sft");
        lib.setAuthor("wasinapl");
        lib.setId("sft322");
        return lib;
    }


}
