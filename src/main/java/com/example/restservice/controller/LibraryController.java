package com.example.restservice.controller;

import com.example.restservice.entity.Library;
import com.example.restservice.repository.LibraryRepository;
import com.example.restservice.response.ApiResponse;
import com.example.restservice.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryService libraryService;

    public static final  Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @PostMapping(value = "/addBook")
    public ResponseEntity addBookImplementation(@RequestBody Library library){

             ApiResponse apiResponse  = new ApiResponse();
            String id = libraryService.buildId(library.getIsbn(),library.getAisle());
            if(!libraryService.checkBookAlreadyExist(id)){
                logger.info("Book does not exist so creating one");
                library.setId(id);
                libraryRepository.save(library);
                HttpHeaders headers = new HttpHeaders();
                headers.add("unique",id);
                apiResponse.setMsg("Success Book is added");
                apiResponse.setId(id);

                return new ResponseEntity<ApiResponse>(apiResponse,headers, HttpStatus.CREATED);
            }else{
                logger.info("Book exist so skipping creation");
                apiResponse.setMsg("Book already exist");
                apiResponse.setId(id);
                return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
            }


    }
    @GetMapping(value = "/getBooks/{id}")
    public Library getBookById(@PathVariable(value = "id") String id){
       try {
           return  libraryRepository.findById(id).get();
       }catch (Exception e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }

    }

    @GetMapping(value = "/getBooks/author")
    public List<Library> getBookByAuthor(@RequestParam(value = "authorname") String authorname){

        try {
            return libraryRepository.findAllByAuthor(authorname);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Library> updateBook(@PathVariable(value = "id") String id,@RequestBody Library library){
//        Library existingBook = libraryRepository.findById(id).get();
        Library existingBook = libraryService.getBookById(id);
        existingBook.setAisle(library.getAisle());
        existingBook.setAuthor(library.getAuthor());
        existingBook.setBook_name(library.getBook_name());

        libraryRepository.save(existingBook);
        return new ResponseEntity<Library>(existingBook,HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteBook")
    public ResponseEntity<String> deleteBookById(@RequestBody Library library){
//      Library lidelete  = libraryRepository.findById(library.getId()).get();
        Library lidelete  = libraryService.getBookById(library.getId());
      libraryRepository.delete(lidelete);
      logger.info("Book is delete");
      return new ResponseEntity<>("Book is deleted",HttpStatus.OK);

    }
}
