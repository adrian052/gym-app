package gym.service;

import gym.entities.Trainee;

import javax.naming.AuthenticationException;
import java.util.Date;

public interface TraineeService{
    Credentials create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);
    Trainee update(Credentials credentials, Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) throws AuthenticationException;
    boolean delete(Credentials credentials, Long id) throws AuthenticationException;
    Trainee select(Long id);



}
