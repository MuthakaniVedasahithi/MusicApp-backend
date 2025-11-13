package com.example.InitialOne.controller;

import com.example.InitialOne.model.*;
import com.example.InitialOne.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ✅ Add Artist (with multiple songs)
    @PostMapping("/addArtistWithSongs")
    public Artist addArtistWithSongs(
            @RequestParam String name,
            @RequestParam MultipartFile image,
            @RequestParam("songTitles") List<String> songTitles,
            @RequestParam("songTypes") List<String> songTypes,
            @RequestParam("songFiles") List<MultipartFile> songFiles
    ) throws IOException {

        Artist artist = new Artist();
        artist.setName(name);
        artist.setImage(image.getBytes());

        List<Song> songs = new ArrayList<>();
        for (int i = 0; i < songFiles.size(); i++) {
            Song song = new Song();
            song.setTitle(songTitles.get(i));
            song.setType(songTypes.get(i));
            song.setData(songFiles.get(i).getBytes());
            song.setArtist(artist);
            songs.add(song);
        }

        artist.setSongs(songs);
        return adminService.addArtist(artist);
    }

    // ✅ Get artist names (for dropdowns)
    @GetMapping("/artists-names")
    public List<String> getArtistNames() {
        return adminService.getAllArtists()
                .stream()
                .map(Artist::getName)
                .toList();
    }

    // ✅ Add Trending Song
    @PostMapping("/addTrendingSong")
    public TrendingSong addTrendingSong(
            @RequestParam String title,
            @RequestParam String artistName,
            @RequestParam String type,
            @RequestParam MultipartFile file
    ) throws IOException {
        TrendingSong song = new TrendingSong();
        song.setTitle(title);
        song.setArtistName(artistName);
        song.setType(type);
        song.setFileData(file.getBytes());
        return adminService.addTrendingSong(song);
    }

    // ✅ Get all artists
    @GetMapping("/artists")
    public List<Artist> getAllArtists() {
        System.out.println("🎵 [AdminController] Fetching all artists...");
        List<Artist> artists = adminService.getAllArtists();
        System.out.println("✅ [AdminController] Found " + artists.size() + " artists in DB.");
        for (Artist a : artists) {
            System.out.println("   → Artist: " + a.getName());
        }
        return artists;
    }

    @GetMapping("/test")
    public String test() {
        return "✅ Backend is running correctly on port 7076";
    }

    // ✅ Get all trending songs
    @GetMapping("/songs")
    public List<TrendingSong> getAllSongs() {
        return adminService.getAllTrendingSongs();
    }

    // ✅ Add new songs to an existing artist
    @PostMapping("/addSongsToArtist")
    public Artist addSongsToExistingArtist(
            @RequestParam String artistName,
            @RequestParam("songTitles") List<String> songTitles,
            @RequestParam("songTypes") List<String> songTypes,
            @RequestParam("songFiles") List<MultipartFile> songFiles
    ) throws IOException {

        Artist artist = adminService.getArtistByName(artistName);
        if (artist == null) {
            throw new RuntimeException("Artist not found: " + artistName);
        }

        List<Song> newSongs = new ArrayList<>();
        for (int i = 0; i < songFiles.size(); i++) {
            Song song = new Song();
            song.setTitle(songTitles.get(i));
            song.setType(songTypes.get(i));
            song.setData(songFiles.get(i).getBytes());
            song.setArtist(artist);
            newSongs.add(song);
        }

        return adminService.addSongsToExistingArtist(artist, newSongs);
    }

    // ✅ Add Movie with multiple songs
    @PostMapping("/addMovieWithSongs")
    public Movie addMovieWithSongs(
            @RequestParam String title,
            @RequestParam String director,
            @RequestParam String category,
            @RequestParam MultipartFile poster,
            @RequestParam("songTitles") List<String> songTitles,
            @RequestParam("singers") List<String> singers,
            @RequestParam("songTypes") List<String> songTypes,
            @RequestParam("durations") List<String> durations,
            @RequestParam("songFiles") List<MultipartFile> songFiles
    ) throws IOException {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDirector(director);
        movie.setCategory(category);
        movie.setPosterImage(poster.getBytes());

        List<MovieSong> songs = new ArrayList<>();
        for (int i = 0; i < songFiles.size(); i++) {
            MovieSong song = new MovieSong();
            song.setTitle(songTitles.get(i));
            song.setSinger(singers.get(i));
            song.setType(songTypes.get(i));
            song.setDuration(durations.get(i));
            song.setFileData(songFiles.get(i).getBytes());
            song.setMovie(movie);
            songs.add(song);
        }

        movie.setSongs(songs);
        return adminService.addMovieWithSongs(movie);
    }

    // ✅ Get all movies (single definition)
    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return adminService.getAllMovies();
    }

    // ✅ Optional: Get movies by category (for frontend filtering)
    @GetMapping("/movies/{category}")
    public List<Movie> getMoviesByCategory(@PathVariable String category) {
        return adminService.getMoviesByCategory(category);
    }
 // ✅ Get a single movie by ID (with its songs)
    @GetMapping("/movie/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return adminService.getMovieById(id);
    }

}
