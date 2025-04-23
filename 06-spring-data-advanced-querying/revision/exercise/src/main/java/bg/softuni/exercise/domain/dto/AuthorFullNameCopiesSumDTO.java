package bg.softuni.exercise.domain.dto;

import bg.softuni.exercise.constants.TemplateFormatsAndMessages;

public class AuthorFullNameCopiesSumDTO {

    private String firstName;
    private String lastName;
    private Long totalCopies;

    public AuthorFullNameCopiesSumDTO() {}

    public AuthorFullNameCopiesSumDTO(String firstName, String lastName, Long totalCopies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalCopies = totalCopies;
    }

    public String getFirstName() {
        return firstName;
    }

    public AuthorFullNameCopiesSumDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthorFullNameCopiesSumDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Long getTotalCopies() {
        return totalCopies;
    }

    public AuthorFullNameCopiesSumDTO setTotalCopies(Long totalCopies) {
        this.totalCopies = totalCopies;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                TemplateFormatsAndMessages.FORMAT_AUTHOR_FIRST_LAST_NAME_TOTAL_BOOK_COPIES,
                this.firstName, this.lastName, this.totalCopies
        );
    }
}

