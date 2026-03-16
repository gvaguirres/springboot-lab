package org.example.springbootlab;

import org.example.springbootlab.entity.Movie;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLabApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(MovieRepository movieRepository) {
        return args -> {
            if (movieRepository.count() == 0) {
                String[][] movies = {
                        {"Pudu in the Wild", "Nature Films", "2023", "90", "A documentary about the smallest deer."},
                        {"The Shawshank Redemption", "Frank Darabont", "1994", "142", "Two imprisoned men bond over a number of years."},
                        {"Inception", "Christopher Nolan", "2010", "148", "A thief who steals corporate secrets through dream-sharing."},
                        {"The Godfather", "Francis Ford Coppola", "1972", "175", "The aging patriarch of an organized crime dynasty."},
                        {"Pulp Fiction", "Quentin Tarantino", "1994", "154", "The lives of two mob hitmen, a boxer, and others."},
                        {"The Dark Knight", "Christopher Nolan", "2008", "152", "Batman sets out to dismantle the remaining crime gangs."},
                        {"Forrest Gump", "Robert Zemeckis", "1994", "142", "The presidencies, the Vietnam War, and more unfold."},
                        {"The Matrix", "Lana Wachowski", "1999", "136", "A computer hacker learns about the true nature of his reality."},
                        {"Parasite", "Bong Joon Ho", "2019", "132", "Greed and class discrimination threaten a relationship."},
                        {"Interstellar", "Christopher Nolan", "2014", "169", "A team of explorers travel through a wormhole in space."}
                };

                for (String[] data : movies) {
                    Movie movie = new Movie();
                    movie.setTitle(data[0]);
                    movie.setDirector(data[1]);
                    movie.setYear(data[2]);
                    movie.setDurationMinutes(Integer.parseInt(data[3]));
                    movie.setDescription(data[4]);
                    movieRepository.save(movie);
                }
            }
        };
    }

}
