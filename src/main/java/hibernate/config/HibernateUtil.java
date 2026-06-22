package hibernate.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static volatile EntityManagerFactory emf;

    private HibernateUtil() {}

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            synchronized (HibernateUtil.class) {
                if (emf == null) {
                    try {
                        emf = Persistence.createEntityManagerFactory("marafon-pu");
                        logger.info("EntityManagerFactory создана!");
                    } catch (Exception e) {
                        logger.error("Ошибка создания EntityManagerFactory: {}", e.getMessage());
                        throw new RuntimeException("Ошибка инициализации Hibernate", e);
                    }
                }
            }
        }
        return emf;
    }

    public static EntityManager createEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            logger.info("EntityManagerFactory закрыта");
        }
    }
}