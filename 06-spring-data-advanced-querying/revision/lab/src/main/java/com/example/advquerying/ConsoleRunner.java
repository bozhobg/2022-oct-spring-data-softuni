package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.service.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final static Scanner SC = new Scanner(System.in);

    private final ShampooService shampooService;

    @Autowired
    public ConsoleRunner(ShampooService shampooService) {
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {

        p01SelectShampoosBySize();
    }

    private void p01SelectShampoosBySize() {
        String input = SC.nextLine();
        Size size;

        try {
            size = Size.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println(input + "is not a valid size!");
            return;
        }

        System.out.println(this.shampooService.getAsStringOutputShampoosBySizeOrderedById(size));
    }
}
