package org.example.springbootlab;

import org.example.springbootlab.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends ListCrudRepository<Movie, Long> {

    @Query("SELECT t FROM Movie t WHERE LOWER(REPLACE(t.title, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:title, ' ', ''), '%'))")
    List<Movie> findByTitleContainingIgnoreCase(@Param("title") String title);

    @Query("""
            SELECT m
            FROM Movie m
            WHERE LOWER(m.director)
            LIKE LOWER(CONCAT('%', :director, '%'))
           """)
    List<Movie> findMovieByDirectorContainingIgnoreCase(@Param("director") String director);

    @Query("SELECT y FROM Movie y WHERE y.year = :year")
    List<Movie> findMovieByYear(@Param("year") String year);

    Page<Movie> findAllBy(Pageable pageable);

    boolean existsByTitleIgnoreCase(String title);
}
