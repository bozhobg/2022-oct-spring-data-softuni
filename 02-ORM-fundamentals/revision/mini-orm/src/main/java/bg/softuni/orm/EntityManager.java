package bg.softuni.orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityManager<E> implements DBContext<E> {

    private Connection conn;

    public EntityManager(Connection conn) {
        this.conn = conn;
    }

    private Field getId(Class<?> entityClass) {

        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));
    }

    @Override
    public boolean persist(E entity)
            throws IllegalAccessException, SQLException {
        Field primaryKey = getId(entity.getClass());
        primaryKey.setAccessible(true);
        Object value = primaryKey.get(entity);

        if (value == null || (long) value <= 0) {
            return doInsert(entity, primaryKey);
        }

        return doUpdate(entity, value);
    }

    private boolean doUpdate(E entity, Object idValue)
            throws IllegalAccessException, SQLException {
        String tableQuery = QueryFormatter.formatSQLAddressPart(
                getTableName(entity.getClass()));
        String idQuery = QueryFormatter.formatSqlValue(idValue);

        Map<String, Object> columnNamesAndValuesMap = getColumnNamesAndValues(entity);
        String setQueryListing = updateSetQueryListing(columnNamesAndValuesMap);

        String sql = """
                UPDATE %s
                SET %s
                WHERE `id` = %s;
                """;

        String query = String.format(sql, tableQuery, setQueryListing, idQuery);
        int updatedRows = conn.prepareStatement(query).executeUpdate();

        return updatedRows != 0;
    }

    private boolean doInsert(E entity, Field primaryKey)
            throws SQLException, IllegalAccessException {

        String tableName = this.getTableName(entity.getClass());

        Map<String, Object> columnNamesAndValuesMap = getColumnNamesAndValues(entity);

        String[] columnNames = columnNamesAndValuesMap.keySet().toArray(String[]::new);
        String columnsSql = formatQueryListing(columnNames, QueryFormatter::formatSQLAddressPart);

        Object[] columnValues = columnNamesAndValuesMap.values().toArray(Object[]::new);
        String valuesSql = formatQueryListing(columnValues, QueryFormatter::formatSqlValue);

        String sql = "INSERT INTO " + tableName + "(" + columnsSql + ") " +
                "VALUES (" + valuesSql + ");";

        int updateCount = conn.prepareStatement(sql).executeUpdate();

        return updateCount != 0;
    }

    private String getTableName(Class<?> entityClass) {

        return entityClass.getAnnotation(Entity.class)
                .name();
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where) {
        return null;
    }

    private static <E> Map<String, Object> getColumnNamesAndValues(E entity)
            throws IllegalAccessException {

        Field[] declaredFields = entity.getClass().getDeclaredFields();
        Map<String, Object> mapColumnValue = new LinkedHashMap<>();

        for (Field field : declaredFields) {
            Column columnAnnotation = field.getAnnotation(Column.class);

            if (columnAnnotation == null) continue;

            Class<?> type = field.getType();
            field.setAccessible(true);
            Object o = field.get(entity);

            mapColumnValue.put(columnAnnotation.name(), o);
        }

        return mapColumnValue;
    }

    private static <T> String formatQueryListing(T[] arr, Function<T, String> mappingFunction) {

        return Arrays.stream(arr)
                .map(mappingFunction)
                .collect(Collectors.joining(", "));
    }

    private String updateSetQueryListing(Map<String, Object> columnWithValueMap) {
        List<String> updateSetDeclarations = new ArrayList<>();

        for (Map.Entry<String, Object> e : columnWithValueMap.entrySet()) {
            updateSetDeclarations.add(
                    String.format("%s = %s",
                            QueryFormatter.formatSQLAddressPart(e.getKey()),
                            QueryFormatter.formatSqlValue(e.getValue()))
            );
        }

        return String.join(", ", updateSetDeclarations);
    }
}
