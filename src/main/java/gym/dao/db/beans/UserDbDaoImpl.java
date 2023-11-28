package gym.dao.db.beans;

import gym.dao.db.DataBaseDao;
import gym.service.ValidationUtil;
import gym.dao.db.UserDbDao;
import gym.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Repository
@Profile("db")
@Transactional
public class UserDbDaoImpl extends DataBaseDao<User> implements UserDbDao {

    @Override
    protected Class<User> getIdentifieableClass() {
        return User.class;
    }
    public User findByUsername(String username) {
        ValidationUtil.validateNotNull(Map.of("username",username));
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (Exception e) {
            logger.error("Error in userPasswordMatching method", e);
            return null;
        }
    }
}
