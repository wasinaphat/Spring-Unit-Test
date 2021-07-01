package com.example.restservice.repository;

import com.example.restservice.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library,String>,LibraryRepositoryCustom {
//    List<Library> findAllByAuthor(String authorName);
}
