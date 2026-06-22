package hibernate.repository;

import hibernate.entity.ReportEntity;
import hibernate.config.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ReportRepository extends GenericRepository<ReportEntity, Integer> {

    public ReportRepository() {
        super(ReportEntity.class);
    }

    public List<ReportEntity> findByTutorId(Integer tutorId) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("FROM ReportEntity r WHERE r.tutor.tutorId = :tid", ReportEntity.class)
                    .setParameter("tid", tutorId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<ReportEntity> findByParticipantId(Integer participantId) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("FROM ReportEntity r WHERE r.participant.participantId = :pid", ReportEntity.class)
                    .setParameter("pid", participantId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}