package gym.entities;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Trainee implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Training> trainings;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Trainee() {
    }

    public Trainee(Long id, Date dateOfBirth, String address, User user) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.user = user;
    }

    // Getters and setters


    public List<Training> getTrainings() {
        return trainings;
    }

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
