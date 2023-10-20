import gym.config.AppConfig;
import gym.entities.Trainee;
import gym.storage.InMemoryStorage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class GymApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        InMemoryStorage storage = context.getBean("storage", InMemoryStorage.class);
        Map<Long, Trainee> traineeMap = storage.getTrainees();

        for (Map.Entry<Long, Trainee> entry : traineeMap.entrySet()) {
            Long traineeId = entry.getKey();
            Trainee trainee = entry.getValue();

            System.out.println("Trainee ID: " + traineeId);
            System.out.println("Name: " + trainee.getUser().getFirstName() + " " + trainee.getUser().getLastName());
            System.out.println("Date of Birth: " + trainee.getDateOfBirth());
            System.out.println("Address: " + trainee.getAddress());
            System.out.println("User ID: " + trainee.getUser().getId());
            System.out.println();
        }
    }
}