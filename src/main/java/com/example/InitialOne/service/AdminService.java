package com.example.InitialOne.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.InitialOne.model.*;
import com.example.InitialOne.repository.*;
import java.util.*;
import java.util.Base64;

@Service
public class AdminService {

    @Autowired private ArtistRepository artistRepo;
    @Autowired private SongRepository songRepo;
    @Autowired private TrendingSongRepository songTrendingRepo;
    @Autowired private MovieRepository movieRepo;
    @Autowired private MovieSongRepository movieSongRepo;

    // ✅ Add Artist
    public Artist addArtist(Artist artist) {
        return artistRepo.save(artist);
    }

    // ✅ Find artist by name
    public Artist getArtistByName(String name) {
        return artistRepo.findByName(name).orElse(null);
    }

    // ✅ Add new songs to existing artist
    public Artist addSongsToExistingArtist(Artist artist, List<Song> newSongs) {
        if (artist.getSongs() == null) {
            artist.setSongs(new ArrayList<>());
        }
        artist.getSongs().addAll(newSongs);
        return artistRepo.save(artist);
    }

    // ✅ Get all artists (with Base64 conversion)
    public List<Artist> getAllArtists() {
        List<Artist> artists = artistRepo.findAll();

        for (Artist artist : artists) {
            if (artist.getImage() != null) {
                artist.setImageBase64(Base64.getEncoder().encodeToString(artist.getImage()));
            }

            if (artist.getSongs() != null) {
                for (Song song : artist.getSongs()) {
                    if (song.getData() != null) {
                        song.setDataBase64(Base64.getEncoder().encodeToString(song.getData()));
                        song.setData(null);
                    }
                }
            }

            artist.setImage(null);
        }

        return artists;
    }

    // ✅ Add Movie with multiple songs
    public Movie addMovieWithSongs(Movie movie) {
        return movieRepo.save(movie);
    }

    // ✅ Get all movies (Base64 encoding for frontend display)
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieRepo.findAll();

        for (Movie movie : movies) {
            // Encode poster image
            if (movie.getPosterImage() != null) {
                movie.setImageBase64(Base64.getEncoder().encodeToString(movie.getPosterImage()));
            }

            // Encode each song’s file data
            if (movie.getSongs() != null) {
                movie.getSongs().forEach(song -> {
                    if (song.getFileData() != null) {
                        song.setDataBase64(Base64.getEncoder().encodeToString(song.getFileData()));
                        song.setFileData(null);
                    }
                });
            }

            movie.setPosterImage(null);
        }

        return movies;
    }
 // ✅ Get movies by category
    public List<Movie> getMoviesByCategory(String category) {
        List<Movie> movies = movieRepo.findByCategory(category);

        for (Movie movie : movies) {
            if (movie.getPosterImage() != null) {
                movie.setImageBase64(Base64.getEncoder().encodeToString(movie.getPosterImage()));
            }

            if (movie.getSongs() != null) {
                movie.getSongs().forEach(song -> {
                    if (song.getFileData() != null) {
                        song.setDataBase64(Base64.getEncoder().encodeToString(song.getFileData()));
                        song.setFileData(null);
                    }
                });
            }

            movie.setPosterImage(null);
        }

        return movies;
    }
    public Movie getMovieById(Long id) {
        Movie movie = movieRepo.findById(id).orElse(null);
        if (movie == null) return null;

        // Convert poster to Base64
        if (movie.getPosterImage() != null) {
            movie.setImageBase64(Base64.getEncoder().encodeToString(movie.getPosterImage()));
            movie.setPosterImage(null);
        }

        // Convert each song file to Base64
        if (movie.getSongs() != null) {
            movie.getSongs().forEach(song -> {
                if (song.getFileData() != null) {
                    song.setDataBase64(Base64.getEncoder().encodeToString(song.getFileData()));
                    song.setFileData(null);
                }
            });
        }

        return movie;
    }



    // ✅ Trending Songs
    public TrendingSong addTrendingSong(TrendingSong song) {
        return songTrendingRepo.save(song);
    }

    public List<TrendingSong> getAllTrendingSongs() {
        return songTrendingRepo.findAll();
    }

    // ✅ Add simple movie (used by older endpoint if needed)
    public Movie addMovie(Movie movie) {
        return movieRepo.save(movie);
    }
}
