package hibernate.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "registration")
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Integer registrationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private ParticipantEntity participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marathon", nullable = false)
    private MarathonEntity marathon;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "tariff", precision = 8, scale = 2)
    private BigDecimal tariff;

    public RegistrationEntity() {}

    public RegistrationEntity(ParticipantEntity participant, MarathonEntity marathon,
                              LocalDate registrationDate, BigDecimal tariff) {
        this.participant = participant;
        this.marathon = marathon;
        this.registrationDate = registrationDate;
        this.tariff = tariff;
    }

    public Integer getRegistrationId() { return registrationId; }
    public void setRegistrationId(Integer registrationId) { this.registrationId = registrationId; }
    public ParticipantEntity getParticipant() { return participant; }
    public void setParticipant(ParticipantEntity participant) { this.participant = participant; }
    public MarathonEntity getMarathon() { return marathon; }
    public void setMarathon(MarathonEntity marathon) { this.marathon = marathon; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    public BigDecimal getTariff() { return tariff; }
    public void setTariff(BigDecimal tariff) { this.tariff = tariff; }

    @Override
    public String toString() {
        return "RegistrationEntity{id=" + registrationId + "}";
    }
}