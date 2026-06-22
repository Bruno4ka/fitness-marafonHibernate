package hibernate.service;

import hibernate.entity.CoachEntity;
import hibernate.entity.ParticipantEntity;
import hibernate.repository.CoachRepository;
import hibernate.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class CrudDemoService {
    private static final Logger logger = LoggerFactory.getLogger(CrudDemoService.class);

    public void demo() {
        logger.info("========== HIBERNATE CRUD ДЕМОНСТРАЦИЯ ==========\n");

        CoachRepository coachRepo = new CoachRepository();
        ParticipantRepository participantRepo = new ParticipantRepository();

        // 1. CREATE
        logger.info("1. CREATE - Создание тренера:");
        CoachEntity newCoach = new CoachEntity("Hibernate", "Test");
        coachRepo.save(newCoach);
        System.out.println("   Создан: " + newCoach + "\n");

        // 2. READ (все)
        logger.info("2. READ - Все тренеры:");
        List<CoachEntity> coaches = coachRepo.findAll();
        coaches.forEach(c -> System.out.println("   " + c));
        System.out.println();

        // 3. READ (по ID)
        logger.info("3. READ - Поиск тренера по ID=1:");
        coachRepo.findById(1).ifPresentOrElse(
                c -> System.out.println("   Найден: " + c),
                () -> System.out.println(" Тренер не найден")
        );
        System.out.println();

        // 4. UPDATE
        logger.info("4. UPDATE - Обновление тренера:");
        coachRepo.findById(1).ifPresent(coach -> {
            coach.setFirstName("HibernateUpdated");
            coachRepo.update(coach);
            System.out.println("   Обновлен: " + coach);
        });
        System.out.println();

        // 5. DELETE
        logger.info("5. DELETE - Удаление созданного тренера:");
        int idToDelete = newCoach.getCoachId();
        boolean deleted = coachRepo.deleteById(idToDelete);
        System.out.println("   Удален: " + (deleted ? "да (id=" + idToDelete + ")" : "нет"));
        System.out.println();

        // 6. Participant
        logger.info("6. CREATE - Создание участника:");
        ParticipantEntity newParticipant = new ParticipantEntity(
                "HibernateUser", "Test", "+7-999-888-77-66",
                LocalDate.now(), 100
        );
        participantRepo.save(newParticipant);
        System.out.println("   Создан: " + newParticipant + "\n");

        // 7. Топ участников
        logger.info("7. READ - Топ-3 участников по баллам:");
        participantRepo.findTopByPoints(3).forEach(p -> System.out.println("   " + p));
        System.out.println();

        // 8. Удаление
        logger.info("8. DELETE - Удаление участника:");
        participantRepo.deleteById(newParticipant.getParticipantId());
        System.out.println();

        // 9. COUNT
        logger.info("9. COUNT - Количество тренеров:");
        System.out.println("   Всего тренеров: " + coachRepo.count() + "\n");

        logger.info("========== HIBERNATE CRUD ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА ==========");
    }
}