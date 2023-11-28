package gym.dao;

import gym.entities.Trainee;

public interface TraineeDao extends CRUD<Trainee>{
    Trainee findByUsername(String username);
}
