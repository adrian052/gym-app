package gym.facade;

import gym.entities.*;
import gym.service.GymService;
import gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SimpleFacadeGym implements FacadeGym{

    private GymService<Trainee> traineeService;
    private GymService<Trainer> trainerService;
    private GymService<Training> trainingService;
    private GymService<User> userService;
    private GymService<TrainingType> trainingTypeService;


    @Autowired
    public SimpleFacadeGym(GymService<Trainee> traineeService,
                           GymService<Trainer> trainerService,
                           GymService<Training> trainingService,
                           GymService<User> userService,
                           GymService<TrainingType> trainingTypeService){
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.userService = userService;
        this.trainingTypeService = trainingTypeService;
    }

    //trainee methods
    @Override
    public List<Trainee> selectAllTrainees(){
        return traineeService.selectAll();
    }

    @Override
    public Trainee selectTrainee(Long id){
        return traineeService.select(id);
    }

    @Override
    public void createTrainee(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address){
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUsername(((UserService)userService).generateUsername(newUser));
        newUser.setPassword(((UserService)userService).generateRandomPassword(10));
        newUser.setActive(isActive);
        userService.create(newUser);

        Trainee newTrainee = new Trainee();
        newTrainee.setUser(newUser);
        newTrainee.setDateOfBirth(dateOfBirth);
        newTrainee.setAddress(address);
        traineeService.create(newTrainee);
        System.out.println("New trainee created...");
        System.out.println("The new default username is:" + newUser.getUsername());
        System.out.println("The new default password is:" + newUser.getPassword());
    }

    @Override
    public void updateTrainee(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        Trainee existingTrainee = traineeService.select(id);

        if (existingTrainee == null) {
            System.out.println("Trainee with ID " + id + " does not exist.");
            return;
        }

        User user = existingTrainee.getUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setActive(isActive);

        existingTrainee.setDateOfBirth(dateOfBirth);
        existingTrainee.setAddress(address);

        // Update user and trainee
        userService.update(user);
        traineeService.update(existingTrainee);

        System.out.println("Trainee with ID " + id + " has been updated.");
    }

    @Override
    public boolean deleteTrainee(Long id){
        try{
            traineeService.delete(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    ///trainer methods
    @Override
    public List<Trainer> selectAllTrainers(){
        return trainerService.selectAll();
    }

    @Override
    public Trainer selectTrainer(Long id){
        return trainerService.select(id);
    }

    @Override
    public void createTrainer(Long specializationId, String firstName, String lastName, boolean isActive) {
        // Check if the specialization with the specified ID exists
        TrainingType specialization = trainingTypeService.select(specializationId);

        if (specialization == null) {
            System.out.println("Specialization with ID " + specializationId + " does not exist.");
            return;
        }

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUsername(((UserService) userService).generateUsername(newUser));
        newUser.setPassword(((UserService) userService).generateRandomPassword(10));
        newUser.setActive(isActive);
        userService.create(newUser);

        Trainer newTrainer = new Trainer();
        newTrainer.setUser(newUser);
        newTrainer.setSpecialization(specialization);
        trainerService.create(newTrainer);

        System.out.println("New trainer created...");
        System.out.println("The new default username is:" + newUser.getUsername());
        System.out.println("The new default password is:" + newUser.getPassword());
    }

    @Override
    public void updateTrainer(Long id, Long specializationId, String firstName, String lastName, boolean isActive) {
        Trainer existingTrainer = trainerService.select(id);

        if (existingTrainer == null) {
            System.out.println("Trainer with ID " + id + " does not exist.");
            return;
        }

        // Check if the specialization with the specified ID exists
        TrainingType specialization = trainingTypeService.select(specializationId);

        if (specialization == null) {
            System.out.println("Specialization with ID " + specializationId + " does not exist.");
            return;
        }

        User user = existingTrainer.getUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setActive(isActive);

        existingTrainer.setSpecialization(specialization);

        // Update user and trainer
        userService.update(user);
        trainerService.update(existingTrainer);

        System.out.println("Trainer with ID " + id + " has been updated.");
    }

    @Override
    public List<Training> selectAllTrainings() {
        return trainingService.selectAll();
    }

    @Override
    public Training selectTraining(Long id) {
        try {
            return trainingService.select(id);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public void createTraining(Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration) {
        // Check if Trainee, Trainer, and TrainingType exist using the services
        Trainee trainee = traineeService.select(traineeId);
        Trainer trainer = trainerService.select(trainerId);
        TrainingType trainingType = trainingTypeService.select(trainingTypeId);

        if (trainee == null) {
            System.out.println("Trainee with ID " + traineeId + " does not exist.");
            return;
        }

        if (trainer == null) {
            System.out.println("Trainer with ID " + trainerId + " does not exist.");
            return;
        }

        if (trainingType == null) {
            System.out.println("TrainingType with ID " + trainingTypeId + " does not exist.");
            return;
        }

        Training newTraining = new Training();
        newTraining.setTrainee(trainee);
        newTraining.setTrainer(trainer);
        newTraining.setTrainingName(trainingName);
        newTraining.setTrainingType(trainingType);
        newTraining.setTrainingDate(trainingDate);
        newTraining.setTrainingDuration(trainingDuration);

        try {
            trainingService.create(newTraining);
            System.out.println("New training created...");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
