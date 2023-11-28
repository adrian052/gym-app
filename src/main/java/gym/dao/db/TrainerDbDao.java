package gym.dao.db;

import gym.dao.DataAccessObject;
import gym.entities.Trainer;

public interface TrainerDbDao extends DataAccessObject<Trainer> {
    Trainer findByUsername(String username);
}
