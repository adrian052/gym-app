package gym.entities;


import java.util.Date;

public class Trainee {
    private Long id;

    private Date dateOfBirth;

    private String address;

    private User user;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String toString() {
        return "Trainee ID: " + id +
                "\nName: " + user.getFirstName() + " " + user.getLastName() +
                "\nDate of Birth: " + dateOfBirth +
                "\nAddress: " + address +
                "\nUser ID: " + user.getId();
    }
}
