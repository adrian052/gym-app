package gym.dao.db;

import gym.entities.Trainee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("db")
public class TraineeDbDao extends DataBaseDao<Trainee> {
    @Override
    protected Class<Trainee> getIdentifieableClass() {
        return Trainee.class;
    }
}
