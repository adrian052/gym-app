package gym.dao.db;

import gym.entities.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
@Repository
@Profile("db")
public class UserDbDao extends DataBaseDao<User> {

    @Override
    protected Class<User> getIdentifieableClass() {
        return User.class;
    }
}
