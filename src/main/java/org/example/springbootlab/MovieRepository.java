package org.example.springbootlab;

import org.example.springbootlab.entity.Movie;
import org.springframework.data.repository.ListCrudRepository;

public interface MovieRepository extends ListCrudRepository<Movie, Long> {
}
