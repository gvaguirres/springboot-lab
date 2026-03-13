package org.example.springbootlab;

import org.example.springbootlab.dto.CreateMovieDTO;
import org.example.springbootlab.dto.MovieDTO;
import org.example.springbootlab.dto.UpdateMovieDTO;
import org.example.springbootlab.entity.Movie;
import org.example.springbootlab.exception.ResourceNotFoundException;
import org.example.springbootlab.mapper.MovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);
    private MovieRepository movieRepository;
    private MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        log.info("MovieService constructor");
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public MovieDTO createMovie(CreateMovieDTO createDto) {
        log.info("MovieService createMovie");
        Movie movie = movieMapper.toEntity(createDto);
        Movie newMovie = movieRepository.save(movie);
        return movieMapper.toDto(newMovie);
    }

    public MovieDTO updateMovie(Long id, UpdateMovieDTO updateDto) {
        log.info("MovieService updateMovie");
        Movie movie = movieRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Movie not found"));
        movieMapper.updateEntityFromDto(updateDto, movie);
        Movie updatedMovie = movieRepository.save(movie);
        return movieMapper.toDto(updatedMovie);
    }

    public void deleteMovie(Long id){
        log.info("MovieService deleteMovie: {}", id);

        if (!movieRepository.existsById(id))
            throw new ResourceNotFoundException("Movie not found with id: " + id);

        movieRepository.deleteById(id);
    }

    public List<MovieDTO> getAllMovies() {
        log.info("MovieService getAllMovies");

        return movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .toList();
    }

    public MovieDTO getMovieById(Long id) {

        return movieRepository.findById(id)
                .map(movieMapper::toDto)
                .orElseThrow( () -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    public List<MovieDTO> getMovieByDirector(String director) {

        return movieRepository.findMovieByDirector(director).stream()
                .map(movieMapper::toDto)
                .toList();
    }

    public List<MovieDTO> getMovieByYear(String year) {

        return movieRepository.findMovieByYear(year).stream()
                .map(movieMapper::toDto)
                .toList();
    }
}
