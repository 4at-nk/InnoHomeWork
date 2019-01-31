package ru.inno.lec12HomeWork.dao;

import ru.inno.lec12HomeWork.entity.Person;

import java.sql.SQLException;
import java.util.Collection;

/**
 * DAO-интерфейс для работы с сущностью "студент"
 */
public interface PersonDAO {

    /**
     * возвращает всех студентов из таблицы person
     *
     * @return список всех студентов
     */
    Collection<Person> getAllPersons() throws SQLException;

    /**
     * добавляет студента в таблицу person
     *
     * @param person студент, которого нужно добавить
     */
    void addPerson(Person person) throws SQLException;

    /**
     * редактирует конкретного студента в таблице person
     *
     * @param person студент, которго нужно редактировать
     */
    void updatePerson(Person person) throws SQLException;

    /**
     * удаляет конкретного студента из таблицы person
     *
     * @param person студент, которого нужно удалить
     */
    void deletePerson(Person person) throws SQLException;

    /**
     * ищет студента(-ов) в таблице person по имени
     *
     * @param name имя искомого студента
     * @return список студентов с подходящим именем
     */
    Collection<Person> getPersonByName(String name) throws SQLException;
}
