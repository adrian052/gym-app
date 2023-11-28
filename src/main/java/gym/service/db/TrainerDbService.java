package gym.service.db;

import gym.entities.Trainer;
import gym.service.Credentials;
import gym.service.TrainerService;
import javax.naming.AuthenticationException;

public interface TrainerDbService extends TrainerService {

    Trainer selectByUsername(String username);

    Trainer updatePassword(Credentials credentials, Long id, String newPassword) throws AuthenticationException;

    Trainer updateStatus(Credentials credentials, Long id, boolean newStatus) throws AuthenticationException;
}
