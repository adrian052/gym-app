import gym.config.AppConfig;
import gym.dao.TrainingTypeDAO;
import gym.entities.*;
import gym.service.TraineeService;
import gym.service.TrainerService;
import gym.service.TrainingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class GymApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Obtener los servicios necesarios
        TraineeService traineeService = context.getBean(TraineeService.class);
        TrainerService trainerService = context.getBean(TrainerService.class);
        TrainingService trainingService = context.getBean(TrainingService.class);
        TrainingTypeDAO trainingTypeDAO = context.getBean(TrainingTypeDAO.class);

        // Crear un nuevo Trainee a partir de los datos proporcionados
        Trainee newTrainee = new Trainee();
        newTrainee.setDateOfBirth(new Date());
        newTrainee.setAddress("123 Main St");

        // Crear un nuevo User para el Trainee
        User traineeUser = new User();
        traineeUser.setId(3L); // ID 3 del JSON
        newTrainee.setUser(traineeUser);

        traineeService.create(newTrainee);
        System.out.println("New Trainee created: " + newTrainee);

        // Crear un nuevo Trainer a partir de los datos proporcionados
        Trainer newTrainer = new Trainer();

        // Crear un nuevo User para el Trainer
        User trainerUser = new User();
        trainerUser.setId(1L); // ID 1 del JSON
        newTrainer.setUser(trainerUser);

        // Utiliza una TrainingType existente (por ejemplo, "Training Type 1")
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L); // ID 1 del JSON
        newTrainer.setSpecialization(trainingType);

        trainerService.create(newTrainer);
        System.out.println("New Trainer created: " + newTrainer);

        // Crear un nuevo Training a partir de los datos proporcionados
        Training newTraining = new Training();
        newTraining.setTrainee(newTrainee);
        newTraining.setTrainer(newTrainer);
        newTraining.setTrainingName("New Training 3");
        newTraining.setTrainingDate(new Date()); // Fecha nueva
        newTraining.setTrainingDuration(75);

        // Utiliza una TrainingType existente (por ejemplo, "Training Type 1")
        newTraining.setTrainingType(trainingType);

        trainingService.create(newTraining);
        System.out.println("New Training created: " + newTraining);
    }
}
