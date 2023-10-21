package gym.service;

import gym.dao.DataAccessObject;
import gym.entities.Trainee;
import gym.dao.UserDAO;
import gym.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService implements GymService<Trainee> {
    private DataAccessObject<Trainee> traineeDAO;
    private DataAccessObject<User> userDAO;

    @Autowired
    public TraineeService(DataAccessObject<Trainee> traineeDAO, DataAccessObject<User> userDAO) {
        this.traineeDAO = traineeDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<Trainee> selectAll() {
        return traineeDAO.findAll();
    }

    @Override
    public Trainee select(Long id) {
        return traineeDAO.findById(id);
    }

    @Override
    public void create(Trainee trainee) {
        if (trainee.getId() != null) {
            throw new IllegalArgumentException("Trainee ID must be null for create.");
        }
        User user = userDAO.findById(trainee.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("User with the specified ID does not exist.");
        }

        traineeDAO.save(trainee);
    }

    @Override
    public void update(Trainee trainee) {
        if (trainee.getId() == null) {
            throw new IllegalArgumentException("Trainee ID cannot be null for update.");
        }

        Trainee existingTrainee = traineeDAO.findById(trainee.getId());
        if (existingTrainee == null) {
            throw new IllegalArgumentException("Trainee with the specified ID does not exist.");
        }

        User user = userDAO.findById(trainee.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("User with the specified ID does not exist.");
        }

        traineeDAO.save(trainee);
    }

    @Override
    public void delete(Long id) {
        Trainee existingTrainee = traineeDAO.findById(id);
        if (existingTrainee == null) {
            throw new IllegalArgumentException("Trainee with the specified ID does not exist.");
        }

        traineeDAO.delete(id);
    }
}