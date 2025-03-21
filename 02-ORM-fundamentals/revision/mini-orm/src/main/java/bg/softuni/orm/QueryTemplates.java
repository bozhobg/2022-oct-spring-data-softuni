package bg.softuni.orm;

public record QueryTemplates() {
    ;

    public static final String UPDATE_TABLE_SET_LIST_WHERE_ID = """
            UPDATE %s
            SET %s
            WHERE `id` = %s;
            """;

    public static final String INSERT_SINGLE_ROW_TABLE_COLUMNS_VALUES = """
            INSERT INTO %s(%s)
            VALUES (%s);
            """;
}
