package hibernate.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "participant")
public class ParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Integer participantId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "phone_number", nullable = false, length = 25)
    private String phoneNumber;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "points_quantity", nullable = false)
    private Integer pointsQuantity = 0;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RegistrationEntity> registrations = new ArrayList<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ReportEntity> reports = new ArrayList<>();

    public ParticipantEntity() {}

    public ParticipantEntity(String firstName, String lastName, String phoneNumber,
                             LocalDate registrationDate, Integer pointsQuantity) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.pointsQuantity = pointsQuantity;
    }

    public Integer getParticipantId() { return participantId; }
    public void setParticipantId(Integer participantId) { this.participantId = participantId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    public Integer getPointsQuantity() { return pointsQuantity; }
    public void setPointsQuantity(Integer pointsQuantity) { this.pointsQuantity = pointsQuantity; }
    public List<RegistrationEntity> getRegistrations() { return registrations; }
    public void setRegistrations(List<RegistrationEntity> registrations) { this.registrations = registrations; }
    public List<ReportEntity> getReports() { return reports; }
    public void setReports(List<ReportEntity> reports) { this.reports = reports; }

    @Override
    public String toString() {
        return "ParticipantEntity{id=" + participantId + ", firstName='" + firstName +
                "', lastName='" + lastName + "', points=" + pointsQuantity + "}";
    }
}