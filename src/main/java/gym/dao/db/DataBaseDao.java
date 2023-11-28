package gym.dao.db;

import gym.service.ValidationUtil;
import gym.entities.Identifiable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

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
        ValidationUtil.validateNotNull(Map.of("id",id));
        try (Session session = sessionFactory.openSession()) {
            return session.get(getIdentifieableClass(), id);
        } catch (Exception e) {
            logger.error("Error in findById method", e);
            throw e;
        }
    }


    public T save(T entity) {
        ValidationUtil.validateNotNull(Map.of("entity",entity));
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
        ValidationUtil.validateNotNull(Map.of("id",id));
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

    protected abstract Class<T> getIdentifieableClass();
}