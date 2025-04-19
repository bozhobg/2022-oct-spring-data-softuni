package com.example.advquerying.service;

import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.Set;

public interface ShampooService {

    String getAsStringOutputShampoosBySizeOrderedById(Size size);

    String getAsStringOutputShampoosBySizeAndLabelIdOrderedByPriceAsc(Size size, Long labelId);

    String getAsStringOutputShampoosWithPriceGreaterThan(BigDecimal price);

    long getCountOfPriceLowerThan(BigDecimal price);

    String getStringOutputShampoosByIngredients(Set<String> ingredientNames);

    String getAsStringOutputShampoosWithIngredientsCountLessThan(int count);
}
