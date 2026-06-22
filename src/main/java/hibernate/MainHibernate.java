package hibernate;

import hibernate.config.HibernateUtil;
import hibernate.service.CrudDemoService;
import hibernate.service.BusinessQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainHibernate {
    private static final Logger logger = LoggerFactory.getLogger(MainHibernate.class);

    public static void main(String[] args) {
        System.out.println("""
        ╔══════════════════════════════════════════════════════════╗
        ║                                                          ║
        ║         СИСТЕМА УПРАВЛЕНИЯ МАРАФОНАМИ(Hibernate)         ║
        ║                                                          ║
        ╚══════════════════════════════════════════════════════════╝
        """);

        try {
            HibernateUtil.getEntityManagerFactory();
            logger.info("Подключение к БД установлено!\n");

            CrudDemoService crudDemo = new CrudDemoService();
            crudDemo.demo();

            BusinessQueryService bizQuery = new BusinessQueryService();
            bizQuery.showAllQueries();

            HibernateUtil.close();

        } catch (Exception e) {
            logger.error("Ошибка: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}