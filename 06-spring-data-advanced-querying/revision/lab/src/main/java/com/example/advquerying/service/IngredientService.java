package com.example.advquerying.service;

import com.example.advquerying.entities.Ingredient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface IngredientService {

    String getAsStringOutputIngredientsStartingWith(String startsWith);

    Set<Ingredient> findIngredientsByName(Set<String> ingredientNames);

    int deleteByName(String name);

    int updateIngredientsPrice(BigDecimal percent);

    int updateIngredientsPriceByName(BigDecimal percent, String name);
}
