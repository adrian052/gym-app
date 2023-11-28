package gym.service;

import gym.entities.Trainer;

import javax.naming.AuthenticationException;

public interface TrainerService {
    Trainer create(String firstName, String lastName, boolean isActive, Long specialization);
    Trainer update(Credentials credentials, Long id, String firstName, String lastName, boolean isActive, Long specialization) throws AuthenticationException;
    Trainer select(Long id);
}
