package gym;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import gym.facade.FacadeGym;
import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;

import java.util.Date;

public class GymApp {
    public static void main(String[] args) {
        // Initialize the Spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext("gym");

        // Get the FacadeGym bean from the application context
        FacadeGym facadeGym = context.getBean(FacadeGym.class);
        // Select a Trainee by ID
        Trainee selectedTrainee = facadeGym.selectTrainee(1L);
        if (selectedTrainee != null) {
            System.out.println("Selected Trainee:");
            System.out.println(selectedTrainee);
        } else {
            System.out.println("Trainee not found.");
        }

        // Create a new Trainee
        Long newTraineeId = facadeGym.createTrainee("Alice", "Johnson", true, new Date(), "123 Elm St");
        System.out.println("New Trainee created with ID: " + newTraineeId);

        // Update an existing Trainee
        boolean traineeUpdated = facadeGym.updateTrainee(1L, "Updated John", "Updated Doe", true, new Date(), "456 Oak St");
        System.out.println("Trainee updated: " + traineeUpdated);

        // Delete a Trainee by ID
        boolean traineeDeleted = facadeGym.deleteTrainee(2L);
        System.out.println("Trainee deleted: " + traineeDeleted);

        // Select a Trainer by ID
        Trainer selectedTrainer = facadeGym.selectTrainer(1L);
        if (selectedTrainer !=null ) {
            System.out.println("Selected Trainer:");
            System.out.println(selectedTrainer);
        } else {
            System.out.println("Trainer not found.");
        }

        // Create a new Trainer
        Long newTrainerId = facadeGym.createTrainer("John", "Doe", true, new Date(), "789 Oak St", 1L);
        System.out.println("New Trainer created with ID: " + newTrainerId);

        // Update an existing Trainer
        boolean trainerUpdated = facadeGym.updateTrainer(1L, "Updated Trainer", "Updated Smith", true, new Date(), "456 Pine St", 2L);
        System.out.println("Trainer updated: " + trainerUpdated);

        // Select a Training by ID
        Training selectedTraining = facadeGym.selectTraining(1L);
        if (selectedTraining != null) {
            System.out.println("Selected Training:");
            System.out.println(selectedTraining);
        } else {
            System.out.println("Training not found.");
        }

        // Create a new Training
        Long newTrainingId = facadeGym.createTraining(1L, 1L, "New Training", 1L, new Date(), 60);
        System.out.println("New Training created with ID: " + newTrainingId);
    }
}
