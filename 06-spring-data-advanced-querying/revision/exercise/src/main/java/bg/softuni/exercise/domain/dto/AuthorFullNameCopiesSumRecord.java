package bg.softuni.exercise.domain.dto;

import bg.softuni.exercise.constants.TemplateFormatsAndMessages;

public record AuthorFullNameCopiesSumRecord(String firstName, String lastName, Long totalCopies) {

    @Override
    public String toString() {
        return String.format(
                TemplateFormatsAndMessages.FORMAT_AUTHOR_FIRST_LAST_NAME_TOTAL_BOOK_COPIES,
                this.firstName, this.lastName, this.totalCopies
        );
    }
}
