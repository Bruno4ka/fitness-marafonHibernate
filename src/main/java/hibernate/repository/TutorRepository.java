package hibernate.repository;

import hibernate.entity.TutorEntity;
import hibernate.config.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TutorRepository extends GenericRepository<TutorEntity, Integer> {

    public TutorRepository() {
        super(TutorEntity.class);
    }

    public List<TutorEntity> findByFirstName(String firstName) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("FROM TutorEntity t WHERE t.firstName ILIKE :name", TutorEntity.class)
                    .setParameter("name", "%" + firstName + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}