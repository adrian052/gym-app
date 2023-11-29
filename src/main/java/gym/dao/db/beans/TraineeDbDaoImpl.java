package gym.dao.db.beans;

import gym.dao.db.TraineeDbDao;
import gym.dao.db.DataBaseDao;
import gym.dao.db.TrainerDbDao;
import gym.entities.Training;
import gym.service.ValidationUtil;
import gym.entities.Trainee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("db")
public class TraineeDbDaoImpl extends DataBaseDao<Trainee> implements TraineeDbDao {
    @Override
    protected Class<Trainee> getIdentifieableClass() {
        return Trainee.class;
    }

    TraineeDbDao traineeDao;

    @Autowired
    public void setTraineeDao(TraineeDbDao traineeDao){
        this.traineeDao = traineeDao;
    }

    @Override
    public Trainee findByUsername(String username) {
        ValidationUtil.validateNotNull("username",username);
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT t FROM Trainee t JOIN FETCH t.user u WHERE u.username = :username";
            Query<Trainee> query = session.createQuery(hql, Trainee.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            logger.error("Error in findByUsername method", e);
            return null;
        }
    }
}
