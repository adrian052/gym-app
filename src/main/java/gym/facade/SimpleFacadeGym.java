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
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.List;

@Service
public class SimpleFacadeGym implements FacadeGym {
    private static final Logger logger = LoggerFactory.getLogger(SimpleFacadeGym.class);
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private static final String AUTH_ERROR = "Authentication failed: {}";
    public SimpleFacadeGym(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    @Override
    public Trainee selectTrainee(Long id) {
        return traineeService.select(id);
    }

    @Override
    public Trainee selectTraineeByUsername(String username) {
        return traineeService.selectByUsername(username);
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
    public Trainee updateTraineePassword(Credentials credentials, Long id, String newPassword) {
        try{       
            return traineeService.updatePassword(credentials, id, newPassword);           
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }
    }

    @Override
    public Trainee activateTrainee(Credentials credentials, Long id) {
        try{
            return traineeService.updateStatus(credentials, id, true);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }
        
    }

    @Override
    public Trainee deactivateTrainee(Credentials credentials, Long id) {
        try{
            return traineeService.updateStatus(credentials, id, false);
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
    public boolean deleteTraineeByUsername(Credentials credentials, String username) {
        try{
            return traineeService.deleteByUsername(credentials, username);
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
    public Trainer selectTrainerByUsername(String username) {
        return trainerService.selectByUsername(username);
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
    public Trainer updateTrainerPassword(Credentials credentials, Long id, String newPassword) {
        try{
            return trainerService.updatePassword(credentials, id, newPassword);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }
    }

    @Override
    public Trainer activateTrainer(Credentials credentials, Long id) {
        try{
            return trainerService.updateStatus(credentials,id, true);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }
        
    }

    @Override
    public Trainer deactivateTrainer(Credentials credentials, Long id) {
        try{
            return trainerService.updateStatus(credentials,id, false);
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

    @Override
    public List<Training> getTraineeTrainingsByTrainingName(String username, String trainingName) {
        return trainingService.getTraineeTrainingsByTrainingName(username,trainingName);
    }

    @Override
    public List<Training> getTraineeTrainingsByDuration(String username, int minDuration, int maxDuration) {
        return trainingService.getTraineeTrainingsByDuration(username, minDuration, maxDuration);
    }

    @Override
    public List<Training> getTrainerTrainingsByTrainingName(String username, String trainingName) {
        return trainingService.getTrainerTrainingsByTrainingName(username,trainingName);
    }

    @Override
    public List<Training> getTrainerTrainingsByDuration(String username, int minDuration, int maxDuration) {
        return trainingService.getTrainerTrainingsByDuration(username,minDuration, maxDuration);
    }
}
