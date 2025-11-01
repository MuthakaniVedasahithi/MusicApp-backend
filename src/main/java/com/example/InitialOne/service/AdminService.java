package com.example.InitialOne.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.InitialOne.model.*;
import com.example.InitialOne.repository.*;
import java.util.*;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired private ArtistRepository artistRepo;
    @Autowired private SongRepository songRepo;
    @Autowired private TrendingSongRepository songTrendingRepo;
    @Autowired private MovieRepository movieRepo;

    // ✅ Artist
    public Artist addArtist(Artist artist) {
        return artistRepo.save(artist);
    }

    public List<Artist> getAllArtists() {
        System.out.println("Fetching artists...");
        List<Artist> artists = artistRepo.findAll();
        System.out.println("Found " + artists.size() + " artists");

        for (Artist artist : artists) {
            // ✅ First, encode image if present
            if (artist.getImage() != null) {
                artist.setImageBase64(Base64.getEncoder().encodeToString(artist.getImage()));
            }

            // ✅ Then, encode each song before clearing bytes
            if (artist.getSongs() != null) {
                for (Song song : artist.getSongs()) {
                    if (song.getData() != null) {
                        song.setDataBase64(Base64.getEncoder().encodeToString(song.getData()));
                    }
                }
            }

            // ✅ Finally, clear raw byte fields to avoid large payloads
            artist.setImage(null);
            if (artist.getSongs() != null) {
                for (Song song : artist.getSongs()) {
                    song.setData(null);
                }
            }
        }


        return artists;
    }



    // ✅ Trending Songs
    public TrendingSong addTrendingSong(TrendingSong song) {
        return songTrendingRepo.save(song);
    }

    public List<TrendingSong> getAllTrendingSongs() {
        return songTrendingRepo.findAll();
    }

    // ✅ Movies
    public Movie addMovie(Movie movie) {
        return movieRepo.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }
}
