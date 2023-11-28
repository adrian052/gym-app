package gym.dao;

import gym.entities.Trainer;

public interface TrainerDao extends CRUD<Trainer>{
    Trainer findByUsername(String username);
}
