package gym.service.implementations;

import gym.dao.*;
import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
import gym.entities.TrainingType;
import gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TrainingServiceImpl implements TrainingService {
    private DataAccessObject<Trainee> traineeDAO;
    private DataAccessObject<Trainer> trainerDAO;
    private DataAccessObject<TrainingType> trainingTypeDAO;
    private DataAccessObject<Training> trainingDAO;

    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

    @Autowired
    public void setTraineeDAO(DataAccessObject<Trainee> traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setTrainerDAO(DataAccessObject<Trainer> trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void setTrainingTypeDAO(DataAccessObject<TrainingType> trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }

    @Autowired
    public void setTrainingDAO(DataAccessObject<Training> trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public Long create(Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration) {
        if(traineeId==null || trainerId==null ||trainingTypeId == null || trainingTypeId==null || trainingDate==null){
            logger.error("Failed to create Training: the following parameters cannot be null (traineeId, trainerId, trainingTypeId, trainingTypeId, trainingDate)");
            return null;
        }

        if (traineeDAO.findById(traineeId) != null && trainerDAO.findById(trainerId) != null && trainingTypeDAO.findById(trainingTypeId) != null) {
            Training training = new Training();
            training.setTrainee(traineeDAO.findById(traineeId));
            training.setTrainer(trainerDAO.findById(trainerId));
            training.setTrainingName(trainingName);
            training.setTrainingType(trainingTypeDAO.findById(trainingTypeId));
            training.setTrainingDate(trainingDate);
            training.setTrainingDuration(trainingDuration);
            Long trainingId = trainingDAO.save(training);

            logger.info("Created a new Training with ID: {}" , trainingId);

            return trainingId;
        } else {
            logger.warn("Failed to create training");
            return null;
        }
    }

    @Override
    public Training select(Long id) {
        if(id == null){
            logger.error("Failed to create Training: the following parameters cannot be null (id)");
            return null;
        }
        return trainingDAO.findById(id);
    }
}
