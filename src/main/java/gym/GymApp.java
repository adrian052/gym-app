package gym;

import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.facade.FacadeDbGym;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import java.util.Date;

public class GymApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles("db");
        context.setEnvironment(environment);
        context.scan("gym");
        context.refresh();
        FacadeDbGym facadeGym = context.getBean(FacadeDbGym.class);
        Trainee trainee = facadeGym.createTrainee("Adrian", "Ibarra",true, new Date(), "5 de mayo");
        Trainer trainer = facadeGym.createTrainer("Fernando", "Ibarra", true, 1L);
        context.close();
    }
}
