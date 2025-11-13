package com.example.InitialOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.InitialOne.model.Artist;
@Repository

public interface ArtistRepository extends JpaRepository<Artist, Long>{
    Optional<Artist> findByName(String name);

}
