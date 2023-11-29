package gym;

import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
import gym.facade.FacadeDbGym;
import gym.service.Credentials;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import java.util.Date;
import java.util.List;

public class GymApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles("db");
        context.setEnvironment(environment);
        context.scan("gym");
        context.refresh();
        FacadeDbGym facadeGym = context.getBean(FacadeDbGym.class);

        //Trainee
        Credentials traineeCredentials = facadeGym.createTrainee("Adrian", "Ibarra",true, new Date(), "5 de mayo");
        Trainee trainee = facadeGym.selectTraineeByUsername(traineeCredentials.username());
        System.out.println(trainee);
        Trainee selectedTrainee = facadeGym.selectTrainee(trainee.getId());
        System.out.println(selectedTrainee);
        Trainee updatedTrainee = facadeGym.updateTrainee(new Credentials(trainee.getUser().getUsername(), trainee.getUser().getPassword()), trainee.getId(), "Ibarra", "Adrian", true, new Date(), null);
        System.out.println(updatedTrainee);
        Trainee deactivatedTrainee = facadeGym.deactivateTrainee(new Credentials(trainee.getUser().getUsername(), trainee.getUser().getPassword()), updatedTrainee.getId());
        System.out.println(deactivatedTrainee);
        Trainee activatedTrainee = facadeGym.activateTrainee(new Credentials(trainee.getUser().getUsername(), trainee.getUser().getPassword()), deactivatedTrainee.getId());
        System.out.println(activatedTrainee);
        Trainee traineeByUsername = facadeGym.selectTraineeByUsername(trainee.getUser().getUsername());
        System.out.println(traineeByUsername);
        boolean isTraineeDeleted = facadeGym.deleteTrainee(new Credentials(trainee.getUser().getUsername(), trainee.getUser().getPassword()), traineeByUsername.getId());
        System.out.println(isTraineeDeleted);

        //Trainer
        Credentials trainerCredentials = facadeGym.createTrainer("Antonio", "Robles",true, 4L);
        Trainer trainer = facadeGym.selectTrainerByUsername(trainerCredentials.username());
        System.out.println(trainer);
        Trainer selectedTrainer = facadeGym.selectTrainer(trainer.getId());
        System.out.println(selectedTrainer);
        Trainer updatedTrainer = facadeGym.updateTrainer(trainerCredentials, trainer.getId(), "Robles Perez", "Antonio", true,3L);
        System.out.println(updatedTrainer);
        Trainer deactivatedTrainer = facadeGym.deactivateTrainer(trainerCredentials, updatedTrainer.getId());
        System.out.println("Is active? "+deactivatedTrainer.getUser().isActive());
        Trainer activatedTrainer = facadeGym.activateTrainer(trainerCredentials, deactivatedTrainer.getId());
        System.out.println("Is active? "+activatedTrainer.getUser().isActive());

        //Training
        Credentials trainee2Credentials = facadeGym.createTrainee("Adrian2", "Ibarra",true, new Date(), "5 de mayo");
        Trainee trainee2 = facadeGym.selectTraineeByUsername(trainee2Credentials.username());
        facadeGym.createTraining(trainerCredentials,trainee2.getId(), trainer.getId(), "Workout #123", 1L, new Date(), 20);
        facadeGym.createTraining(trainerCredentials,trainee2.getId(), trainer.getId(), "Workout #124", 1L, new Date(), 30);
        facadeGym.createTraining(trainerCredentials,trainee2.getId(), trainer.getId(), "Workout #125", 1L, new Date(), 60);
        facadeGym.createTraining(trainerCredentials,trainee2.getId(), trainer.getId(), "Workout #127", 1L, new Date(), 70);
        List<Training> trainings = facadeGym.getTraineeTrainingsByTrainingName(trainee2Credentials.username(), "Workout #123");
        trainings.forEach(training -> System.out.println("\nTraining 1: " + training));
        List<Training> trainings2 = facadeGym.getTrainerTrainingsByTrainingName(trainerCredentials.username(), "Workout #124");
        trainings2.forEach(training -> System.out.println("\nTraining 2: " + training));
        List<Training> trainings3 = facadeGym.getTraineeTrainingsByDuration(trainee2Credentials.username(), 50, 65);
        trainings3.forEach(training -> System.out.println("\nTraining 3: " + training));
        List<Training> trainings4 = facadeGym.getTrainerTrainingsByDuration(trainerCredentials.username(), 65, 75);
        trainings4.forEach(training -> System.out.println("\nTraining 4: " + training));
        Training trainingSelected4 = facadeGym.selectTraining(trainings4.get(0).getId());
        System.out.println("\n"+trainingSelected4);
        context.close();
    }
}
