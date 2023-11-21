package gym.service;

import gym.entities.Trainee;

import java.util.Date;

public interface TraineeService {
    Trainee create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);
    Trainee update(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);
    boolean delete(Long id);
    Trainee select(Long id);
}
