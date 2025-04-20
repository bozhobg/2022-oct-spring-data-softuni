package com.example.advquerying.repository;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Transactional
    @Modifying
    @Query("""
            DELETE FROM Ingredient AS i
            WHERE i.name = :name
            """)
    int deleteQueryByIngredientName(String name);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Ingredient AS i
            SET i.price = i.price * :percentage
            """)
    int updateQueryPrice(BigDecimal percentage);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Ingredient AS i
            SET i.price = i.price * :percentage
            WHERE i.name = :name
            """)
    int updateQueryPriceByName(BigDecimal percentage, String name);
}
