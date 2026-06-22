package hibernate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id", nullable = false)
    private TutorEntity tutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private ParticipantEntity participant;

    @Column(name = "link_photo")
    private String linkPhoto;

    @Column(name = "tasks_proof")
    private String tasksProof;

    @Column(name = "report_status", nullable = false)
    private Boolean reportStatus = false;

    public ReportEntity() {}

    public ReportEntity(TutorEntity tutor, ParticipantEntity participant,
                        String linkPhoto, String tasksProof, Boolean reportStatus) {
        this.tutor = tutor;
        this.participant = participant;
        this.linkPhoto = linkPhoto;
        this.tasksProof = tasksProof;
        this.reportStatus = reportStatus;
    }

    public Integer getReportId() { return reportId; }
    public void setReportId(Integer reportId) { this.reportId = reportId; }
    public TutorEntity getTutor() { return tutor; }
    public void setTutor(TutorEntity tutor) { this.tutor = tutor; }
    public ParticipantEntity getParticipant() { return participant; }
    public void setParticipant(ParticipantEntity participant) { this.participant = participant; }
    public String getLinkPhoto() { return linkPhoto; }
    public void setLinkPhoto(String linkPhoto) { this.linkPhoto = linkPhoto; }
    public String getTasksProof() { return tasksProof; }
    public void setTasksProof(String tasksProof) { this.tasksProof = tasksProof; }
    public Boolean getReportStatus() { return reportStatus; }
    public void setReportStatus(Boolean reportStatus) { this.reportStatus = reportStatus; }

    @Override
    public String toString() {
        return "ReportEntity{id=" + reportId + ", status=" + reportStatus + "}";
    }
}