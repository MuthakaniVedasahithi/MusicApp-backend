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



    // ✅ Add Movie

    @PostMapping("/addMovie")

    public Movie addMovie(

            @RequestParam String title,

            @RequestParam String genre,

            @RequestParam String description,

            @RequestParam MultipartFile poster

    ) throws IOException {

        Movie movie = new Movie();

        movie.setTitle(title);

        movie.setGenre(genre);

        movie.setDescription(description);

        movie.setPosterImage(poster.getBytes());

        return adminService.addMovie(movie);

    }



    // ✅ Get all artists (with debug logs)

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





    @GetMapping("/songs")

    public List<TrendingSong> getAllSongs() {

        return adminService.getAllTrendingSongs();

    }



    @GetMapping("/movies")

    public List<Movie> getAllMovies() {

        return adminService.getAllMovies();

    }

}