package gym.service.interfaces;

import gym.entities.Trainer;

import java.util.Date;

public interface TrainerService {
    Long create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address, Long specialization);
    boolean update(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address, Long specialization);
    Trainer select(Long id);
}
