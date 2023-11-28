package gym.service;

import gym.entities.Trainee;

import javax.naming.AuthenticationException;
import java.util.Date;

public interface TraineeService{
    Trainee create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);
    Trainee update(Credentials credentials, Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) throws AuthenticationException;
    boolean delete(Credentials credentials, Long id) throws AuthenticationException;
    boolean deleteByUsername(Credentials credentials, String username) throws AuthenticationException;
    Trainee select(Long id);
    Trainee selectByUsername(String username);

    Trainee updatePassword(Credentials credentials, Long id, String newPassword) throws AuthenticationException;

    Trainee updateStatus(Credentials credentials, Long id, boolean active) throws AuthenticationException;


}
