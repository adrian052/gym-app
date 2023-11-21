package gym.service;

import gym.entities.Trainer;

import java.util.Date;

public interface TrainerService {
    Trainer create(String firstName, String lastName, boolean isActive, Long specialization);
    Trainer update(Long id, String firstName, String lastName, boolean isActive, Long specialization);
    Trainer select(Long id);
}
