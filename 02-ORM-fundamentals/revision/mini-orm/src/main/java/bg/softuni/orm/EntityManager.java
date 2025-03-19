package bg.softuni.orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

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

        if (value == null || (long) value < 0) {
            return doInsert(entity, primaryKey);
        }

        return doUpdate(entity, primaryKey);
    }

    private boolean doUpdate(E entity, Field primaryKey) {
        return false;
    }

    private boolean doInsert(E entity, Field primaryKey) throws SQLException {
        // Getting table name from @Entity.name() anno element
        String tableName = this.getTableName(entity.getClass());

        // INSERT INTO t1(c1, c2, c3) VALUES (val1, val2, val3);
        String sql = "INSERT INTO " + tableName + "()";
        // get fields
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        // iterate and collect: 1. @Column.name(), 2. Field's value (change of access modifier);
        // then add the data to the sql query


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
}
