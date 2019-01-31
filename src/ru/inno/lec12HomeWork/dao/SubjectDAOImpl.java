package ru.inno.lec12HomeWork.dao;

import ru.inno.lec12HomeWork.dao.StatementToList.StatementToList;
import ru.inno.lec12HomeWork.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * DAO-класс для работы с сущностью "предмет"
 */
public class SubjectDAOImpl implements SubjectDAO {

    /**
     * SQL-запрос на выборку всех предметов
     */
    private static final String SELECT_ALL_SUBJECTS_SQL_TEMPLATE =
            "select * from subject";

    /**
     * SQL-запрос на вставку предмета с возвратом новоиспечённого id
     */
    private static final String INSERT_SUBJECT_SQL_TEMPLATE =
            "insert into subject (description) values (?) returning subject_id";

    /**
     * SQL-запрос на редактирование предмета с возвратом количества отредактированных
     */
    private static final String UPDATE_SUBJECT_SQL_TEMPLATE =
            "with rows as (update subject set description = ? where subject_id = ? returning 1) "
                    + "select count(*) from rows";

    /**
     * SQL-запрос на удаление предмета
     */
    private static final String DELETE_SUBJECT_SQL_TEMPLATE =
            "delete from subject where subject_id = ?";

    /**
     * SQL-запрос на выборку предмета по названию
     */
    private static final String SELECT_SUBJECT_BY_NAME_SQL_TEMPLATE =
            "select * from subject where description = ?";

    /**
     * объект-подключение к БД
     */
    private final Connection connection;

    /**
     * Конструктор
     *
     * @param connection объект-подключение к БД
     */
    public SubjectDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Subject> getAllSubjects() throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_ALL_SUBJECTS_SQL_TEMPLATE)) {
            return StatementToList.statementToSubjectList(statement);
        }
    }

    @Override
    public void addSubject(Subject subject) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(INSERT_SUBJECT_SQL_TEMPLATE)) {
            statement.setString(1, subject.getDescription());

            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    int id = res.getInt("subject_id");
                    // устанавливаем объекту полученный в таблице id
                    subject.setId(id);
                }
            }
        }
    }

    @Override
    public void updateSubject(Subject subject) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(UPDATE_SUBJECT_SQL_TEMPLATE)) {
            statement.setString(1, subject.getDescription());
            statement.setInt(2, subject.getId());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                if (res.getInt("count") > 0) {
                    return;
                }
            }
            // если неотредактирован ни один предмет
            throw new SQLException("Редактирование несуществующего предмета!");
        }
    }

    @Override
    public void deleteSubject(Subject subject) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(DELETE_SUBJECT_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            statement.execute();
        }
    }

    @Override
    public Collection<Subject> getSubjectByName(String name) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_SUBJECT_BY_NAME_SQL_TEMPLATE)) {
            statement.setString(1, name);
            return StatementToList.statementToSubjectList(statement);
        }
    }
}
