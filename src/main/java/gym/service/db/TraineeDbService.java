package gym.service.db;

import gym.entities.Trainee;
import gym.service.Credentials;
import gym.service.TraineeService;
import javax.naming.AuthenticationException;

public interface TraineeDbService extends TraineeService {
    boolean deleteByUsername(Credentials credentials, String username) throws AuthenticationException;

    Trainee selectByUsername(String username);


    Trainee updatePassword(Credentials credentials, Long id, String newPassword) throws AuthenticationException;

    Trainee updateStatus(Credentials credentials, Long id, boolean newStatus) throws AuthenticationException;

}
