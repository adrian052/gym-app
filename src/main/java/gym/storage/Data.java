package gym.storage;

import java.util.List;
import gym.entities.Trainer;
import gym.entities.Trainee;
import gym.entities.Training;
import org.springframework.stereotype.Component;

public class Data {
    private List<Trainer> trainers;
    private List<Trainee> trainees;
    private List<Training> trainings;

    // Getters y setters para los campos trainers, trainees y trainings

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
}
