package bg.softuni.exercise.service.impl;

import bg.softuni.exercise.domain.entities.Category;
import bg.softuni.exercise.repositories.CategoryRepository;
import bg.softuni.exercise.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getRandomCategory() {
        long total = this.categoryRepository.count();
        int returnCount = new Random().nextInt(1, (int) total);

        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < returnCount; i++) {
            int randomId = new Random().nextInt((int) total) + 1;
            categories.add(
                    this.categoryRepository.findById(randomId)
                            .orElseThrow(() -> new IllegalArgumentException("Category not found for random id: " + randomId + "!"))
            );
        }

        return categories;
    }

    @Override
    public void seedCategories(List<Category> categories) {
        this.categoryRepository.saveAllAndFlush(categories);
    }

    @Override
    public boolean isDataSeeded() {
        return this.categoryRepository.count() > 0;
    }
}
