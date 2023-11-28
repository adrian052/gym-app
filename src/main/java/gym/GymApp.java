package gym;

import gym.facade.FacadeDbGym;
import gym.service.Credentials;
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
        Credentials credentials = new Credentials("Adrian.Ibarra","7hrPIIXgTO");
        System.out.println(facadeGym.updateTrainee(credentials, 14L, "Antonio", "Ibarra", true,new Date(), "5 de Mayo"));
        context.close();
    }
}
