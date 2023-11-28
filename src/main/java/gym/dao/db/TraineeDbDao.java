package gym.dao.db;

import gym.service.simple.ValidationUtil;
import gym.dao.TraineeDao;
import gym.entities.Trainee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Profile("db")
public class TraineeDbDao extends DataBaseDao<Trainee> implements TraineeDao {
    @Override
    protected Class<Trainee> getIdentifieableClass() {
        return Trainee.class;
    }
    @Override
    public Trainee findByUsername(String username) {
        ValidationUtil.validateNotNull(Map.of("username",username));
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT t FROM Trainee t JOIN FETCH t.user u WHERE u.username = :username";
            Query<Trainee> query = session.createQuery(hql, Trainee.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            logger.error("Error in findByUsername method", e);
            throw e;
        }
    }
}
