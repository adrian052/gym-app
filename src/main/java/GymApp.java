import gym.config.AppConfig;
import gym.dao.*;
import gym.entities.*;
import gym.storage.InMemoryStorage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class GymApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TrainerDAO trainerDAO = context.getBean(TrainerDAO.class);
        TraineeDAO traineeDAO = context.getBean(TraineeDAO.class);
        TrainingDAO trainingDAO = context.getBean(TrainingDAO.class);
        TrainingTypeDAO trainingTypeDAO = context.getBean(TrainingTypeDAO.class);
        UserDAO userDAO = context.getBean(UserDAO.class);

        printTrainers(trainerDAO.findAll());
        printTrainees(traineeDAO.findAll());
        printTrainings(trainingDAO.findAll());
        printTrainingTypes(trainingTypeDAO.findAll());
        printUsers(userDAO.findAll());
    }

    private static void printTrainers(Iterable<Trainer> trainers) {
        System.out.println("Trainers:");
        for (Trainer trainer : trainers) {
            System.out.println("Trainer ID: " + trainer.getId());
            System.out.println("Name: " + trainer.getUser().getFirstName() + " " + trainer.getUser().getLastName());
            System.out.println("Specialization: " + trainer.getSpecialization().getTrainingTypeName());
            System.out.println("User ID: " + trainer.getUser().getId());
            System.out.println();
        }
    }

    private static void printTrainees(Iterable<Trainee> trainees) {
        System.out.println("Trainees:");
        for (Trainee trainee : trainees) {
            System.out.println("Trainee ID: " + trainee.getId());
            System.out.println("Name: " + trainee.getUser().getFirstName() + " " + trainee.getUser().getLastName());
            System.out.println("Date of Birth: " + trainee.getDateOfBirth());
            System.out.println("Address: " + trainee.getAddress());
            System.out.println("User ID: " + trainee.getUser().getId());
            System.out.println();
        }
    }

    private static void printTrainings(Iterable<Training> trainings) {
        System.out.println("Trainings:");
        for (Training training : trainings) {
            System.out.println("Training ID: " + training.getId());
            System.out.println("Training Name: " + training.getTrainingName());
            System.out.println("Training Date: " + training.getTrainingDate());
            System.out.println("Duration: " + training.getTrainingDuration() + " minutes");
            System.out.println("Trainee: " + training.getTrainee().getUser().getFirstName() + " " + training.getTrainee().getUser().getLastName());
            System.out.println("Trainer: " + training.getTrainer().getUser().getFirstName() + " " + training.getTrainer().getUser().getLastName());
            System.out.println("Training Type: " + training.getTrainingType().getTrainingTypeName());
            System.out.println();
        }
    }

    private static void printTrainingTypes(Iterable<TrainingType> trainingTypes) {
        System.out.println("Training Types:");
        for (TrainingType type : trainingTypes) {
            System.out.println("Type ID: " + type.getId());
            System.out.println("Training Type: " + type.getTrainingTypeName());
            System.out.println();
        }
    }

    private static void printUsers(Iterable<User> users) {
        System.out.println("Users:");
        for (User user : users) {
            System.out.println("User ID: " + user.getId());
            System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Active: " + user.isActive());
            System.out.println();
        }
    }
}
