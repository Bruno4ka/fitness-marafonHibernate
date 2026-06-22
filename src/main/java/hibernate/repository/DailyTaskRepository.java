package hibernate.repository;

import hibernate.entity.DailyTaskEntity;
import hibernate.config.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DailyTaskRepository extends GenericRepository<DailyTaskEntity, Integer> {

    public DailyTaskRepository() {
        super(DailyTaskEntity.class);
    }

    public List<DailyTaskEntity> findByTaskType(String taskType) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("FROM DailyTaskEntity d WHERE d.taskType ILIKE :type", DailyTaskEntity.class)
                    .setParameter("type", "%" + taskType + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}