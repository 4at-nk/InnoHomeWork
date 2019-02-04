package ru.inno.lec12HomeWork.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.lec12HomeWork.dao.StatementToList.StatementToList;
import ru.inno.lec12HomeWork.entity.Person;
import ru.inno.lec12HomeWork.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * DAO-класс для работы со связью студент-предмет
 */
public class CourseDAOImpl implements CourseDAO {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CourseDAOImpl.class);

    /**
     * SQL-запрос на вставку связи студент-предмет
     */
    private static final String INSERT_COURSE_SQL_TEMPLATE =
            "insert into course (person_id, subject_id) values (?, ?) on conflict do nothing";

    /**
     * SQL-запрос на удаление связи студент-предмет
     */
    private static final String DELETE_COURSE_SQL_TEMPLATE =
            "delete from course where person_id = ? and subject_id = ?";

    /**
     * SQL-запрос на выборку всех студентов на определённом предмете
     */
    private static final String SELECT_PERSONS_BY_SUBJECT_SQL_TEMPLATE =
            "select * from person as p join course as c on p.person_id = c.person_id"
                    + " join subject as s on c.subject_id = s.subject_id where s.subject_id = ?";

    /**
     * SQL-запрос на выборку всех предметов определённого студента
     */
    private static final String SELECT_SUBJECTS_BY_PERSON_SQL_TEMPLATE =
            "select * from subject as s join course c on s.subject_id = c.subject_id"
                    + " join person p on c.person_id = p.person_id where c.person_id = ?";

    /**
     * объект-подключение к БД
     */
    private final Connection connection;

    /**
     * Конструктор
     *
     * @param connection объект-подключение к БД
     */
    public CourseDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * добавляет связь студент-предмет
     *
     * @param person  студент
     * @param subject предмет
     */
    private void linkPersonToSubject(Person person, Subject subject) throws SQLException {
        LOGGER.info("Попытка добавления связи студент-предмет: "
                        + "id студента - {}, имя студента - {}, дата рождения студента - {}, "
                        + "id предмета - {}, название предмета - {}", person.getId(), person.getName(),
                person.getBirthDate(), subject.getId(), subject.getDescription());

        try (PreparedStatement statement =
                     connection.prepareStatement(INSERT_COURSE_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());
            statement.setInt(2, subject.getId());

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            statement.execute();

            LOGGER.info("Связь студент-предмет успешно добавлена");
        } catch (SQLException e) {
            LOGGER.error("Связь студент-предмет не добавлена");
            throw e;
        }
    }

    /**
     * удаляет связь студент-предмет
     *
     * @param person  студент
     * @param subject предмет
     */
    private void unlinkPersonFromSubject(Person person, Subject subject) throws SQLException {
        LOGGER.info("Попытка удаления связи студент-предмет: "
                        + "id студента - {}, имя студента - {}, дата рождения студента - {}, "
                        + "id предмета - {}, название предмета - {}", person.getId(), person.getName(),
                person.getBirthDate(), subject.getId(), subject.getDescription());

        try (PreparedStatement statement =
                     connection.prepareStatement(DELETE_COURSE_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());
            statement.setInt(2, subject.getId());

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            statement.execute();

            LOGGER.info("Связь студент-предмет успешно удалена");
        } catch (SQLException e) {
            LOGGER.error("Связь студент-предмет не удалена");
            throw e;
        }
    }

    @Override
    public void linkPersonToSubjects(Person person, Subject... subjects) throws SQLException {
        LOGGER.info("Попытка добавления студенту предмета(-ов)");
        for (Subject subject : subjects) {
            linkPersonToSubject(person, subject);
        }
        LOGGER.info("Предмет(-ы) студенту успешно добавлен(-ы)");
    }

    @Override
    public void unlinkPersonFromSubjects(Person person, Subject... subjects) throws SQLException {
        LOGGER.info("Попытка удаления у студента предмета(-ов)");
        for (Subject subject : subjects) {
            unlinkPersonFromSubject(person, subject);
        }
        LOGGER.info("Предмет(-ы) у студента успешно удален(-ы)");
    }

    @Override
    public void linkSubjectToPersons(Subject subject, Person... persons) throws SQLException {
        LOGGER.info("Попытка добавления предмету студента(-ов)");
        for (Person person : persons) {
            linkPersonToSubject(person, subject);
        }
        LOGGER.info("Студент(-ы) предмету успешно добавлен(-ы)");
    }

    @Override
    public void unlinkSubjectFromPersons(Subject subject, Person... persons) throws SQLException {
        LOGGER.info("Попытка удаления у предмета студента(-ов)");
        for (Person person : persons) {
            unlinkPersonFromSubject(person, subject);
        }
        LOGGER.info("Студент(-ы) у предмета успешно удален(-ы)");
    }

    @Override
    public Collection<Person> getPersonsBySubject(Subject subject) throws SQLException {
        LOGGER.info("Попытка получения всех студентов предмета: id - {}, название - {}",
                subject.getId(), subject.getDescription());

        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_PERSONS_BY_SUBJECT_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            List<Person> personList = StatementToList.statementToPersonList(statement);

            LOGGER.info("Студенты успешно получены");
            return personList;
        } catch (SQLException e) {
            LOGGER.error("Студенты не получены");
            throw e;
        }
    }

    @Override
    public Collection<Subject> getSubjectsByPerson(Person person) throws SQLException {
        LOGGER.info("Попытка получения всех предметов студента: id - {}, имя - {}, дата рождения - {}",
                person.getId(), person.getName(), new Date(person.getBirthDate()));

        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_SUBJECTS_BY_PERSON_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            List<Subject> subjectList = StatementToList.statementToSubjectList(statement);

            LOGGER.info("Предметы успешно получены");
            return subjectList;
        } catch (SQLException e) {
            LOGGER.error("Предметы не получены");
            throw e;
        }
    }
}
