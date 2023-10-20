package gym.storage;

import java.util.List;

import gym.entities.*;
import org.springframework.stereotype.Component;

public class Data {
    private List<Trainer> trainers;
    private List<Trainee> trainees;
    private List<Training> trainings;
    private List<User> users;
    private List<TrainingType> trainingTypes;

    // Getters y setters

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public List<User> getUsers(){
        return users;
    }

    public List<TrainingType> getTrainingTypes(){
        return trainingTypes;
    }
}
