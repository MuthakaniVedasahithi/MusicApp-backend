package com.example.InitialOne.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InitialOne.model.Movie;
public interface MovieRepository extends JpaRepository<Movie, Long>{

}
