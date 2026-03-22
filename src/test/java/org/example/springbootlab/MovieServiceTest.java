package org.example.springbootlab;

import org.example.springbootlab.dto.MovieDTO;
import org.example.springbootlab.dto.UpdateMovieDTO;
import org.example.springbootlab.entity.Movie;
import org.example.springbootlab.exception.MovieAlreadyExistsException;
import org.example.springbootlab.exception.ResourceNotFoundException;
import org.example.springbootlab.mapper.MovieMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import org.example.springbootlab.dto.CreateMovieDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieService movieService;

    private Long movieId;
    private String director;
    private String title;
    private String year;
    private Movie movieEntity;
    private MovieDTO movieDTO;

    @BeforeEach
    void setUp() {
        movieId = 1L;
        director = "James Cameron";
        title = "Titanic";
        year = "1998";

        movieEntity = new Movie(movieId, title, "Description", year, director, "195");
        movieDTO = new MovieDTO(movieId, title, "Description", year, director, "195");
    }

    @Test
    void createMovie() {

        CreateMovieDTO createMovieDTO = new CreateMovieDTO(title, "Description", year, director, "195");

        when(movieMapper.toEntity(createMovieDTO)).thenReturn(movieEntity);
        when(movieRepository.save(movieEntity)).thenReturn(movieEntity);
        when(movieMapper.toDto(movieEntity)).thenReturn(movieDTO);

        MovieDTO result = movieService.createMovie(createMovieDTO);

        assertNotNull(result);
        assertEquals("Titanic", result.getTitle());
        verify(movieRepository).save(movieEntity);
    }

    @Test
    void notCreateMovie_WhenItAlreadyExists() {

        CreateMovieDTO createMovieDTO = new CreateMovieDTO(title, "Description", year, director, "195");

        when(movieMapper.toEntity(createMovieDTO)).thenReturn(movieEntity);
        when(movieRepository.existsByTitleIgnoreCase(title)).thenReturn(true);

        MovieAlreadyExistsException exception = assertThrows(MovieAlreadyExistsException.class, () -> movieService.createMovie(createMovieDTO));

        assertEquals("The PuduMovie " + title + " already exists", exception.getMessage());
    }

    @Test
    void updateMovie() {

        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO(title, "Description", year, director, "195");

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movieEntity));
        when(movieRepository.save(movieEntity)).thenReturn(movieEntity);
        when(movieMapper.toDto(movieEntity)).thenReturn(movieDTO);

        MovieDTO result = movieService.updateMovie(movieId, updateMovieDTO);

        assertNotNull(result);
        assertEquals("Titanic", result.getTitle());
        verify(movieRepository).save(movieEntity);
    }

    @Test
    void deleteMovie() {

        when(movieRepository.existsById(movieId)).thenReturn(true);

        movieService.deleteMovie(movieId);

        verify(movieRepository).deleteById(movieId);
    }

    @Test
    void deleteMovie_ShouldReturnResourceNotFoundException_WhenItDoesNotExist() {

        when(movieRepository.existsById(movieId)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> movieService.deleteMovie(movieId));

        assertEquals("PuduMovie not found with id: " + movieId, exception.getMessage());

        verify(movieRepository, never()).deleteById(movieId);
    }

    @Test
    void getMovieById_ShouldReturnMovieDTO_WhenItExists() {

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movieEntity));
        when(movieMapper.toDto(any(Movie.class))).thenReturn(movieDTO);

        MovieDTO result = movieService.getMovieById(movieId);

        assertNotNull(result);
        assertEquals("Titanic", result.getTitle());
        verify(movieRepository).findById(movieId);
    }

    @Test
    void getMovieByTitle_ShouldReturnListOfMovieDTO_WhenItExists() {

        when(movieRepository.findByTitleContainingIgnoreCase(title)).thenReturn(List.of(movieEntity));
        when(movieMapper.toDto(any(Movie.class))).thenReturn(movieDTO);

        List<MovieDTO> result = movieService.getMovieByTitle(title);

        assertNotNull(result);
        assertEquals("Titanic", result.getFirst().getTitle());
        verify(movieRepository).findByTitleContainingIgnoreCase(title);
    }

    @Test
    void getMovieByTitle_ShouldReturnResourceNotFoundException_WhenItDoesNotExist() {
        when(movieRepository.findByTitleContainingIgnoreCase(title)).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> movieService.getMovieByTitle(title));

        assertEquals("PuduMovie not found with title: " + title, exception.getMessage());

        verify(movieRepository).findByTitleContainingIgnoreCase(title);
    }

    @Test
    void getMovieByDirector_ShouldReturnListOfMovieDTO_WhenItExists() {

        when(movieRepository.findMovieByDirectorContainingIgnoreCase(director)).thenReturn(List.of(movieEntity));
        when(movieMapper.toDto(any(Movie.class))).thenReturn(movieDTO);

        List<MovieDTO> result = movieService.getMoviesByDirector(director);

        assertNotNull(result);
        assertEquals("James Cameron", result.getFirst().getDirector());
        verify(movieRepository).findMovieByDirectorContainingIgnoreCase(director);
    }

    @Test
    void getMovieByDirector_ShouldReturnResourceNotFoundException_WhenItDoesNotExist() {

        when(movieRepository.findMovieByDirectorContainingIgnoreCase(director)).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> movieService.getMoviesByDirector(director));

        assertEquals("PuduMovie not found with director: " + director, exception.getMessage());

        verify(movieRepository).findMovieByDirectorContainingIgnoreCase(director);
    }

    @Test
    void getMovieByYear_ShouldReturnListOfMovieDTO_WhenItExists() {

        when(movieRepository.findMovieByYear(year)).thenReturn(List.of(movieEntity));
        when(movieMapper.toDto(any(Movie.class))).thenReturn(movieDTO);

        List<MovieDTO> result = movieService.getMoviesByYear(year);

        assertNotNull(result);
        assertEquals("1998", result.getFirst().getYear());
        verify(movieRepository).findMovieByYear(year);
    }

    @Test
    void getMovieByYear_ShouldReturnResourceNotFoundException_WhenItDoesNotExist() {

        when(movieRepository.findMovieByYear(year)).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> movieService.getMoviesByYear(year));

        assertEquals("PuduMovie not found with year: " + year, exception.getMessage());

        verify(movieRepository).findMovieByYear(year);
    }

    @Test
    void getAllBy_ShouldReturnPageMovieDTO() {

        Pageable pageable;
        pageable = PageRequest.of(0, 10);
        Page<Movie> moviePage = new PageImpl<>(List.of(movieEntity), pageable, 1);

        when(movieRepository.findAllBy(pageable)).thenReturn(moviePage);
        when(movieMapper.toDto(any(Movie.class))).thenReturn(movieDTO);

        Page<MovieDTO> result = movieService.getAllBy(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Titanic", result.getContent().getFirst().getTitle());
        verify(movieRepository).findAllBy(pageable);

    }
}
