package gym.dao;

import gym.entities.User;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class UserDAO extends DataAccessObject<User> {
    @Override
    protected Map<Long, User> getEntityMap() {
        return storage.getUsers();
    }

    @Override
    protected boolean hasReferentialIntegrity(User user) {
        return true;
    }

    @Override
    protected boolean validateNotNull(User user) {
        return user.getFirstName()!= null && user.getLastName()!=null
                && user.getUsername() != null && user.getPassword() != null;
    }

}