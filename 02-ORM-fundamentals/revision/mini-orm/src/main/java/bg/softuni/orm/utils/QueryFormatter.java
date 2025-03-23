package bg.softuni.orm.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class QueryFormatter {

    public static String formatSQLAddressPart(String name) {
        return "`" + name + "`";
    }

    public static String formatSqlValue(Object o) {

        return switch (o) {
            case Integer i -> i.toString();
            case Short s -> s.toString();
            case Long l -> l.toString();
            case Float f -> f.toString();
            case Double d -> d.toString();
            case Boolean b -> b.toString().toUpperCase();
            case null -> "NULL";
            default -> "'" + o + "'";
        };
    }

    public static String formatToSQLDataType(Class<?> fieldType) {
//        TODO: other SQL data types (like blobs, TEXT, FLOAT, DOUBLE, DECIMAL)
//         as well as specifying size and digits after decimal point

        if (fieldType == short.class || fieldType == Short.class) {
            return "SMALLINT";
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return "INT";
        } else if (fieldType == long.class || fieldType == Long.class) {
            return "BIGINT";
        }  else if (fieldType == LocalDate.class) {
            return "DATE";
        } else {
            return "VARCHAR(255)";
        }
    }


}
