package org.example.springbootlab;

import org.example.springbootlab.dto.MovieDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.example.springbootlab.MovieReadController.MOVIE_LIST;
import static org.example.springbootlab.MovieReadController.MOVIE_PAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = MovieReadController.class)
class MovieReadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @MockitoBean
    private MovieRepository movieRepository;

    private String director;
    private String title;
    private String year;
    private MovieDTO movieDto;
    private List<MovieDTO> movies;

    @BeforeEach
    void setUp() {

        director = "James Cameron";
        title = "Titanic";
        year = "1998";

        movieDto = new MovieDTO(1L, title, "Description", year, director, "195");
        movies = List.of(movieDto);
    }

    @Test
    void showHomePage_shouldReturnHomeView() throws Exception {

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void getMovies_ShouldReturnListViewWithPagination() throws Exception {

        Page<MovieDTO> moviePage = new PageImpl<>(List.of(movieDto));

        when(movieService.getAllBy(any(Pageable.class))).thenReturn(moviePage);

        mockMvc.perform(get("/movies/list")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("ascending", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name(MOVIE_LIST))
                .andExpect(model().attributeExists(MOVIE_PAGE))
                .andExpect(model().attribute(MOVIE_PAGE, Matchers.hasProperty("content", Matchers.hasSize(1))));
    }

    @Test
    void getMovieByTitle_ShouldReturnListView() throws Exception {

        when(movieService.getMovieByTitle(title)).thenReturn(movies);

        mockMvc.perform(get("/movies/title/{title}", title))
                .andExpect(status().isOk())
                .andExpect(view().name(MOVIE_LIST))
                .andExpect(model().attributeExists(MOVIE_PAGE))
                .andExpect(model().attribute(MOVIE_PAGE, Matchers.hasProperty("content", Matchers.hasSize(1))));
    }

    @Test
    void getMovieByDirector_ShouldReturnListView() throws Exception {

        when(movieService.getMoviesByDirector(director)).thenReturn(movies);

        mockMvc.perform(get("/movies/director/{director}", director))
                .andExpect(status().isOk())
                .andExpect(view().name(MOVIE_LIST))
                .andExpect(model().attributeExists(MOVIE_PAGE))
                .andExpect(model().attribute(MOVIE_PAGE, Matchers.hasProperty("content", Matchers.hasSize(1))));
    }

    @Test
    void getMovieByYear_ShouldReturnListView() throws Exception {

        when(movieService.getMoviesByYear(year)).thenReturn(movies);

        mockMvc.perform(get("/movies/year/{year}", year))
                .andExpect(status().isOk())
                .andExpect(view().name(MOVIE_LIST))
                .andExpect(model().attributeExists(MOVIE_PAGE))
                .andExpect(model().attribute(MOVIE_PAGE, Matchers.hasProperty("content", Matchers.hasSize(1))));
    }

    @Test
    void search_byTitle_ShouldRedirectToTitleUrl() throws Exception {

        mockMvc.perform(get("/movies/search")
                .param("query", "Titanic")
                .param("searchType", "title"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies/title/Titanic"));
    }

    @Test
    void search_byDirector_ShouldRedirectToDirectorUrl() throws Exception {

        mockMvc.perform(get("/movies/search")
                .param("query", "James Cameron")
                .param("searchType", "director"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies/director/James Cameron"));
    }

    @Test
    void search_byYear_ShouldRedirectToYearUrl() throws Exception {

        mockMvc.perform(get("/movies/search")
                        .param("query", "1998")
                        .param("searchType", "year"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies/year/1998"));
    }

    @Test
    void search_EmptyQuery_ShouldRedirectToRootUrl() throws Exception {

        mockMvc.perform(get("/movies/search")
                .param("query", "")
                .param("searchType", "title"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
