package gym.service;

import gym.dao.TrainerDAO;
import gym.entities.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    private TrainerDAO trainerDAO;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    public List<Trainer> getAllTrainers() {
        return trainerDAO.findAll();
    }

    public Trainer getTrainerById(Long id) {
        return trainerDAO.findById(id);
    }

    public void createTrainer(Trainer trainer) {
        trainerDAO.save(trainer);
    }

    public void updateTrainer(Trainer trainer) {
        trainerDAO.save(trainer);
    }
}

