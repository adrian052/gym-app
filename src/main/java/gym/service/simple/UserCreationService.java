package gym.service.simple;

import gym.dao.DataAccessObject;
import gym.entities.User;
import gym.service.ValidationUtil;

import java.util.Map;
import java.util.Random;

public class UserCreationService {
    private UserCreationService(){
    }
    private static final Random random = new Random();

    public static User createUser(String firstName, String lastName, boolean isActive, DataAccessObject<User> userDAO) {
        ValidationUtil.validateNotNull(Map.of("firstName",firstName, "lastName",lastName));
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

    private static String generateUniqueUsername(String firstName, String lastName, DataAccessObject<User> userDAO) {
        String baseUsername = firstName + "." + lastName;
        String generatedUsername = baseUsername;

        while (usernameAlreadyExist(generatedUsername, userDAO)) {
            generatedUsername = baseUsername + "." + generateRandomNumber();
        }

        return generatedUsername;
    }

    private static boolean usernameAlreadyExist(String username, DataAccessObject<User> userDAO) {
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