package com.example.restservice.service;

import com.example.restservice.entity.Library;
import com.example.restservice.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public String buildId(String isbn,int aisle){
        if (isbn.startsWith("Z")){
            return "OLD"+isbn+aisle;
        }
        return isbn+aisle;
    }
    public boolean checkBookAlreadyExist(String id){
       Optional<Library> lib = libraryRepository.findById(id);
       if (lib.isPresent()){
           return true;
       }else{
           return false;
       }
    }
    public Library getBookById(String id){
        return libraryRepository.findById(id).get();
    }
}
