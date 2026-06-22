package hibernate.repository;

import hibernate.entity.CoachEntity;
import hibernate.config.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CoachRepository extends GenericRepository<CoachEntity, Integer> {

    public CoachRepository() {
        super(CoachEntity.class);
    }

    public List<CoachEntity> findByFirstName(String firstName) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("FROM CoachEntity c WHERE c.firstName ILIKE :name", CoachEntity.class)
                    .setParameter("name", "%" + firstName + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<CoachEntity> findAllWithMarathons() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT c FROM CoachEntity c LEFT JOIN FETCH c.marathons", CoachEntity.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}