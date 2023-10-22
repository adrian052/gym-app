package gym.service;

import gym.dao.DataAccessObject;
import gym.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService implements GymService<Training> {
    @Autowired
    private  DataAccessObject<Training> trainingDAO;
    @Autowired
    private DataAccessObject<Trainee> traineeDAO;
    @Autowired
    private DataAccessObject<Trainer> trainerDAO;
    @Autowired
    private DataAccessObject<TrainingType> trainingTypeDAO;

    @Override
    public List<Training> selectAll() {
        return trainingDAO.findAll();
    }

    @Override
    public Training select(Long id) {
        Training training = trainingDAO.findById(id);
        if (training == null) {
            throw new IllegalArgumentException("Training with ID " + id + " does not exist.");
        }
        return training;
    }

    @Override
    public Long create(Training training) {
        // Check if Trainee, Trainer, and TrainingType exist
        if (traineeDAO.findById(training.getTrainee().getId()) == null) {
            throw new IllegalArgumentException("Trainee with ID " + training.getTrainee().getId() + " does not exist.");
        }

        if (trainerDAO.findById(training.getTrainer().getId()) == null) {
            throw new IllegalArgumentException("Trainer with ID " + training.getTrainer().getId() + " does not exist.");
        }

        if (trainingTypeDAO.findById(training.getTrainingType().getId()) == null) {
            throw new IllegalArgumentException("TrainingType with ID " + training.getTrainingType().getId() + " does not exist.");
        }

        Long newTrainingId = generateNewTrainingId();
        training.setId(newTrainingId);
        // If all checks pass, save the training
        trainingDAO.save(training);
        return newTrainingId;
    }

    @Override
    public void update(Training training) {
        // Implement update logic if necessary.
    }

    @Override
    public void delete(Long id) {
        // Implement deletion logic if necessary.
    }
    private long generateNewTrainingId() {
        long maxId = trainingDAO.findAll().stream()
                .mapToLong(Training::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }
}