package org.example.springbootlab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        log.info("MovieService constructor");
        this.movieRepository = movieRepository;
    }
}
