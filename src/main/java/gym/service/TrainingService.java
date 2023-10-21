package gym.service;

import gym.dao.DataAccessObject;
import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
import gym.entities.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService implements GymService<Training> {
    private final DataAccessObject<Training> trainingDAO;
    private final DataAccessObject<Trainee> traineeDAO;
    private final DataAccessObject<Trainer> trainerDAO;
    private final DataAccessObject<TrainingType> trainingTypeDAO;

    @Autowired
    public TrainingService(DataAccessObject<Training> trainingDAO, DataAccessObject<Trainee> traineeDAO, DataAccessObject<Trainer> trainerDAO, DataAccessObject<TrainingType> trainingTypeDAO) {
        this.trainingDAO = trainingDAO;
        this.traineeDAO = traineeDAO;
        this.trainerDAO = trainerDAO;
        this.trainingTypeDAO = trainingTypeDAO;
    }

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
    public void create(Training training) {
        if (training.getId() != null) {
            throw new IllegalArgumentException("Training ID must be null for create.");
        }

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

        // If all checks pass, save the training
        trainingDAO.save(training);
    }

    @Override
    public void update(Training training) {
        // Implement update logic if necessary.
    }

    @Override
    public void delete(Long id) {
        // Implement deletion logic if necessary.
    }
}