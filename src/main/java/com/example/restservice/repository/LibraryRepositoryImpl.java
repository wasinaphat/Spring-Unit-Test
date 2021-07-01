package com.example.restservice.repository;

import com.example.restservice.entity.Library;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LibraryRepositoryImpl implements LibraryRepositoryCustom{

    @Autowired
    LibraryRepository repository;

    @Override
    public List<Library> findAllByAuthor (String authorName) {
        List<Library> bookswithAuthor =new ArrayList<Library>();

       List<Library> books =  repository.findAll();
        for (Library item : books) {
            if(item.getAuthor().equalsIgnoreCase(authorName)){
                bookswithAuthor.add(item);
            }
        }

        return bookswithAuthor;
    }
}
