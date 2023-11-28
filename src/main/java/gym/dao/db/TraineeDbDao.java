package gym.dao.db;

import gym.dao.DataAccessObject;
import gym.entities.Trainee;

public interface TraineeDbDao extends DataAccessObject<Trainee> {
    Trainee findByUsername(String username);
}
