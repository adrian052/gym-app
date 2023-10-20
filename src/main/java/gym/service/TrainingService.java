package gym.service;

import gym.dao.TrainingDAO;
import gym.entities.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {
    private TrainingDAO trainingDAO;

    @Autowired
    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    public List<Training> getAllTrainings() {
        return trainingDAO.findAll();
    }

    public Training getTrainingById(Long id) {
        return trainingDAO.findById(id);
    }

    public void createTraining(Training training) {
        trainingDAO.save(training);
    }
}
