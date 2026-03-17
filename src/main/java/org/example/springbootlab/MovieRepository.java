package org.example.springbootlab;

import org.example.springbootlab.dto.MovieDTO;
import org.example.springbootlab.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends ListCrudRepository<Movie, Long> {

    @Query("SELECT t FROM Movie t WHERE t.title like %:title%")
    Optional<Movie> findByTitle(@Param("title") String title);

    @Query("SELECT m FROM Movie m WHERE m.director like %:director%")
    List<Movie> findMovieByDirector(@Param("director") String director);

    @Query("SELECT y FROM Movie y WHERE y.year = :year")
    List<Movie> findMovieByYear(@Param("year") String year);
}
