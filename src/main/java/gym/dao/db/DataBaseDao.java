package gym.dao.db;

import gym.dao.DataAccessObject;
import gym.entities.Identifiable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class DataBaseDao<T extends Identifiable> implements DataAccessObject<T> {

    private final Logger logger = LoggerFactory.getLogger(getIdentifieableClass());

    private SessionFactory sessionFactory;

    @Autowired
    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT e FROM " + getIdentifieableClass().getName() + " e", getIdentifieableClass()).getResultList();
        } catch (Exception e) {
            logger.error("Error in findAll method", e);
            throw e;
        }
    }

    @Override
    public T findById(Long id) {
        checkNotNull(id);
        try (Session session = sessionFactory.openSession()) {
            return session.get(getIdentifieableClass(), id);
        } catch (Exception e) {
            logger.error("Error in findById method", e);
            throw e;
        }
    }

    @Override
    public T save(T entity) {
        checkNotNull(entity);
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

    @Override
    public boolean delete(Long id) {
        checkNotNull(id);
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

    private void checkNotNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                logger.error("DAO arguments must not be null");
                throw new IllegalArgumentException("DAO arguments must not be null");
            }
        }
    }

    protected abstract Class<T> getIdentifieableClass();
}