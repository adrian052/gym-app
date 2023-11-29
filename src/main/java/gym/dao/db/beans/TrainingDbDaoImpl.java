package gym.dao.db.beans;

import gym.dao.db.DataBaseDao;
import gym.dao.db.TrainingDbDao;
import gym.service.ValidationUtil;
import gym.entities.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Profile("db")
public class TrainingDbDaoImpl extends DataBaseDao<Training> implements TrainingDbDao {
    @Override
    protected Class<Training> getIdentifieableClass() {
        return Training.class;
    }

    @Override
    public List<Training> getTraineeTrainingsByUsername(String traineeUsername) {
        ValidationUtil.validateNotNull("traineeUsername",traineeUsername);
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT t FROM Training t JOIN FETCH t.trainee tr JOIN FETCH tr.user u WHERE u.username = :username";
            Query<Training> query = session.createQuery(hql, Training.class);
            query.setParameter("username", traineeUsername);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error in getTraineeTrainingsByUsername method", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Training> getTrainerTrainingsByUsername(String trainerUsername) {
        ValidationUtil.validateNotNull("trainerUsername",trainerUsername);
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT t FROM Training t JOIN FETCH t.trainer tr JOIN FETCH tr.user u WHERE u.username = :username";
            Query<Training> query = session.createQuery(hql, Training.class);
            query.setParameter("username", trainerUsername);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error in getTrainerTrainingsByUsername method", e);
            return new ArrayList<>();
        }
    }
}
