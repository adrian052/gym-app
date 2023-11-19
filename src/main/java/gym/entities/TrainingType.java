package gym.entities;


public class TrainingType implements Entity{
    private Long id;

    private String trainingTypeName;

    public TrainingType(Long id, String trainingTypeName) {
        this.id = id;
        this.trainingTypeName = trainingTypeName;
    }

    public TrainingType() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainingTypeName() {
        return trainingTypeName;
    }

    public void setTrainingTypeName(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }
    @Override
    public String toString() {
        return "Type ID: " + id +
                "\nTraining Type: " + trainingTypeName;
    }
}
