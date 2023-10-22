import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import gym.facade.FacadeGym;
import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;

import java.util.Date;
import java.util.List;

public class GymApp {
    public static void main(String[] args) {
        // Initialize the Spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext("gym");

        // Get the FacadeGym bean from the application context
        FacadeGym gymFacade = context.getBean(FacadeGym.class);

        // Select all trainees and print their information
        List<Trainee> trainees = gymFacade.selectAllTrainees();
        System.out.println("All Trainees:");
        for (Trainee trainee : trainees) {
            System.out.println(trainee);
            System.out.println();
        }

        System.out.println("------------------------------------------------------");
        System.out.println();

        // Select all trainers and print their information
        List<Trainer> trainers = gymFacade.selectAllTrainers();
        System.out.println("All Trainers:");
        for (Trainer trainer : trainers) {
            System.out.println(trainer);
            System.out.println();
        }

        System.out.println("------------------------------------------------------");
        System.out.println();

        // Select all trainings and print their information
        List<Training> trainings = gymFacade.selectAllTrainings();
        System.out.println("All Trainings:");
        for (Training training : trainings) {
            System.out.println(training);
            System.out.println();
        }

        System.out.println("------------------------------------------------------");
        System.out.println();

        // Create a new trainee
        gymFacade.createTrainee("Alice", "Johnson", true, new Date(), "123 Elm St");
        System.out.println();

        gymFacade.createTrainee("New", "Trainee", true, new Date(), "123 Elm St");
        System.out.println();

        // Create a new trainer
        gymFacade.createTrainer(1L, "John", "Doe", true);
        System.out.println();

        gymFacade.createTrainer(1L, "New", "Trainer", true);
        System.out.println();

        // Create a new training
        gymFacade.createTraining(3L, 3L, "New Training", 1L, new Date(), 60);

        System.out.println("------------------------------------------------------");
        System.out.println();

        // Select a specific trainee and print their information
        Trainee selectedTrainee = gymFacade.selectTrainee(3L);
        if (selectedTrainee != null) {
            System.out.println("Selected Trainee:");
            System.out.println(selectedTrainee);
            System.out.println();
        }

        // Select a specific trainer and print their information
        Trainer selectedTrainer = gymFacade.selectTrainer(3L);
        if (selectedTrainer != null) {
            System.out.println("Selected Trainer:");
            System.out.println(selectedTrainer);
            System.out.println();
        }

        // Select a specific training and print its information
        Training selectedTraining = gymFacade.selectTraining(3L);
        if (selectedTraining != null) {
            System.out.println("Selected Training:");
            System.out.println(selectedTraining);
            System.out.println();
        }

        System.out.println("------------------------------------------------------");
        System.out.println();

        // Update the trainee
        gymFacade.updateTrainee(1L, "Updated", "Trainee", true, new Date(), "456 Oak St");

        // Update the trainer
        gymFacade.updateTrainer(1L, 2L, "Updated", "Trainer", true);

        System.out.println("------------------------------------------------------");
        System.out.println();

        // Delete a trainee
        boolean traineeDeleted = gymFacade.deleteTrainee(2L);
        if (traineeDeleted) {
            System.out.println("Trainee with ID 2 has been deleted.");
        } else {
            System.out.println("Failed to delete Trainee with ID 2.");
        }

        System.out.println("------------------------------------------------------");
        System.out.println();

        // Select all trainees and print their information again
        List<Trainee> updatedTrainees = gymFacade.selectAllTrainees();
        System.out.println("All Trainees (After Updates and Deletions):");
        for (Trainee trainee : updatedTrainees) {
            System.out.println(trainee);
            System.out.println();
        }

        // Select all trainers and print their information again
        List<Trainer> updatedTrainers = gymFacade.selectAllTrainers();
        System.out.println("All Trainers (After Updates and Deletions):");
        for (Trainer trainer : updatedTrainers) {
            System.out.println(trainer);
            System.out.println();
        }

        // Select all trainings and print their information again
        List<Training> updatedTrainings = gymFacade.selectAllTrainings();
        System.out.println("All Trainings (After Updates and Deletions):");
        for (Training training : updatedTrainings) {
            System.out.println(training);
            System.out.println();
        }
    }
}
