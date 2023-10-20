package gym.service;

import gym.dao.TraineeDAO;
import gym.entities.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    private TraineeDAO traineeDAO;

    @Autowired
    public TraineeService(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    public List<Trainee> getAllTrainees() {
        return traineeDAO.findAll();
    }

    public Trainee getTraineeById(Long id) {
        return traineeDAO.findById(id);
    }

    public void createTrainee(Trainee trainee) {
        traineeDAO.save(trainee);
    }

    public void updateTrainee(Trainee trainee) {
        traineeDAO.save(trainee);
    }

    public void deleteTrainee(Long id) {
        traineeDAO.delete(id);
    }
}
