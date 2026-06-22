package hibernate.repository;

import hibernate.entity.MarathonEntity;
import hibernate.config.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class MarathonRepository extends GenericRepository<MarathonEntity, Integer> {

    public MarathonRepository() {
        super(MarathonEntity.class);
    }

    public List<MarathonEntity> findByCoachId(Integer coachId) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("FROM MarathonEntity m WHERE m.coach.coachId = :coachId", MarathonEntity.class)
                    .setParameter("coachId", coachId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<MarathonEntity> findMostExpensive(int limit) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("FROM MarathonEntity m ORDER BY m.price DESC", MarathonEntity.class)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public BigDecimal getAveragePrice() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("SELECT AVG(m.price) FROM MarathonEntity m", BigDecimal.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}