package gym.service.auth;

import gym.dao.DataAccessObject;
import gym.dao.db.UserDbDao;
import gym.entities.User;
import gym.service.AuthService;
import gym.service.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    private UserDbDao userDao;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired(required = false)
    public void setUserDao(UserDbDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void authorize(Credentials credentials, User userOwner) throws AuthenticationException {
        if (userDao ==null){
            /*Returns because findByUsername is not implemented for inmemoryDAO*/
            return;
        }
        User credentialsUser = userDao.findByUsername(credentials.username());
        if (credentialsUser != null) {
            if (userOwner.equals(credentialsUser)) {
                logger.info("User successfully authorized");
                return;
            }
            logger.warn("The user is not authorized to perform this action");
            throw new AuthenticationException("The user is not authorized to perform this action");
        }

        logger.warn("The username doesn't exist");
        throw new AuthenticationException("Username does not exist");
    }

    public void authenticate(Credentials credentials) throws AuthenticationException {
        if (userDao ==null){
            /*Returns because findByUsername is not implemented for inmemoryDAO*/
            return;
        }
        User credentialsUser = userDao.findByUsername(credentials.username());
        if (credentialsUser != null) {
            if (Objects.equals(credentialsUser.getPassword(), credentials.password())) {
                logger.info("User successfully authenticated");
                return;
            }
            logger.warn("The username/password doesn't match");
            throw new AuthenticationException("Username/password doesn't match");
        }
        logger.warn("The username doesn't exist");
        throw new AuthenticationException("Username does not exist");
    }

    public void authenticationFlow(Credentials credentials, User userOwner) throws AuthenticationException {
        authenticate(credentials);
        authorize(credentials, userOwner);
    }
}
