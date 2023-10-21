package gym.service;

import gym.dao.DataAccessObject;
import gym.dao.TrainerDAO;
import gym.dao.UserDAO;
import gym.entities.Trainer;
import gym.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService implements GymService<Trainer> {
    @Autowired
    private DataAccessObject<Trainer> trainerDAO;
    @Autowired
    private DataAccessObject<User> userDAO;

    @Override
    public List<Trainer> selectAll() {
        return trainerDAO.findAll();
    }

    @Override
    public Trainer select(Long id) {
        return trainerDAO.findById(id);
    }

    @Override
    public void create(Trainer trainer) {
        if (trainer.getId() != null) {
            throw new IllegalArgumentException("Trainer ID must be null for create.");
        }

        // Check if the user with the specified ID exists
        User user = userDAO.findById(trainer.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("User with the specified ID does not exist.");
        }

        // Ensure that no fields are null
        if (trainer.getSpecialization() == null) {
            throw new IllegalArgumentException("Specialization must not be null.");
        }

        trainerDAO.save(trainer);
    }

    @Override
    public void update(Trainer trainer) {
        if (trainer.getId() == null) {
            throw new IllegalArgumentException("Trainer ID cannot be null for update.");
        }

        // Check if the trainer with the specified ID exists
        Trainer existingTrainer = trainerDAO.findById(trainer.getId());
        if (existingTrainer == null) {
            throw new IllegalArgumentException("Trainer with the specified ID does not exist.");
        }

        // Check if the user with the specified ID exists
        User user = userDAO.findById(trainer.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("User with the specified ID does not exist.");
        }
        // Ensure that no fields are null
        if (trainer.getSpecialization() == null) {
            throw new IllegalArgumentException("Specialization must not be null.");
        }
        trainerDAO.save(trainer);
    }

    @Override
    public void delete(Long id) {
        // TODO: Implement delete logic
    }
}
