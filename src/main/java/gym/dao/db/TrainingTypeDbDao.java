package gym.dao.db;

import gym.dao.TrainingTypeDao;
import gym.entities.TrainingType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
@Repository
@Profile("db")
public class TrainingTypeDbDao extends DataBaseDao<TrainingType> implements TrainingTypeDao {
    @Override
    protected Class<TrainingType> getIdentifieableClass() {
        return TrainingType.class;
    }
}
