package jay.io.primefacesdemo.users.entity;

import java.util.List;
import java.util.Optional;

public interface EntityService {

    /**
     * Persists a new entity instance to the database.
     *
     * @param entity The entity to be created.
     * @param <T>    The type of the entity.
     */
    <T> void create(T entity);

    /**
     * Persists a list of entity instances to the database in batch.
     *
     * @param entities The list of entities to be persisted.
     * @param <T>      The type of the entities.
     */
    <T> void createAll(List<T> entities);

    /**
     * Updates an existing entity instance in the database.
     *
     * @param entity The entity instance to be updated.
     * @param <T>    The type of the entity.
     */
    <T> void update(T entity);

    /**
     * Deletes a given entity instance from the database.
     *
     * @param entity The entity instance to be deleted.
     * @param <T>    The type of the entity.
     */
    <T> void remove(T entity);

    /**
     * Deletes a batch of entity instances from the database.
     *
     * @param entities The list of entity instances to be deleted.
     * @param <T>      The type of the entities.
     */
    <T> void removeAll(List<T> entities);

    /**
     * Finds an entity instance by primary key.
     *
     * @param id          The primary key of the entity.
     * @param entityClass The class of the entity.
     * @param <T>         The type of the entity.
     * @return An optional containing the entity if found, otherwise empty.
     */
    <T> Optional<T> lookup(Object id, Class<T> entityClass);

    /**
     * Retrieves all records of the specified entity class.
     *
     * @param entityClass The class of the entity.
     * @param <T>         The type of the entity.
     * @return A list of entities.
     */
    <T> List<T> listAll(Class<T> entityClass);

    /**
     * Retrieves paginated records of a specific entity.
     *
     * @param entityClass The class of the entity.
     * @param offset      The start index of records.
     * @param limit       The maximum number of records to return.
     * @param <T>         The type of the entity.
     * @return A list of entities for the given range.
     */
    <T> List<T> listPaginated(Class<T> entityClass, int offset, int limit);

    /**
     * Executes a JPQL query with optional parameters.
     *
     * @param jpqlQuery   The JPQL query string.
     * @param entityClass The expected entity result class.
     * @param parameters  Parameters to bind in the query.
     * @param <T>         The type of the entity.
     * @return A list of query results.
     */
    <T> List<T> executeQuery(String jpqlQuery, Class<T> entityClass, Object... parameters);

    /**
     * Executes a native SQL query returning raw object arrays.
     *
     * @param nativeQuery The SQL query string.
     * @param parameters  Parameters to bind in the query.
     * @return A list of object arrays.
     */
    List<Object[]> executeNativeQuery(String nativeQuery, Object... parameters);

    /**
     * Executes a native SQL query returning mapped entities.
     *
     * @param nativeQuery The SQL query string.
     * @param clazz       The result entity class.
     * @param parameters  Parameters to bind in the query.
     * @param <T>         The type of the entity.
     * @return A list of entity results.
     */
    <T> List<T> executeNativeQuery(String nativeQuery, Class<T> clazz, Object... parameters);

    /**
     * Executes an update/delete query and returns affected row count.
     *
     * @param query      The JPQL/SQL query string.
     * @param parameters Parameters to bind in the query.
     * @return The number of rows affected.
     */
    int executeUpdate(String query, Object... parameters);

    /**
     * Counts the total number of rows for a given entity class.
     *
     * @param entityClass The class of the entity.
     * @param <T>         The type of the entity.
     * @return The total count.
     */
    <T> long count(Class<T> entityClass);
}
