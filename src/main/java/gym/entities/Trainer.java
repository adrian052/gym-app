package gym.entities;

public class Trainer {
    private Long id;

    private TrainingType specialization;

    private User user;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }


    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Trainer ID: " + id +
                "\nName: " + user.getFirstName() + " " + user.getLastName() +
                "\nSpecialization: " + specialization.getTrainingTypeName() +
                "\nUser ID: " + user.getId();
    }
}