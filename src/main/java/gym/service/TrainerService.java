package gym.service;

import gym.entities.Trainer;

import javax.naming.AuthenticationException;
import java.util.Date;

public interface TrainerService {
    Trainer create(String firstName, String lastName, boolean isActive, Long specialization);
    Trainer update(Credentials credentials, Long id, String firstName, String lastName, boolean isActive, Long specialization) throws AuthenticationException;
    Trainer select(Long id);
    Trainer selectByUsername(String id);
    Trainer updatePassword(Credentials credentials, Long id, String newPassword) throws AuthenticationException;
    Trainer updateStatus(Credentials credentials, Long id, boolean active) throws AuthenticationException;
}
