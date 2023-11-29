package gym.dao.db;

import gym.entities.Identifiable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public abstract class DataBaseDao<T extends Identifiable> {

    protected final Logger logger = LoggerFactory.getLogger(getIdentifieableClass());

    protected SessionFactory sessionFactory;

    @Autowired
    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT e FROM " + getIdentifieableClass().getName() + " e", getIdentifieableClass()).getResultList();
        } catch (Exception e) {
            logger.error("Error in findAll method", e);
            throw e;
        }
    }


    public T findById(Long id) {
        validateNotNull("id",id);
        try (Session session = sessionFactory.openSession()) {
            return session.get(getIdentifieableClass(), id);
        } catch (Exception e) {
            logger.error("Error in findById method", e);
            throw e;
        }
    }


    public T save(T entity) {
        validateNotNull("entity",entity);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            logger.info("Saved entity: {}", entity);
        } catch (Exception e) {
            logger.error("Error in save method", e);
            throw e;
        }
        return entity;
    }

    public boolean delete(Long id) {
        validateNotNull("id",id);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            T entity = session.get(getIdentifieableClass(), id);
            if (entity != null) {
                session.remove(entity);
                transaction.commit();
                logger.info("Deleted entity by ID: {}", id);
                return true;
            } else {
                transaction.rollback();
                logger.warn("Entity not found for deletion by ID: {}", id);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error in delete method", e);
            throw e;
        }
    }

    public void validateNotNull(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Number of arguments must be even");
        }

        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = (String) keyValuePairs[i];
            Object value = keyValuePairs[i + 1];

            if (value == null) {
                String errorMessage = String.format("%s cannot be null", key);
                logger.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }
        }
    }

    protected abstract Class<T> getIdentifieableClass();
}