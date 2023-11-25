package gym.dao.db;

import gym.entities.Training;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("db")
public class TrainingDbDao extends DataBaseDao<Training> {
    @Override
    protected Class<Training> getIdentifieableClass() {
        return Training.class;
    }
}
