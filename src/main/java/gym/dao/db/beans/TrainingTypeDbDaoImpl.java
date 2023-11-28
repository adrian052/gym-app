package gym.dao.db.beans;

import gym.dao.db.TrainingTypeDbDao;
import gym.dao.db.DataBaseDao;
import gym.entities.TrainingType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
@Repository
@Profile("db")
public class TrainingTypeDbDaoImpl extends DataBaseDao<TrainingType> implements TrainingTypeDbDao {
    @Override
    protected Class<TrainingType> getIdentifieableClass() {
        return TrainingType.class;
    }
}
