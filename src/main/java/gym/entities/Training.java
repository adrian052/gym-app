package gym.entities;


import jakarta.persistence.*;
import java.util.Date;


@Entity
public class Training implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

    @Column(name="training_date", nullable = false)
    private Date trainingDate;

    @Column(name="training_duration", nullable = false)
    private int trainingDuration;

    public Training() {
    }

    public Training(Long id, Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType, Date trainingDate, int trainingDuration) {
        this.id = id;
        this.trainee = trainee;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }
    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public int getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(int trainingDuration) {
        this.trainingDuration = trainingDuration;
    }
    
    @Override
    public String toString() {
        return "Training ID: " + id +
                "\nTraining Name: " + trainingName +
                "\nTraining Date: " + trainingDate +
                "\nDuration: " + trainingDuration + " minutes" +
                "\nTrainee: " + trainee.getUser().getFirstName() + " " + trainee.getUser().getLastName() +
                "\nTrainer: " + trainer.getUser().getFirstName() + " " + trainer.getUser().getLastName() +
                "\nTraining Type: " + trainingType.getTrainingTypeName();
    }
}