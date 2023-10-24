package gym.facade;

import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
import gym.service.TraineeService;
import gym.service.TrainerService;
import gym.service.TrainingService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SimpleFacadeGym implements FacadeGym {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

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
    public Long createTrainee(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        return traineeService.create(firstName, lastName, isActive, dateOfBirth, address);
    }

    @Override
    public boolean updateTrainee(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        return traineeService.update(id, firstName, lastName, isActive, dateOfBirth, address);
    }

    @Override
    public boolean deleteTrainee(Long id) {
        return traineeService.delete(id);
    }

    @Override
    public Trainer selectTrainer(Long id) {
        return trainerService.select(id);
    }

    @Override
    public Long createTrainer(String firstName, String lastName, boolean isActive, Long specializationId) {
        return trainerService.create(firstName, lastName, isActive,specializationId);
    }

    @Override
    public boolean updateTrainer(Long id, String firstName, String lastName, boolean isActive,Date birthDate,String address, Long specializationId) {
        return trainerService.update(id, firstName, lastName, isActive,specializationId);
    }

    @Override
    public Training selectTraining(Long id) {
        return trainingService.select(id);
    }

    @Override
    public Long createTraining(Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration) {
        return trainingService.create(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);
    }
}
