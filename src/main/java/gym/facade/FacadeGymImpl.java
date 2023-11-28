package gym.facade;

import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
import gym.service.Credentials;
import gym.service.TraineeService;
import gym.service.TrainerService;
import gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;

@Service
@Profile("memory")
public class FacadeGymImpl implements FacadeGym {
    protected static final Logger logger = LoggerFactory.getLogger(FacadeGymImpl.class);
    protected final TraineeService traineeService;
    protected final TrainerService trainerService;
    protected final TrainingService trainingService;
    protected static final String AUTH_ERROR = "Authentication failed: {}";
    public FacadeGymImpl(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    @Override
    public Trainee selectTrainee(Long id) {
        return traineeService.select(id);
    }

    @Override
    public Trainee createTrainee(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        return traineeService.create(firstName, lastName, isActive, dateOfBirth, address);
    }

    @Override
    public Trainee updateTrainee(Credentials credentials, Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        try{
            return traineeService.update(credentials, id, firstName, lastName, isActive, dateOfBirth, address);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }
    }
    @Override
    public boolean deleteTrainee(Credentials credentials, Long id) {
        try{
            return traineeService.delete(credentials,id);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return false;
        }
    }


    @Override
    public Trainer selectTrainer(Long id) {
        return trainerService.select(id);
    }

    @Override
    public Trainer createTrainer(Credentials credentials, String firstName, String lastName, boolean isActive, Long specialization) {
        return trainerService.create(firstName, lastName, isActive,specialization);
    }

    @Override
    public Trainer updateTrainer(Credentials credentials, Long id, String firstName, String lastName, boolean isActive,Date birthDate,String address, Long specializationId) {
        try{
            return trainerService.update(credentials, id, firstName, lastName, isActive,specializationId);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }
    }

    @Override
    public Training selectTraining(Long id) {
        return trainingService.select(id);
    }

    @Override
    public Training createTraining(Credentials credentials, Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration) {
        try{
            return trainingService.create(credentials,traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }
    }

}
