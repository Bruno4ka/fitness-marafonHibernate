package hibernate.repository;

import hibernate.entity.RegistrationEntity;
import hibernate.config.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RegistrationRepository extends GenericRepository<RegistrationEntity, Integer> {

    public RegistrationRepository() {
        super(RegistrationEntity.class);
    }

    public List<RegistrationEntity> findByParticipantId(Integer participantId) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("FROM RegistrationEntity r WHERE r.participant.participantId = :pid", RegistrationEntity.class)
                    .setParameter("pid", participantId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<RegistrationEntity> findByMarathonId(Integer marathonId) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("FROM RegistrationEntity r WHERE r.marathon.marathonId = :mid", RegistrationEntity.class)
                    .setParameter("mid", marathonId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}