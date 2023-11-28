package gym.facade;

import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
import gym.service.Credentials;
import gym.service.TraineeService;
import gym.service.TrainerService;
import gym.service.TrainingService;
import gym.service.db.TraineeDbService;
import gym.service.db.TrainerDbService;
import gym.service.db.TrainingDbService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import javax.naming.AuthenticationException;
import java.util.List;

@Service
@Profile("db")
public class FacadeDbGymImpl extends FacadeGymImpl implements FacadeDbGym{
    public FacadeDbGymImpl(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        super(traineeService, trainerService, trainingService);
    }

    @Override
    public Trainee selectTraineeByUsername(String username) {
        return ((TraineeDbService)traineeService).selectByUsername(username);
    }

    @Override
    public Trainee updateTraineePassword(Credentials credentials, Long id, String newPassword) {
        try{
            return ((TraineeDbService)traineeService).updatePassword(credentials, id, newPassword);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }
    }

    @Override
    public Trainee activateTrainee(Credentials credentials, Long id) {
        try{
            return ((TraineeDbService)traineeService).updateStatus(credentials, id, true);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }

    }

    @Override
    public Trainee deactivateTrainee(Credentials credentials, Long id) {
        try{
            return ((TraineeDbService)traineeService).updateStatus(credentials, id, false);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }

    }

    @Override
    public boolean deleteTraineeByUsername(Credentials credentials, String username) {
        try{
            return ((TraineeDbService)traineeService).deleteByUsername(credentials, username);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return false;
        }
    }

    @Override
    public Trainer selectTrainerByUsername(String username) {
        return ((TrainerDbService)traineeService).selectByUsername(username);
    }

    @Override
    public Trainer updateTrainerPassword(Credentials credentials, Long id, String newPassword) {
        try{
            return ((TrainerDbService)traineeService).updatePassword(credentials, id, newPassword);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }
    }

    @Override
    public Trainer activateTrainer(Credentials credentials, Long id) {
        try{
            return ((TrainerDbService)traineeService).updateStatus(credentials,id, true);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }

    }

    @Override
    public Trainer deactivateTrainer(Credentials credentials, Long id) {
        try{
            return ((TrainerDbService)traineeService).updateStatus(credentials,id, false);
        }catch (AuthenticationException e){
            logger.error(AUTH_ERROR,e.getMessage());
            return null;
        }
    }

    @Override
    public List<Training> getTraineeTrainingsByTrainingName(String username, String trainingName) {
        return ((TrainingDbService)trainingService).getTraineeTrainingsByTrainingName(username,trainingName);
    }

    @Override
    public List<Training> getTraineeTrainingsByDuration(String username, int minDuration, int maxDuration) {
        return ((TrainingDbService)trainingService).getTraineeTrainingsByDuration(username, minDuration, maxDuration);
    }

    @Override
    public List<Training> getTrainerTrainingsByTrainingName(String username, String trainingName) {
        return ((TrainingDbService)trainingService).getTrainerTrainingsByTrainingName(username,trainingName);
    }

    @Override
    public List<Training> getTrainerTrainingsByDuration(String username, int minDuration, int maxDuration) {
        return ((TrainingDbService)trainingService).getTrainerTrainingsByDuration(username,minDuration, maxDuration);
    }


}
