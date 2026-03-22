package org.example.springbootlab;

import org.example.springbootlab.entity.Movie;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode =
        EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SpringbootLabApplication {

    private static final String MARTIN_SCORSESE = "Martin Scorsese";
    private static final String STEVEN_SPIELBERG = "Steven Spielberg";
    private static final String PETER_JACKSON = "Peter Jackson";
    private static final String QUENTIN_TARANTINO = "Quentin Tarantino";
    private static final String CHRISTOPHER_NOLAN = "Christopher Nolan";

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLabApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(MovieRepository movieRepository) {
        return args -> {
                if (movieRepository.count() == 0) {
                    String[][] movies = {
                            {"Pudu in the Wild", "Nature Films", "2023", "90", "A fascinating documentary about the world's smallest deer in Chilean forests, exploring its unique habitat and elusive behavior."},
                            {"The Shawshank Redemption", "Frank Darabont", "1994", "142", "A banker wrongly convicted of murder finds hope and redemption over decades in the harsh Shawshank prison."},
                            {"Inception", CHRISTOPHER_NOLAN, "2010", "148", "A thief who steals secrets through the subconscious during dreams is tasked with the reverse: planting an idea into a target's mind."},
                            {"The Godfather", "Francis Ford Coppola", "1972", "175", "The epic story of the Corleone family, a New York organized crime dynasty struggling to maintain power and honor."},
                            {"Pulp Fiction", QUENTIN_TARANTINO, "1994", "154", "Intertwined stories of criminals, boxers, and mobsters in a vibrant Los Angeles, filled with iconic dialogue and unexpected twists."},
                            {"The Dark Knight", CHRISTOPHER_NOLAN, "2008", "152", "Batman faces his greatest psychological challenge against the Joker, a mastermind of chaos seeking to destroy Gotham's morality."},
                            {"Forrest Gump", "Robert Zemeckis", "1994", "142", "A man with a low IQ experiences key historical events in the US while searching for his childhood sweetheart, Jenny."},
                            {"The Matrix", "Lana Wachowski", "1999", "136", "A computer programmer discovers reality is a simulation created by machines and joins a rebellion to free humanity."},
                            {"Parasite", "Bong Joon Ho", "2019", "132", "A poor family cleverly infiltrates the lives of a wealthy household, triggering a series of dark and shocking events."},
                            {"Interstellar", CHRISTOPHER_NOLAN, "2014", "169", "A team of explorers travels through a wormhole in space to find a new home for humanity before Earth collapses."},
                            {"The Fellowship of the Ring", PETER_JACKSON, "2001", "178", "A young hobbit is tasked with destroying a dark ring to save Middle-earth from the evil forces of Sauron."},
                            {"The Two Towers", PETER_JACKSON, "2002", "179", "Frodo and Sam continue their journey to Mordor while their friends defend the kingdom of Rohan from a massive orc invasion."},
                            {"The Return of the King", PETER_JACKSON, "2003", "201", "The final battle for Middle-earth begins as Frodo reaches Mount Doom to destroy the One Ring once and for all."},
                            {"Harry Potter and the Sorcerer's Stone", "Chris Columbus", "2001", "152", "An orphaned boy discovers on his 11th birthday that he is a wizard and is invited to study at Hogwarts School."},
                            {"Harry Potter and the Chamber of Secrets", "Chris Columbus", "2002", "161", "Harry returns to Hogwarts for his second year, but a mysterious threat begins petrifying students in the hallways."},
                            {"Harry Potter and the Prisoner of Azkaban", "Alfonso Cuarón", "2004", "142", "Harry learns that a dangerous follower of Lord Voldemort has escaped from wizard prison to come after him."},
                            {"Harry Potter and the Goblet of Fire", "Mike Newell", "2005", "157", "Harry is unexpectedly selected to compete in a dangerous tournament between three schools of magic."},
                            {"The Prestige", CHRISTOPHER_NOLAN, "2006", "130", "Two rival magicians in 19th-century London become obsessed with outdoing each other, leading to terrible sacrifices."},
                            {"Dunkirk", CHRISTOPHER_NOLAN, "2017", "106", "Hundreds of thousands of Allied soldiers are surrounded by enemy forces on French beaches during World War II."},
                            {"Oppenheimer", CHRISTOPHER_NOLAN, "2023", "180", "A biographical drama about J. Robert Oppenheimer and his pivotal role in developing the atomic bomb during the war."},
                            {"Django Unchained", QUENTIN_TARANTINO, "2012", "165", "A freed slave joins a German bounty hunter to rescue his wife from a brutal plantation owner in Mississippi."},
                            {"Inglourious Basterds", QUENTIN_TARANTINO, "2009", "153", "In occupied France, a group of Jewish soldiers plans to assassinate Nazi leaders during a film premiere."},
                            {"The Wolf of Wall Street", MARTIN_SCORSESE, "2013", "180", "The true story of stockbroker Jordan Belfort and his rise to extreme wealth followed by a fall filled with excess."},
                            {"Killers of the Flower Moon", MARTIN_SCORSESE, "2023", "206", "An investigation into the serial murders of Osage Nation members in the 1920s after oil was discovered on their land."},
                            {"The Irishman", MARTIN_SCORSESE, "2019", "209", "A truck driver becomes a hitman involved with the mob and the mysterious disappearance of Jimmy Hoffa."},
                            {"Schindler's List", STEVEN_SPIELBERG, "1993", "195", "A German businessman saves over a thousand Jews from the Holocaust by employing them in his factories during the war."},
                            {"Jurassic Park", STEVEN_SPIELBERG, "1993", "127", "A billionaire creates a theme park with cloned dinosaurs, but a security failure unleashes total chaos."},
                            {"Saving Private Ryan", STEVEN_SPIELBERG, "1998", "169", "A group of soldiers risk their lives to rescue a paratrooper whose brothers have all been killed in action."},
                            {"The Hobbit: An Unexpected Journey", PETER_JACKSON, "2012", "169", "Bilbo Baggins is convinced by Gandalf to join a group of dwarves on a quest to reclaim a stolen treasure."},
                            {"The Departed", MARTIN_SCORSESE, "2006", "151", "An undercover cop and a mole in the police attempt to identify each other while infiltrating an Irish gang."}
                    };

                    for (String[] data : movies) {
                        Movie movie = new Movie();
                        movie.setTitle(data[0]);
                        movie.setDirector(data[1]);
                        movie.setYear(data[2]);
                        movie.setDurationMinutes((data[3]));
                        movie.setDescription(data[4]);
                        movieRepository.save(movie);
                    }
                }

        };
    }

}
