package com.example.InitialOne.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InitialOne.model.TrendingSong;
public interface TrendingSongRepository extends JpaRepository<TrendingSong, Long> {

}
