package bg.softuni.exercise.service;

import bg.softuni.exercise.domain.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    Set<Category> getRandomCategory();

    void seedCategories(List<Category> categories);

    boolean isDataSeeded();
}
