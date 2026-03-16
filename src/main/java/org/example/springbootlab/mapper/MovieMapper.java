package org.example.springbootlab.mapper;

import org.example.springbootlab.dto.CreateMovieDTO;
import org.example.springbootlab.dto.MovieDTO;
import org.example.springbootlab.dto.UpdateMovieDTO;
import org.example.springbootlab.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    //Entity to MovieDTO
    MovieDTO toDto(Movie movie);

    //Create from Dto to Entity
    Movie toEntity(CreateMovieDTO createDto);

    //Update an existent Entity
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(UpdateMovieDTO updateDto, @MappingTarget Movie movie );

}
