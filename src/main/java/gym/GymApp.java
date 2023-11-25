package gym;

import gym.service.TraineeService;
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
        TraineeService traineeService = context.getBean(TraineeService.class);
        traineeService.create("Jorge", "Ibarra",true,new Date(), "5 de Mayo #70");
        System.out.println(traineeService.select(12L));
        context.close();
    }
}
