package bg.softuni.exercise.domain.dto;

import bg.softuni.exercise.domain.entities.enums.AgeRestrictionEnum;
import bg.softuni.exercise.domain.entities.enums.EditionEnum;

import java.math.BigDecimal;

public record BookReducedDTO(String title, EditionEnum edition, AgeRestrictionEnum ageRestriction, BigDecimal price) {

    @Override
    public String toString() {
        return title + " " + edition + " " + ageRestriction + " " + price;
    }
}
