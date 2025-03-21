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

    @Override
    public boolean persist(E entity)
            throws IllegalAccessException, SQLException {
        Field primaryKey = getId(entity.getClass());
        primaryKey.setAccessible(true);
        Object value = primaryKey.get(entity);

        //    TODO: how dealt back when passing the field of primary key instead of the value? (given as example in problem)
        if (value == null || (long) value <= 0) {
            return doInsert(entity);
        }

        return doUpdate(entity, value);
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

    private boolean doInsert(E entity)
            throws SQLException, IllegalAccessException {

        String tableName = getTableName(entity.getClass());

        Map<String, Object> columnNamesAndValuesMap = getColumnNamesAndValues(entity);

        String[] columnNames = columnNamesAndValuesMap.keySet().toArray(String[]::new);
        String columnsSql = formatQueryListing(columnNames, QueryFormatter::formatSQLAddressPart);

        Object[] columnValues = columnNamesAndValuesMap.values().toArray(Object[]::new);
        String valuesSql = formatQueryListing(columnValues, QueryFormatter::formatSqlValue);

        int updateCount = conn.prepareStatement(String.format(
                        QueryTemplates.INSERT_SINGLE_ROW_TABLE_COLUMNS_VALUES,
                        tableName, columnsSql, valuesSql))
                .executeUpdate();

        return updateCount != 0;
    }

    private boolean doUpdate(E entity, Object idValue)
            throws IllegalAccessException, SQLException {
        String tableQuery = QueryFormatter.formatSQLAddressPart(
                getTableName(entity.getClass()));
        String idQuery = QueryFormatter.formatSqlValue(idValue);

        Map<String, Object> columnNamesAndValuesMap = getColumnNamesAndValues(entity);
        String setQueryListing = updateSetQueryListing(columnNamesAndValuesMap);

        String query = String.format(
                QueryTemplates.UPDATE_TABLE_SET_LIST_WHERE_ID,
                tableQuery, setQueryListing, idQuery);

        int updatedRows = conn.prepareStatement(query).executeUpdate();

        return updatedRows != 0;
    }

    private static Field getId(Class<?> entityClass) {

        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));
    }

    private static String getTableName(Class<?> entityClass) {

        return entityClass.getAnnotation(Entity.class)
                .name();
    }

//    TODO: w/out static <E> hides param E ?
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

    private static String updateSetQueryListing(Map<String, Object> columnWithValueMap) {
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
