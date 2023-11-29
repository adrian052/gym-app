package gym.service.db.beans;

import gym.dao.db.TrainerDbDao;
import gym.entities.Trainer;
import gym.entities.User;
import gym.service.Credentials;
import gym.service.ValidationUtil;
import gym.service.db.TrainerDbService;
import gym.service.simple.TrainerServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Map;

@Service
@Profile("db")
public class TrainerDbServiceImpl extends TrainerServiceImpl implements TrainerDbService {
    @Override
    public Trainer selectByUsername(String username) {
        ValidationUtil.validateNotNull("username",username);
        return ((TrainerDbDao)trainerDAO).findByUsername(username);
    }

    @Override
    public Trainer updatePassword(Credentials credentials, Long id, String newPassword) throws AuthenticationException {
        ValidationUtil.validateNotNull("credentials",credentials,"id",id, "newPassword",newPassword);
        Trainer trainer = trainerDAO.findById(id);
        User user = trainer.getUser();
        authService.authenticationFlow(credentials, user);
        user.setPassword(newPassword);
        trainerDAO.save(trainer);
        return trainer;
    }

    @Override
    public Trainer updateStatus(Credentials credentials, Long id, boolean newStatus) throws AuthenticationException {
        ValidationUtil.validateNotNull("credentials",credentials,"id",id);
        Trainer trainer = trainerDAO.findById(id);
        User user = trainer.getUser();
        authService.authenticationFlow(credentials,user);
        if (user.isActive() == newStatus) {
            logger.info("No change in status. Trainer ID: {}, Current Status: {}", id, newStatus);
        } else {
            user.setActive(newStatus);
            userDAO.save(user);
            logger.info("Status updated successfully. Trainer ID: {}, New Status: {}", id, newStatus);
        }
        return trainer;
    }

}
