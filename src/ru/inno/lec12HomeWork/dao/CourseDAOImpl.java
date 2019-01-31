package ru.inno.lec12HomeWork.dao;

import ru.inno.lec12HomeWork.dao.StatementToList.StatementToList;
import ru.inno.lec12HomeWork.entity.Person;
import ru.inno.lec12HomeWork.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

/**
 * DAO-класс для работы со связью студент-предмет
 */
public class CourseDAOImpl implements CourseDAO {

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
        try (PreparedStatement statement =
                     connection.prepareStatement(INSERT_COURSE_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());
            statement.setInt(2, subject.getId());
            statement.execute();
        }
    }

    /**
     * удаляет связь студент-предмет
     *
     * @param person  студент
     * @param subject предмет
     */
    private void unlinkPersonFromSubject(Person person, Subject subject) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(DELETE_COURSE_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());
            statement.setInt(2, subject.getId());
            statement.execute();
        }
    }

    @Override
    public void linkPersonToSubjects(Person person, Subject... subjects) throws SQLException {
        for (Subject subject : subjects) {
            linkPersonToSubject(person, subject);
        }
    }

    @Override
    public void unlinkPersonFromSubjects(Person person, Subject... subjects) throws SQLException {
        for (Subject subject : subjects) {
            unlinkPersonFromSubject(person, subject);
        }
    }

    @Override
    public void linkSubjectToPersons(Subject subject, Person... persons) throws SQLException {
        for (Person person : persons) {
            linkPersonToSubject(person, subject);
        }
    }

    @Override
    public void unlinkSubjectFromPersons(Subject subject, Person... persons) throws SQLException {
        for (Person person : persons) {
            unlinkPersonFromSubject(person, subject);
        }
    }

    @Override
    public Collection<Person> getPersonsBySubject(Subject subject) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_PERSONS_BY_SUBJECT_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            return StatementToList.statementToPersonList(statement);
        }
    }

    @Override
    public Collection<Subject> getSubjectsByPerson(Person person) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_SUBJECTS_BY_PERSON_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());
            return StatementToList.statementToSubjectList(statement);
        }
    }
}
