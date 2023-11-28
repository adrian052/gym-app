package gym.service.db.beans;

import gym.dao.db.TraineeDbDao;
import gym.dao.db.UserDbDao;
import gym.entities.Trainee;
import gym.entities.User;
import gym.service.Credentials;
import gym.service.ValidationUtil;
import gym.service.db.TraineeDbService;
import gym.service.simple.TraineeServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Map;

@Service
@Profile("db")
public class TraineeDbServiceImpl extends TraineeServiceImpl implements TraineeDbService{
    @Override
    public boolean deleteByUsername(Credentials credentials, String username) throws AuthenticationException {
        ValidationUtil.validateNotNull(Map.of("credentials",credentials,"username",username));
        User user = ((UserDbDao)userDAO).findByUsername(username);
        authService.authenticationFlow(credentials,user);
        return traineeDAO.delete(user.getId());
    }

    @Override
    public Trainee selectByUsername(String username) {
        ValidationUtil.validateNotNull(Map.of("username", username));
        return ((TraineeDbDao)traineeDAO).findByUsername(username);
    }

    @Override
    public Trainee updatePassword(Credentials credentials, Long id, String newPassword) throws AuthenticationException {
        ValidationUtil.validateNotNull(Map.of("credentials",credentials,"id",id,"newPassword",newPassword));
        Trainee trainee = traineeDAO.findById(id);
        User traineeUser = trainee.getUser();
        authService.authenticationFlow(credentials,traineeUser);
        traineeUser.setPassword(newPassword);
        traineeDAO.save(trainee);
        return trainee;
    }

    @Override
    public Trainee updateStatus(Credentials credentials, Long id, boolean newStatus) throws AuthenticationException {
        ValidationUtil.validateNotNull(Map.of("id",id,"credentials",credentials));
        Trainee trainee = traineeDAO.findById(id);
        User user = trainee.getUser();
        authService.authenticationFlow(credentials,user);
        if (user.isActive() == newStatus) {
            logger.info("No change in status. Trainer ID: {}, Current Status: {}", id, newStatus);
        } else {
            user.setActive(newStatus);
            userDAO.save(user);
            logger.info("Status updated successfully. Trainer ID: {}, New Status: {}", id, newStatus);
        }
        return trainee;
    }
}
