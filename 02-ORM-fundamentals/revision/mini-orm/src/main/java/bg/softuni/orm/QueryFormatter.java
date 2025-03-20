package bg.softuni.orm;

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
            default -> "'" + o + "'";
        };
    }


}
