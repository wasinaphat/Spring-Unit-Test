package com.example.restservice.repository;

import com.example.restservice.entity.Library;

import java.util.List;

public interface LibraryRepositoryCustom {

    List<Library> findAllByAuthor(String authorName);
}
