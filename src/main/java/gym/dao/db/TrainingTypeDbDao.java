package gym.dao.db;

import gym.entities.TrainingType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
@Repository
@Profile("db")
public class TrainingTypeDbDao extends DataBaseDao<TrainingType> {

    @Override
    protected Class<TrainingType> getIdentifieableClass() {
        return TrainingType.class;
    }
}
