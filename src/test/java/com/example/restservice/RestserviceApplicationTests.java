package com.example.restservice;

import com.example.restservice.controller.LibraryController;
import com.example.restservice.entity.Library;
import com.example.restservice.repository.LibraryRepository;
import com.example.restservice.response.ApiResponse;
import com.example.restservice.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import static org.springframework.test.web.servlet.match.MockRestRequestMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class RestserviceApplicationTests {

	@Autowired
	LibraryController con;

	@MockBean
	LibraryRepository repository;

	@MockBean
	LibraryService libraryService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void checkBuildIDLogic(){
		LibraryService lib = new LibraryService();
		String id = lib.buildId("ZMAN",24);
		assertEquals(id,"OLDZMAN24");

		String id1 = lib.buildId("MAN",24);
		assertEquals(id1,"MAN24");
	}

	@Test
	public void addBookTest(){

		// mock
		Library lib = new Library();
		when(libraryService.buildId(lib.getIsbn(),lib.getAisle())).thenReturn(lib.getId());
//		when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(false);
		when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(true);

		ResponseEntity response = con.addBookImplementation(buildLibrary());
		System.out.println("response.getStatusCode()" + response.getStatusCode());
//		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
		ApiResponse ad  = (ApiResponse) response.getBody();

		assertEquals(lib.getId(),ad.getId());
		assertEquals("Book already exist",ad.getMsg());
	}

	@Test
	public void addBookControllerTest() throws Exception {

		// mock
		Library lib = new Library();
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(lib);

		when(libraryService.buildId(lib.getIsbn(),lib.getAisle())).thenReturn(lib.getId());
		when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(false);
		when(repository.save(any())).thenReturn(lib);
		this.mockMvc.perform(post("/addBook")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)).andExpect(status().isCreated())
				.andDo(print())
		.andExpect(jsonPath("$.id").value(lib.getId()));



	}

	@Test
	public void getBookByAuthorTest() throws Exception {
		List<Library> li  = new ArrayList<Library>();
		li.add(buildLibrary());
		li.add(buildLibrary());
		when(repository.findAllByAuthor(any())).thenReturn(li);
		this.mockMvc.perform(get("/getBooks/author")
		.param("authorname","Rahul Shetty"))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.length()",is(2)))
		.andExpect(jsonPath("$.[0].id").value("sft322"));
	}

	@Test
	public void UpdateBookTest() throws Exception {
		Library lib  = buildLibrary();
		ObjectMapper map = new ObjectMapper();
	    String jsonString = map.writeValueAsString(updateLibrary());

		when(libraryService.getBookById(any())).thenReturn(buildLibrary());
		this.mockMvc.perform(put("/updateBook/"+lib.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().json("{\"book_name\":\"Srping\",\"id\":\"sft322\",\"isbn\":\"sft\",\"aisle\":322,\"author\":\"Rashul Shetty\"}"))
		;
	}

	@Test
	public void deleteBookControllerTest() throws Exception {
		when(libraryService.getBookById(any())).thenReturn(buildLibrary());
		doNothing().when(repository).delete(buildLibrary());
		this.mockMvc.perform(
				delete("/deleteBook")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"generate2020\"}")

		).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string("Book is deleted"));
	}

	public Library buildLibrary(){
		Library lib = new Library();
		lib.setAisle(322);
		lib.setBook_name("Srping");
		lib.setIsbn("sft");
		lib.setAuthor("Rashul Shetty");
		lib.setId("sft322");
		return lib;
	}
	public Library updateLibrary(){
		Library lib = new Library();
		lib.setAisle(322);
		lib.setBook_name("Srping");
		lib.setIsbn("sft");
		lib.setAuthor("Rashul Shetty");
		lib.setId("sft322");
		return lib;
	}

}
