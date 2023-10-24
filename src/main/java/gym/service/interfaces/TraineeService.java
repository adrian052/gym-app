package gym.service.interfaces;

import gym.entities.Trainee;

import java.util.Date;

public interface TraineeService {
    Long create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);
    boolean update(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);
    boolean delete(Long id);
    Trainee select(Long id);
}
