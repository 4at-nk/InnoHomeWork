package ru.inno.lec12HomeWork.dao;

import ru.inno.lec12HomeWork.entity.Subject;

import java.sql.SQLException;
import java.util.Collection;

/**
 * DAO-интерфейс для работы с сущностью "предмет"
 */
public interface SubjectDAO {

    /**
     * возвращает все предметы из таблицы subject
     *
     * @return список всех предметов
     */
    Collection<Subject> getAllSubjects() throws SQLException;

    /**
     * добавляет предмет в таблицу subject
     *
     * @param subject предмет, который нужно добавить
     */
    void addSubject(Subject subject) throws SQLException;

    /**
     * редактирует конкретный предмет в таблице subject
     *
     * @param subject предмет, который нужно редактировать
     */
    void updateSubject(Subject subject) throws SQLException;

    /**
     * удаляет конкретный предмет из таблицы subject
     *
     * @param subject предмет, который нужно удалить
     */
    void deleteSubject(Subject subject) throws SQLException;

    /**
     * ищет предмет(-ы) в таблице subject по названию
     *
     * @param name название искомого предмета
     * @return список предметов с подходящим названием
     */
    Collection<Subject> getSubjectByName(String name) throws SQLException;
}
