package org.example.springbootlab;

import org.example.springbootlab.dto.MovieDTO;
import org.example.springbootlab.exception.MovieAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = MovieWriteController.class)
class MovieWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @MockitoBean
    private MovieRepository movieRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public InternalResourceViewResolver viewResolver() {
            InternalResourceViewResolver resolver = new InternalResourceViewResolver();
            resolver.setPrefix("/templates/");
            resolver.setSuffix(".jte");
            return resolver;
        }
    }

    private static final String MOVIE_FORM = "movieForm";
    private static final String REDIRECT_MOVIES_LIST = "/movies/list";

    @Test
    void showForm_ShouldReturnMovieForm() throws Exception {

        mockMvc.perform(get("/movies/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(model().attributeExists(MOVIE_FORM));
    }

    @Test
    void showCreateForm_ShouldReturnMovieForm() throws Exception {

        mockMvc.perform(get("/movies/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(model().attributeExists(MOVIE_FORM));
    }

    @Test
    void createMovie_ShouldRedirectWhenValid() throws Exception {

        mockMvc.perform(post("/movies/create")
                .param("title", "Titanic")
                .param("year", "1998")
                .param("director", "James Cameron")
                .param("description", "Description")
                .param("durationMinutes", "195"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(REDIRECT_MOVIES_LIST))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    void createMovie_ShouldReturnForm_WhenValidationFails() throws Exception {

        mockMvc.perform(post("/movies/create")
                .param("title", "")
                .param("year", "1998")
                .param("director", "James Cameron")
                .param("description", "Description")
                .param("durationMinutes", "195"))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(model().attributeExists("nameError"))
                .andExpect(model().attribute("nameError", "Please enter a title of a PuduMovie"))
                .andExpect(model().attributeExists("errors"));
    }

    @Test
    void createMovie_ShouldReturnForm_WhenMovieAlreadyExists() throws Exception {

        doThrow(new MovieAlreadyExistsException("Already exists"))
                .when(movieService).createMovie(any());

        mockMvc.perform(post("/movies/create")
                .param("title", "Titanic")
                .param("year", "1998")
                .param("director", "James Cameron")
                .param("description", "Description")
                .param("durationMinutes", "195"))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors(MOVIE_FORM, "title"))
                .andExpect(model().attributeExists("errors"));
    }

    @Test
    void showUpdateForm_ShouldReturnMovieEditForm() throws Exception {

        Long id = 1L;
        MovieDTO movie = new MovieDTO(id, "Titanic", "Description", "1998", "James Cameron", "195");

        when(movieService.getMovieById(id)).thenReturn(movie);

        mockMvc.perform(get("/movies/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("movie-edit-form"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attribute("movie", movie))
                .andExpect(model().attribute("id", id))
                .andExpect(model().attributeExists(MOVIE_FORM));
    }

    @Test
    void updateMovie_ShouldRedirectWhenValid() throws Exception {

        Long id = 1L;

        mockMvc.perform(post("/movies/update/{id}", id)
                        .param("title", "Titanic")
                        .param("year", "1998")
                        .param("director", "James Cameron")
                        .param("description", "Description")
                        .param("durationMinutes", "195"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(REDIRECT_MOVIES_LIST))
                .andExpect(flash().attribute("message", "PuduMovie updated successfully"));

        verify(movieService).updateMovie(eq(id), any());
    }

    @Test
    void updateMovie_ShouldReturnForm_WhenValidationFails() throws Exception {

        Long id = 1L;

        mockMvc.perform(post("/movies/update/{id}", id)
                .param("title", "")
                .param("director", "James Cameron"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie-edit-form"));

        verify(movieService, never()).updateMovie(any(), any());
    }

    @Test
    void deleteMovie_ShouldRedirectWhenValid() throws Exception {

        Long id = 1L;

        mockMvc.perform(post("/movies/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(REDIRECT_MOVIES_LIST));
    }
}
