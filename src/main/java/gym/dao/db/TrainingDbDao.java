package gym.dao.db;

import gym.service.simple.ValidationUtil;
import gym.dao.TrainingDao;
import gym.entities.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
@Profile("db")
public class TrainingDbDao extends DataBaseDao<Training> implements TrainingDao {
    @Override
    protected Class<Training> getIdentifieableClass() {
        return Training.class;
    }

    @Override
    public List<Training> getTraineeTrainingsByUsername(String traineeUsername) {
        ValidationUtil.validateNotNull(Map.of("traineeUsername",traineeUsername));
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT t FROM Training t JOIN FETCH t.trainee tr JOIN FETCH tr.user u WHERE u.username = :username";
            Query<Training> query = session.createQuery(hql, Training.class);
            query.setParameter("username", traineeUsername);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error in getTraineeTrainingsByUsername method", e);
            throw e;
        }
    }

    @Override
    public List<Training> getTrainerTrainingsByUsername(String trainerUsername) {
        ValidationUtil.validateNotNull(Map.of("trainerUsername",trainerUsername));
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT t FROM Training t JOIN FETCH t.trainer tr JOIN FETCH tr.user u WHERE u.username = :username";
            Query<Training> query = session.createQuery(hql, Training.class);
            query.setParameter("username", trainerUsername);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error in getTrainerTrainingsByUsername method", e);
            throw e;
        }
    }
}
