package bg.softuni.orm;

import bg.softuni.orm.annotations.Column;
import bg.softuni.orm.annotations.Entity;
import bg.softuni.orm.annotations.Id;
import bg.softuni.orm.utils.QueryFormatter;
import bg.softuni.orm.utils.QueryTemplates;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
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
    public Iterable<E> find(Class<E> table)
            throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException,
            InstantiationException {

        return find(table, "");
    }

    @Override
    public Iterable<E> find(Class<E> table, String where)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {

        Statement stmt = conn.createStatement();
        String sql = String.format(
                QueryTemplates.SELECT_TABLE_WHERE_CLAUSE,
                getTableName(table), where
        );

        ResultSet rs = stmt.executeQuery(sql);
        List<E> entities = new ArrayList<>();

        while (rs.next()) {
            E e = table.getDeclaredConstructor().newInstance();
            fillEntity(table, rs, e);
            entities.add(e);
        }

        return entities;
    }

    @Override
    public E findFirst(Class<E> table)
            throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException {

        return findFirst(table, "");
    }

    @Override
    public E findFirst(Class<E> table, String where)
            throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {

        Statement stmt = conn.createStatement();
        String tableName = getTableName(table);
        String sql = String.format(QueryTemplates.SELECT_FIRST_TABLE_WHERE_CLAUSE,
                tableName, where == null ? "" : where);

        ResultSet rs = stmt.executeQuery(sql);
        E entity = table.getDeclaredConstructor().newInstance();

        rs.next();
        fillEntity(table, rs, entity);

        return entity;
    }

    @Override
    public void doCreate(Class<E> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);

        String sqlColumnDefinitionListing = Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotation(Column.class) != null)
                .map(this::getColumnDefinition)
                .collect(Collectors.joining("," + System.lineSeparator()));

        String createTableSql = String.format(
                QueryTemplates.CREATE_TABLE_ID_AUTO_INCREMENT_COL_DEFINITION_LISTING,
                QueryFormatter.formatSQLAddressPart(tableName), sqlColumnDefinitionListing
        );

        Statement stmt = conn.createStatement();
        stmt.execute(createTableSql);
    }

    //    TODO: alter cases logic
    @Override
    @SuppressWarnings("unchecked")
    public void doAlter(E entity) throws SQLException {
        Class<E> entityClass = (Class<E>) entity.getClass();
        String tableName = getTableName(entityClass);

        String alterAddColumnsSql = String.format(
                QueryTemplates.ALTER_TABLE_ADD_COLUMNS,
                tableName, getNewColumnDefinitionsListings(entityClass)
        );

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(alterAddColumnsSql);
    }

    private String getNewColumnDefinitionsListings(Class<E> entityClass) throws SQLException {

        Set<String> existingColLabels = getExistingColumnLabelsFromTable(getTableName(entityClass));

        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !existingColLabels.contains(getColumnLabelFromField(f)))
                .map(this::getColumnDefinition)
                .collect(Collectors.joining(", "));
    }

    private Set<String> getExistingColumnLabelsFromTable(String tableName) throws SQLException {

        Set<String> existingColumnLabels = new HashSet<>();

        PreparedStatement prStmt = conn.prepareStatement(QueryTemplates.PR_STMT_COLUMN_LABELS_FOR_TABLE);
        prStmt.setString(1, tableName);
        ResultSet rs = prStmt.executeQuery();

        while (rs.next()) {
            existingColumnLabels.add(rs.getString("COLUMN_NAME"));
        }

        return existingColumnLabels;
    }

    private String getColumnDefinition(Field declaredField) {
        declaredField.setAccessible(true);

        String columnLabel = getColumnLabelFromFieldWithColumnAnno(declaredField);
        Class<?> type = declaredField.getType();

        return String.format(
                QueryTemplates.COLUMN_DEFINITION,
                QueryFormatter.formatSQLAddressPart(columnLabel),
                QueryFormatter.formatToSQLDataType(type)
        );
    }

    private void fillEntity(Class<E> table, ResultSet rs, E entity)
            throws SQLException, IllegalAccessException {

        for (Field field : table.getDeclaredFields()) {
            fillField(entity, field, rs);
        }
    }

    private void fillField(E entity, Field field, ResultSet rs)
            throws SQLException, IllegalAccessException {

        String columnLabel = getColumnLabelFromField(field);
        if (columnLabel == null) return;

        field.setAccessible(true);
        Class<?> type = field.getType();

//        TODO: blob, text, timestamp, year and other SQL data types

        if (type == short.class || type == Short.class) {
            field.set(entity, rs.getShort(columnLabel));
        } else if (type == int.class || type == Integer.class) {
            field.set(entity, rs.getInt(columnLabel));
        } else if (type == long.class || type == Long.class) {
            field.set(entity, rs.getLong(columnLabel));
        } else if (type == float.class || type == Float.class) {
            field.set(entity, rs.getFloat(columnLabel));
        } else if (type == double.class || type == Double.class) {
            field.set(entity, rs.getDouble(columnLabel));
        } else if (type == BigDecimal.class) {
            field.set(entity, rs.getBigDecimal(columnLabel));
        } else if (type == LocalDate.class) {
            field.set(entity, LocalDate.parse(rs.getString(columnLabel)));
        } else {
            field.set(entity, rs.getString(columnLabel));
        }
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
            Object value = field.get(entity);

            String columnName = columnAnnotation.name();
            mapColumnValue.put(getColumnLabelFromFieldWithColumnAnno(field), value);
        }

        return mapColumnValue;
    }

    private static String getColumnLabelFromField(Field field) {
//        presumably we haven't got 2 id fields on class, 2 id pk columns on table(?!), field with @Id and @Column
//        prioritizing @Id over @Column...

        Id idAnno = field.getAnnotation(Id.class);
        if (idAnno != null) return field.getName();

        return getColumnLabelFromFieldWithColumnAnno(field);
    }

    private static String getColumnLabelFromFieldWithColumnAnno(Field field) {
        Column columnAnno = field.getAnnotation(Column.class);
        if (columnAnno == null) return null;

        String columnAnnoName = columnAnno.name();
        return columnAnnoName.isEmpty() ? field.getName() : columnAnnoName;
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
