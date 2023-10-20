import gym.config.AppConfig;
import gym.entities.*;
import gym.service.TraineeService;
import gym.service.TrainerService;
import gym.service.TrainingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Date;
import java.util.List;

public class GymApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TraineeService traineeService = context.getBean(TraineeService.class);
        TrainerService trainerService = context.getBean(TrainerService.class);
        TrainingService trainingService = context.getBean(TrainingService.class);

        Trainee trainee1 = new Trainee();
        trainee1.setDateOfBirth(new Date());
        trainee1.setAddress("123 Main St");
        User user1 = new User();
        user1.setFirstName("Alice");
        user1.setLastName("Johnson");
        user1.setUsername("Alice.Johnson");
        user1.setPassword("user-pass");
        user1.setActive(true);
        trainee1.setUser(user1);
        traineeService.createTrainee(trainee1);

        Trainee trainee2 = new Trainee();
        trainee2.setDateOfBirth(new Date());
        trainee2.setAddress("456 Elm St");
        User user2 = new User();
        user2.setFirstName("Bob");
        user2.setLastName("Smith");
        user2.setUsername("Bob.Smith");
        user2.setPassword("password123");
        user2.setActive(true);
        trainee2.setUser(user2);
        traineeService.createTrainee(trainee2);

        List<Trainee> allTrainees = traineeService.getAllTrainees();
        for (Trainee trainee : allTrainees) {
            System.out.println(trainee);
        }

        Trainer trainer1 = new Trainer();
        TrainingType specialization1 = new TrainingType();
        specialization1.setTrainingTypeName("Specialization 1");
        trainer1.setSpecialization(specialization1);
        User user3 = new User();
        user3.setFirstName("John");
        user3.setLastName("Doe");
        user3.setUsername("John.Doe");
        user3.setPassword("password123");
        user3.setActive(true);
        trainer1.setUser(user3);
        trainerService.createTrainer(trainer1);

        Trainer trainer2 = new Trainer();
        TrainingType specialization2 = new TrainingType();
        specialization2.setTrainingTypeName("Specialization 2");
        trainer2.setSpecialization(specialization2);
        User user4 = new User();
        user4.setFirstName("Jane");
        user4.setLastName("Smith");
        user4.setUsername("Jane.Smith");
        user4.setPassword("secure-pass");
        user4.setActive(true);
        trainer2.setUser(user4);
        trainerService.createTrainer(trainer2);

        List<Trainer> allTrainers = trainerService.getAllTrainers();
        for (Trainer trainer : allTrainers) {
            System.out.println(trainer);
        }

        Training training1 = new Training();
        training1.setTrainingName("Training 1");
        training1.setTrainingDate(new Date());
        training1.setTrainingDuration(60);
        training1.setTrainee(trainee1);
        training1.setTrainer(trainer1);
        training1.setTrainingType(specialization1);
        trainingService.createTraining(training1);

        Training training2 = new Training();
        training2.setTrainingName("Training 2");
        training2.setTrainingDate(new Date());
        training2.setTrainingDuration(45);
        training2.setTrainee(trainee2);
        training2.setTrainer(trainer2);
        training2.setTrainingType(specialization2);
        trainingService.createTraining(training2);

        List<Training> allTrainings = trainingService.getAllTrainings();
        for (Training training : allTrainings) {
            System.out.println(training);
        }
    }
}
