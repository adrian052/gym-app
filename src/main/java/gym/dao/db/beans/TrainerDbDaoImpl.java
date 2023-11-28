package gym.dao.db.beans;

import gym.dao.db.DataBaseDao;
import gym.service.ValidationUtil;
import gym.dao.db.TrainerDbDao;
import gym.entities.Trainer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Profile("db")
public class TrainerDbDaoImpl extends DataBaseDao<Trainer> implements TrainerDbDao {
    @Override
    protected Class<Trainer> getIdentifieableClass() {
        return Trainer.class;
    }

    @Override
    public Trainer findByUsername(String username) {
        ValidationUtil.validateNotNull(Map.of("username",username));
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT t FROM Trainer t JOIN FETCH t.user u WHERE u.username = :username";
            Query<Trainer> query = session.createQuery(hql, Trainer.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            logger.error("Error in findByUsername method", e);
            return null;
        }
    }
}
