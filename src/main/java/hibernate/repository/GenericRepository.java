package hibernate.repository;

import hibernate.config.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public abstract class GenericRepository<T, ID> {
    private static final Logger logger = LoggerFactory.getLogger(GenericRepository.class);
    private final Class<T> entityClass;

    public GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T save(T entity) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
            logger.info("Сохранено: {}", entity);
            return entity;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logger.error("Ошибка сохранения: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public Optional<T> findById(ID id) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return Optional.ofNullable(em.find(entityClass, id));
        } finally {
            em.close();
        }
    }

    public List<T> findAll() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public T update(T entity) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T merged = em.merge(entity);
            tx.commit();
            logger.info("Обновлено: {}", merged);
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logger.error("Ошибка обновления: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(ID id) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                tx.commit();
                logger.info("Удалено: id={}", id);
                return true;
            }
            tx.commit();
            logger.warn("Объект с id={} не найден", id);
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logger.error("Ошибка удаления: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}