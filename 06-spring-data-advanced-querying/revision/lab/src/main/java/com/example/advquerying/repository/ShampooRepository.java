package com.example.advquerying.repository;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPriceAsc(Size size, Long id);

    List<Shampoo> findAllByPriceIsGreaterThanOrderByPriceDesc(BigDecimal price);

    long countAllByPriceLessThan(BigDecimal lessThanPrice);

    @Query("""
                SELECT s
                FROM Shampoo AS s
                JOIN s.ingredients AS i
                WHERE i in :ingredients
            """)
    Set<Shampoo> selectShampoosByIngredients(@Param("ingredients") Set<Ingredient> ingredients);

    @Query("""
            SELECT s
            FROM Shampoo AS s
            JOIN s.ingredients AS i
            GROUP BY s
            HAVING COUNT(i) < :count
            """)
    List<Shampoo> selectShampoosWithIngredientsCountLessThan(@Param("count") int count);


}
