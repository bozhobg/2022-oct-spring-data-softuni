package com.example.advquerying.repository;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByNameStartingWith(String startsWith);

    @Query("""
            SELECT i
            FROM Ingredient AS i
            WHERE i.name IN :names
            """)
    Set<Ingredient> selectIngredientsByNameIn(Set<String> names);

    
}
