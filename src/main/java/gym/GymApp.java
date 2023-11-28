package gym;

import gym.facade.FacadeGym;
import gym.service.Credentials;
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
        FacadeGym facadeGym = context.getBean(FacadeGym.class);
        Credentials credentials = new Credentials("Adrian1.Ibarra","7hrPIIXgTO");
        System.out.println(facadeGym.updateTrainee(credentials, 14L, "Antonio", "Ibarra", true,null, null));
        context.close();
    }
}
