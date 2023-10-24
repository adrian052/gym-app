package gym.service.implementations;


import gym.dao.GenericDAO;
import gym.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class UserCreationService {
    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(UserCreationService.class);

    public static User createUser(String firstName, String lastName, boolean isActive, GenericDAO<User> userDAO) {
        if (firstName == null || lastName == null) {
            logger.error("Failed to create the User: the following parameters cannot be null (firstName, lastName)");
            return null;
        }

        String username = generateUniqueUsername(firstName, lastName, userDAO);
        String password = generateRandomPassword();

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setActive(isActive);
        user.setUsername(username);
        user.setPassword(password);
        userDAO.save(user);

        return user;
    }

    private static String generateUniqueUsername(String firstName, String lastName, GenericDAO<User> userDAO) {
        String baseUsername = firstName + "." + lastName;
        String generatedUsername = baseUsername;

        while (usernameAlreadyExist(generatedUsername, userDAO)) {
            generatedUsername = baseUsername + "." + generateRandomNumber();
        }

        return generatedUsername;
    }

    private static boolean usernameAlreadyExist(String username, GenericDAO<User> userDAO) {
        for (User user : userDAO.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    private static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    private static String generateRandomNumber() {
        String characters = "0123456789";
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            randomNumber.append(characters.charAt(index));
        }
        return randomNumber.toString();
    }
}