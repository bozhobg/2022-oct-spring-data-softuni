package bg.softuni.orm.utils;

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

    public static final String SELECT_FIRST_TABLE_WHERE_CLAUSE = """
            SELECT *
            FROM %s
            %S
            LIMIT 1;
            """;
    public static final String SELECT_TABLE_WHERE_CLAUSE = """
            SELECT *
            FROM %s
            %S;
            """;

    public static final String CREATE_TABLE_ID_AUTO_INCREMENT_COL_DEFINITION_LISTING = """
            CREATE TABLE %s(
                `id` INT PRIMARY KEY AUTO_INCREMENT,
                %s
            );
            """;

    // ALTER TABLE `table` ADD [COLUMN] (col_def1, col_def2 ...);
    public static final String ALTER_TABLE_ADD_COLUMNS = """
            ALTER TABLE %s
            ADD COLUMN (%s);
            """;

//    `username` VARCHAR(255)
    public static final String COLUMN_DEFINITION = "%s %s";

    public static final String PR_STMT_COLUMN_LABELS_FOR_TABLE = """
            SELECT `column_name`
            FROM `information_schema`.`columns`
            WHERE `table_name` = ?;
            """;
}
