package org.example.springbootlab;

import org.example.springbootlab.entity.Movie;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MovieRepository extends ListCrudRepository<Movie, Long> {

    List<Movie> findMovieByDirector(String director);

    List<Movie> findMovieByYear(String year);
}
