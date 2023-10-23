package gym.service;

import gym.dao.UserDAO;
import gym.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService implements GymService<User> {
    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> selectAll() {
        return userDAO.findAll();
    }

    @Override
    public User select(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public Long create(User user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("User fields (FirstName, LastName, Username, Password) must not be null.");
        }

        long newUserId = generateNewUserId();
        user.setId(newUserId);

        userDAO.save(user);
        return newUserId;
    }

    @Override
    public void update(User user) {
        userDAO.save(user);
    }

    @Override
    public void delete(Long id) {
        userDAO.delete(id);
    }

    private long generateNewUserId() {
        long maxId = userDAO.findAll().stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }

    public String generateUsername(User user) {
        String baseUsername = user.getFirstName() + "." + user.getLastName();
        Random random = new Random();
        String uniqueUsername = baseUsername;

        while(existUsername(uniqueUsername)){
            int randomNumber = random.nextInt(900000) + 100000; // Generate a random 6-digit number
            uniqueUsername = baseUsername + randomNumber;
        }

        return uniqueUsername;
    }


    // Check if a username already exists in the maps.
    public boolean existUsername(String username) {
        List<User> users = userDAO.findAll();
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
