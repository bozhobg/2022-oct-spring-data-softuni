package com.example.advquerying;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Size;
import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final static Scanner SC = new Scanner(System.in);

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public ConsoleRunner(
            ShampooService shampooService,
            IngredientService ingredientService
    ) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {

//        p01SelectShampoosBySize();
//        p02SelectShampoosBySizeAndLabel();
//        p03SelectShampoosByPriceGreater();
//        p04SelectIngredientsByName();
//        p05CountShampoosByPrice();
//        p06JpqlSelectShampoosByIngredients();
//        p07JpqlSelectShampoosByIngredientsCount();
        p08JpqlDeleteIngredientsByName();
    }

    private void p08JpqlDeleteIngredientsByName() {

    }

    private void p07JpqlSelectShampoosByIngredientsCount() {
        int count = Integer.parseInt(SC.nextLine());

        System.out.println(this.shampooService.getAsStringOutputShampoosWithIngredientsCountLessThan(count));
    }

    private void p06JpqlSelectShampoosByIngredients() {
        String input = SC.nextLine();
        Set<String> ingredientNames = new HashSet<>();

        while (!input.isBlank()) {
            ingredientNames.add(input);
            input = SC.nextLine();
        }

        System.out.println(this.shampooService.getStringOutputShampoosByIngredients(ingredientNames));
    }

    private void p05CountShampoosByPrice() {
        BigDecimal price = new BigDecimal(SC.nextLine());
        System.out.println(this.shampooService.getCountOfPriceLowerThan(price));
    }

    private void p04SelectIngredientsByName() {
        String startsWith = SC.nextLine();
        System.out.println(this.ingredientService.getAsStringOutputIngredientsStartingWith(startsWith));
    }

    private void p03SelectShampoosByPriceGreater() {
        BigDecimal price = new BigDecimal(SC.nextLine());
        System.out.println(this.shampooService.getAsStringOutputShampoosWithPriceGreaterThan(price));
    }

    private void p02SelectShampoosBySizeAndLabel() {
        String sizeInput = SC.nextLine();
        Size size = getSize(sizeInput);
        Long labelId = Long.parseLong(SC.nextLine());

        System.out.println(this.shampooService.getAsStringOutputShampoosBySizeAndLabelIdOrderedByPriceAsc(size, labelId));

    }

    private void p01SelectShampoosBySize() {
        String input = SC.nextLine();
        Size size = getSize(input);

        System.out.println(this.shampooService.getAsStringOutputShampoosBySizeOrderedById(size));
    }

    private static Size getSize(String input) {
        Size size;

        try {
            size = Size.valueOf(input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(input + "is not a valid size!");
        }
        return size;
    }
}
