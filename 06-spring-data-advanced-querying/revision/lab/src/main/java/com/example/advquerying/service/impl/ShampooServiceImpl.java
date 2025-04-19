package com.example.advquerying.service.impl;

import com.example.advquerying.constant.FormatTemplates;
import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repository.ShampooRepository;
import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    private final IngredientService ingredientService;

    @Autowired
    public ShampooServiceImpl(
            ShampooRepository shampooRepository,
            IngredientService ingredientService
    ) {
        this.shampooRepository = shampooRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public String getAsStringOutputShampoosBySizeOrderedById(Size size) {

        return parseShampoosToOutput(
                this.shampooRepository.findAllBySizeOrderById(size),
                ShampooServiceImpl::formatShampooToStringOfBrandSizePrice
        );
    }

    @Override
    public String getAsStringOutputShampoosBySizeAndLabelIdOrderedByPriceAsc(Size size, Long labelId) {
        return parseShampoosToOutput(
                this.shampooRepository.findAllBySizeOrLabelIdOrderByPriceAsc(size, labelId),
                ShampooServiceImpl::formatShampooToStringOfBrandSizePrice
        );
    }

    @Override
    public String getAsStringOutputShampoosWithPriceGreaterThan(BigDecimal price) {
        return parseShampoosToOutput(
                this.shampooRepository.findAllByPriceIsGreaterThanOrderByPriceDesc(price),
                ShampooServiceImpl::formatShampooToStringOfBrandSizePrice
        );
    }

    @Override
    public long getCountOfPriceLowerThan(BigDecimal price) {
        return this.shampooRepository.countAllByPriceLessThan(price);
    }

    @Override
    public String getStringOutputShampoosByIngredients(Set<String> ingredientNames) {
        Set<Ingredient> ingredients = this.ingredientService.findIngredientsByName(ingredientNames);
        Set<Shampoo> shampoos = this.shampooRepository.selectShampoosByIngredients(ingredients);

        return parseShampoosToUniqueOutput(shampoos, Shampoo::getBrand);
    }

    @Override
    public String getAsStringOutputShampoosWithIngredientsCountLessThan(int count) {

        return parseShampoosToOutput(
                this.shampooRepository.selectShampoosWithIngredientsCountLessThan(count),
                Shampoo::getBrand
        );
    }

    private static String parseShampoosToUniqueOutput(Collection<Shampoo> shampoos, Function<Shampoo, String> mapLogic) {
        return shampoos.stream()
                .map(mapLogic)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static String parseShampoosToOutput(Collection<Shampoo> shampoos, Function<Shampoo, String> mapLogic) {
        return shampoos.stream()
                .map(mapLogic)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static String formatShampooToStringOfBrandSizePrice(Shampoo s) {
        return String.format(
                FormatTemplates.SHAMPOO_BRAND_SIZE_PRICE,
                s.getBrand(), s.getSize().name(), s.getPrice().toString()
        );
    }
}
