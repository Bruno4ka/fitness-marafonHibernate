package hibernate.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily_task")
public class DailyTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dailyTask_id")
    private Integer dailyTaskId;

    @Column(name = "taskType", nullable = false)
    private String taskType;

    @Column(name = "typeStatus", nullable = false)
    private Boolean typeStatus = false;

    @OneToMany(mappedBy = "dailyTask", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MarathonEntity> marathons = new ArrayList<>();

    public DailyTaskEntity() {}

    public DailyTaskEntity(String taskType, Boolean typeStatus) {
        this.taskType = taskType;
        this.typeStatus = typeStatus;
    }

    public Integer getDailyTaskId() { return dailyTaskId; }
    public void setDailyTaskId(Integer dailyTaskId) { this.dailyTaskId = dailyTaskId; }
    public String getTaskType() { return taskType; }
    public void setTaskType(String taskType) { this.taskType = taskType; }
    public Boolean getTypeStatus() { return typeStatus; }
    public void setTypeStatus(Boolean typeStatus) { this.typeStatus = typeStatus; }
    public List<MarathonEntity> getMarathons() { return marathons; }
    public void setMarathons(List<MarathonEntity> marathons) { this.marathons = marathons; }

    @Override
    public String toString() {
        return "DailyTaskEntity{id=" + dailyTaskId + ", type='" + taskType + "', status=" + typeStatus + "}";
    }
}