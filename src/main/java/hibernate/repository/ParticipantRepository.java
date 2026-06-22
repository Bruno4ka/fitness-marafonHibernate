package hibernate.repository;

import hibernate.entity.ParticipantEntity;
import hibernate.config.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ParticipantRepository extends GenericRepository<ParticipantEntity, Integer> {

    public ParticipantRepository() {
        super(ParticipantEntity.class);
    }

    public List<ParticipantEntity> findTopByPoints(int limit) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("FROM ParticipantEntity p ORDER BY p.pointsQuantity DESC", ParticipantEntity.class)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<ParticipantEntity> findAllWithRegistrations() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT p FROM ParticipantEntity p LEFT JOIN FETCH p.registrations", ParticipantEntity.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}