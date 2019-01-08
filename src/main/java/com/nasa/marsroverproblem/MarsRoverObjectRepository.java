package com.nasa.marsroverproblem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarsRoverObjectRepository extends CrudRepository<MarsRover, String> {

    Optional<MarsRover> findById(@Param("id") String id);

    Optional<MarsRover> findByName(@Param("name") String name);

    List<MarsRover> findAll();
}
