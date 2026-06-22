package hibernate.service;

import hibernate.config.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class BusinessQueryService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessQueryService.class);

    private void printHeader(String title) {
        System.out.println(title + ":");
        System.out.println("─".repeat(50));
    }

    // 1. Топ-5 участников по баллам
    public void showTopParticipants() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            List<Object[]> results = em.createQuery("""
                SELECT p.firstName, p.lastName, p.pointsQuantity
                FROM ParticipantEntity p
                ORDER BY p.pointsQuantity DESC
                """, Object[].class)
                    .setMaxResults(5)
                    .getResultList();

            printHeader("Топ-5 участников по баллам");
            int rank = 1;
            for (Object[] row : results) {
                System.out.printf("   %d. %s %s - %d баллов%n", rank++, row[0], row[1], row[2]);
            }
        } finally {
            em.close();
        }
    }

    // 2. Участники по марафонам
    public void showParticipantsByMarathon() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            List<Object[]> results = em.createQuery("""
                SELECT m.name, COUNT(r)
                FROM MarathonEntity m
                LEFT JOIN RegistrationEntity r ON r.marathon = m
                GROUP BY m.name
                ORDER BY COUNT(r) DESC
                """, Object[].class)
                    .getResultList();

            printHeader("Участники по марафонам");
            for (Object[] row : results) {
                System.out.printf("   %s - %d участников%n", row[0], row[1]);
            }
        } finally {
            em.close();
        }
    }

    // 3. Выручка по марафонам
    public void showRevenueByMarathon() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            List<Object[]> results = em.createQuery("""
                SELECT m.name, COUNT(r), SUM(r.tariff)
                FROM MarathonEntity m
                LEFT JOIN RegistrationEntity r ON r.marathon = m
                GROUP BY m.name
                ORDER BY SUM(r.tariff) DESC
                """, Object[].class)
                    .getResultList();

            printHeader("Выручка по марафонам");
            for (Object[] row : results) {
                System.out.printf("   %s - %d регистраций, выручка: %.2f ₽%n",
                        row[0], row[1], row[2] != null ? row[2] : BigDecimal.ZERO);
            }
        } finally {
            em.close();
        }
    }

    // 4. Самые дорогие марафоны
    public void showMostExpensiveMarathons() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            List<Object[]> results = em.createQuery("""
                SELECT m.name, m.price, m.duration, m.dateStart
                FROM MarathonEntity m
                ORDER BY m.price DESC
                """, Object[].class)
                    .setMaxResults(5)
                    .getResultList();

            printHeader("Самые дорогие марафоны");
            for (Object[] row : results) {
                System.out.printf("   %s - %.2f ₽, %d дней, старт: %s%n", row[0], row[1], row[2], row[3]);
            }
        } finally {
            em.close();
        }
    }

    // 5. Тренеры и их марафоны
    public void showCoachesAndMarathons() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            List<Object[]> results = em.createQuery("""
                SELECT c.firstName, c.lastName, COUNT(m)
                FROM CoachEntity c
                LEFT JOIN MarathonEntity m ON m.coach = c
                GROUP BY c.coachId, c.firstName, c.lastName
                ORDER BY COUNT(m) DESC
                """, Object[].class)
                    .getResultList();

            printHeader("Тренеры и их марафоны");
            for (Object[] row : results) {
                System.out.printf("   %s %s - %d марафонов%n", row[0], row[1], row[2]);
            }
        } finally {
            em.close();
        }
    }

    // 6. Статистика по отчетам
    public void showReportStatistics() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            Object[] result = em.createQuery("""
                SELECT COUNT(r),
                       SUM(CASE WHEN r.reportStatus = true THEN 1 ELSE 0 END),
                       SUM(CASE WHEN r.reportStatus = false THEN 1 ELSE 0 END)
                FROM ReportEntity r
                """, Object[].class)
                    .getSingleResult();

            printHeader("Статистика по отчетам");
            System.out.printf("   Всего отчетов: %d%n", result[0]);
            System.out.printf("   Завершенных: %d%n", result[1]);
            System.out.printf("   Ожидают проверки: %d%n", result[2]);
        } finally {
            em.close();
        }
    }

    // 7. Самый активный участник
    public void showMostActiveParticipant() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            Object[] result = em.createQuery("""
                SELECT p.firstName, p.lastName, COUNT(r)
                FROM ParticipantEntity p
                LEFT JOIN RegistrationEntity r ON r.participant = p
                GROUP BY p.participantId, p.firstName, p.lastName
                ORDER BY COUNT(r) DESC
                """, Object[].class)
                    .setMaxResults(1)
                    .getSingleResult();

            printHeader("Самый активный участник");
            System.out.printf("   %s %s - %d регистраций%n", result[0], result[1], result[2]);
        } finally {
            em.close();
        }
    }

    // 8. Средняя цена марафонов
    public void showMarathonPriceStats() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            Object[] result = em.createQuery("""
                SELECT COUNT(m), AVG(m.price), MIN(m.price), MAX(m.price)
                FROM MarathonEntity m
                """, Object[].class)
                    .getSingleResult();

            printHeader("Средняя цена марафонов");
            System.out.printf("   Всего марафонов: %d%n", result[0]);
            System.out.printf("   Средняя цена: %.2f ₽%n", result[1]);
            System.out.printf("   Минимальная цена: %.2f ₽%n", result[2]);
            System.out.printf("   Максимальная цена: %.2f ₽%n", result[3]);
        } finally {
            em.close();
        }
    }

    // 9. Самый длительный марафон
    public void showLongestMarathon() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            Object[] result = em.createQuery("""
                SELECT m.name, m.duration, m.price, m.dateStart
                FROM MarathonEntity m
                ORDER BY m.duration DESC
                """, Object[].class)
                    .setMaxResults(1)
                    .getSingleResult();

            printHeader("Самый длительный марафон");
            System.out.printf("   %s - %d дней, %.2f ₽, старт: %s%n", result[0], result[1], result[2], result[3]);
        } finally {
            em.close();
        }
    }

    // 10. Сводная статистика
    public void showSystemStats() {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            Object[] result = em.createQuery("""
                SELECT (SELECT COUNT(c) FROM CoachEntity c),
                       (SELECT COUNT(p) FROM ParticipantEntity p),
                       (SELECT COUNT(m) FROM MarathonEntity m),
                       (SELECT COUNT(r) FROM RegistrationEntity r),
                       (SELECT COUNT(rp) FROM ReportEntity rp)
                """, Object[].class)
                    .getSingleResult();

            printHeader("Сводная статистика");
            System.out.printf("   Тренеров: %d%n", result[0]);
            System.out.printf("   Участников: %d%n", result[1]);
            System.out.printf("   Марафонов: %d%n", result[2]);
            System.out.printf("   Регистраций: %d%n", result[3]);
            System.out.printf("   Отчетов: %d%n", result[4]);
        } finally {
            em.close();
        }
    }

    // Все запросы сразу
    public void showAllQueries() {
        showTopParticipants();
        showParticipantsByMarathon();
        showRevenueByMarathon();
        showMostExpensiveMarathons();
        showCoachesAndMarathons();
        showReportStatistics();
        showMostActiveParticipant();
        showMarathonPriceStats();
        showLongestMarathon();
        showSystemStats();
    }
}