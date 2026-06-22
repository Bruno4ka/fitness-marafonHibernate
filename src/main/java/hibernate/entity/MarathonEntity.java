package hibernate.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "marathon")
public class MarathonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marathon_id")
    private Integer marathonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id", nullable = false)
    private CoachEntity coach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dailyTask_id", nullable = false)
    private DailyTaskEntity dailyTask;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "price", precision = 8, scale = 2)
    private BigDecimal price;

    @Column(name = "date_start", nullable = false)
    private LocalDate dateStart;

    @OneToMany(mappedBy = "marathon", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RegistrationEntity> registrations = new ArrayList<>();

    public MarathonEntity() {}

    public MarathonEntity(CoachEntity coach, DailyTaskEntity dailyTask, String name,
                          Integer duration, BigDecimal price, LocalDate dateStart) {
        this.coach = coach;
        this.dailyTask = dailyTask;
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.dateStart = dateStart;
    }

    public Integer getMarathonId() { return marathonId; }
    public void setMarathonId(Integer marathonId) { this.marathonId = marathonId; }
    public CoachEntity getCoach() { return coach; }
    public void setCoach(CoachEntity coach) { this.coach = coach; }
    public DailyTaskEntity getDailyTask() { return dailyTask; }
    public void setDailyTask(DailyTaskEntity dailyTask) { this.dailyTask = dailyTask; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public LocalDate getDateStart() { return dateStart; }
    public void setDateStart(LocalDate dateStart) { this.dateStart = dateStart; }
    public List<RegistrationEntity> getRegistrations() { return registrations; }
    public void setRegistrations(List<RegistrationEntity> registrations) { this.registrations = registrations; }

    @Override
    public String toString() {
        return "MarathonEntity{id=" + marathonId + ", name='" + name + "', price=" + price + "}";
    }
}