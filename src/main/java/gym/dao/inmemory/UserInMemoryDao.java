package gym.dao.inmemory;

import gym.dao.UserDao;
import gym.entities.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@Profile("memory")
public class UserInMemoryDao extends InMemoryDao<User> implements UserDao {
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

    @Override
    public User findByUsername(String username) {
        /*TODO*/
        return null;
    }
}