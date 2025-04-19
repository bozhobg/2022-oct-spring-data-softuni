package com.example.advquerying.service.impl;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repository.IngredientRepository;
import com.example.advquerying.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public String getAsStringOutputIngredientsStartingWith(String startsWith) {
        return this.ingredientRepository.findAllByNameStartingWith(startsWith)
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public Set<Ingredient> findIngredientsByName(Set<String> ingredientNames) {
        return this.ingredientRepository.selectIngredientsByNameIn(ingredientNames);
    }
}
