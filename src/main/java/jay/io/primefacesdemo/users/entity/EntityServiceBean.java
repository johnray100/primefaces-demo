package jay.io.primefacesdemo.users.entity;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

@Stateless
public class EntityServiceBean implements EntityService {

    @PersistenceContext(unitName = "userPU")
    private EntityManager entityManager;

    @Override
    public <T> void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public <T> void createAll(List<T> entities) {
        for (T entity : entities) {
            entityManager.persist(entity);
        }
    }

    @Override
    public <T> void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public <T> void remove(T entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    @Override
    public <T> void removeAll(List<T> entities) {
        for (T entity : entities) {
            entityManager.remove(entityManager.merge(entity));
        }
    }

    @Override
    public <T> Optional<T> lookup(Object id, Class<T> entityClass) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public <T> List<T> listAll(Class<T> entityClass) {
        String query = "SELECT e FROM " + entityClass.getName() + " e";
        return entityManager.createQuery(query, entityClass).getResultList();
    }

    @Override
    public <T> List<T> listPaginated(Class<T> entityClass, int offset, int limit) {
        String query = "SELECT e FROM " + entityClass.getName() + " e";
        return entityManager.createQuery(query, entityClass)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public <T> List<T> executeQuery(String jpqlQuery, Class<T> entityClass, Object... parameters) {
        Query query = entityManager.createQuery(jpqlQuery, entityClass);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i + 1, parameters[i]);
        }
        return query.getResultList();
    }

    @Override
    public List<Object[]> executeNativeQuery(String nativeQuery, Object... parameters) {
        Query query = entityManager.createNativeQuery(nativeQuery);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i + 1, parameters[i]);
        }
        return query.getResultList();
    }

    @Override
    public <T> List<T> executeNativeQuery(String nativeQuery, Class<T> clazz, Object... parameters) {
        Query query = entityManager.createNativeQuery(nativeQuery, clazz);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i + 1, parameters[i]);
        }
        return query.getResultList();
    }

    @Override
    public int executeUpdate(String query, Object... parameters) {
        Query jpaQuery = entityManager.createQuery(query);
        for (int i = 0; i < parameters.length; i++) {
            jpaQuery.setParameter(i + 1, parameters[i]);
        }

        try {
            return jpaQuery.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public <T> long count(Class<T> entityClass) {
        String query = "SELECT COUNT(e) FROM " + entityClass.getName() + " e";
        return entityManager.createQuery(query, Long.class).getSingleResult();
    }
}
