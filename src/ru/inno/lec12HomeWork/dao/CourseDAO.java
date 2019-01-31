package ru.inno.lec12HomeWork.dao;

import ru.inno.lec12HomeWork.entity.Person;
import ru.inno.lec12HomeWork.entity.Subject;

import java.sql.SQLException;
import java.util.Collection;

/**
 * DAO-интерфейс для работы со связью студент-предмет
 */
public interface CourseDAO {

    /**
     * соединяет студента с предметом(-ами)
     *
     * @param person   студент
     * @param subjects предмет(-ы)
     */
    void linkPersonToSubjects(Person person, Subject... subjects) throws SQLException;

    /**
     * разъединяет студента с предметом(-ами)
     *
     * @param person   студент
     * @param subjects предмет(-ы)
     */
    void unlinkPersonFromSubjects(Person person, Subject... subjects) throws SQLException;

    /**
     * соединяет предмет со студентом(-ами)
     *
     * @param subject предмет
     * @param persons студент(-ы)
     */
    void linkSubjectToPersons(Subject subject, Person... persons) throws SQLException;

    /**
     * разъединяет предмет со студентом(-ами)
     *
     * @param subject предмет
     * @param persons студент(-ы)
     */
    void unlinkSubjectFromPersons(Subject subject, Person... persons) throws SQLException;

    /**
     * возвращает список студентов у предмета
     *
     * @param subject предмет
     * @return список студентов
     */
    Collection<Person> getPersonsBySubject(Subject subject) throws SQLException;

    /**
     * возвращает список предметов у студента
     *
     * @param person студент
     * @return список предметов
     */
    Collection<Subject> getSubjectsByPerson(Person person) throws SQLException;
}
