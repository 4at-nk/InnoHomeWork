package ru.inno.lec12HomeWork.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.lec12HomeWork.dao.StatementToList.StatementToList;
import ru.inno.lec12HomeWork.entity.Person;
import ru.inno.lec12HomeWork.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * DAO-класс для работы с сущностью "предмет"
 */
public class SubjectDAOImpl implements SubjectDAO {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SubjectDAOImpl.class);

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
        LOGGER.info("Попытка получения списка всех предметов");

        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_ALL_SUBJECTS_SQL_TEMPLATE)) {
            List<Subject> allSubjects = StatementToList.statementToSubjectList(statement);
            LOGGER.info("Список всех предметов получен");
            return allSubjects;
        } catch (SQLException e) {
            LOGGER.error("Список всех предметов не был получен");
            throw e;
        }
    }

    @Override
    public void addSubject(Subject subject) throws SQLException {
        LOGGER.info("Попытка добавления предмета: название - {}",
                subject.getDescription());

        try (PreparedStatement statement =
                     connection.prepareStatement(INSERT_SUBJECT_SQL_TEMPLATE)) {
            statement.setString(1, subject.getDescription());

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    int id = res.getInt("subject_id");
                    // устанавливаем объекту полученный в таблице id
                    subject.setId(id);

                    LOGGER.info("Предмет успешно добавлен, subject_id = {}", id);
                } else {
                    LOGGER.error("SQL-запрос не вернул subject_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Предмет не добавлен");
            throw e;
        }
    }

    @Override
    public void updateSubject(Subject subject) throws SQLException {
        LOGGER.info("Попытка редактирования предмета: id - {}, название - {}",
                subject.getId(), subject.getDescription());

        try (PreparedStatement statement =
                     connection.prepareStatement(UPDATE_SUBJECT_SQL_TEMPLATE)) {
            statement.setString(1, subject.getDescription());
            statement.setInt(2, subject.getId());

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    if (res.getInt("count") != 0) {
                        LOGGER.info("Предмет успешно отредактирован");
                        return;
                    } else {
                        LOGGER.error("Предмет с таким id не найден");
                        throw new SQLException("Редактирование несуществующего предмета!");
                    }
                } else {
                    LOGGER.error("SQL-запрос не вернул count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Предмет не отредактирован");
            throw e;
        }
    }

    @Override
    public void deleteSubject(Subject subject) throws SQLException {
        LOGGER.info("Попытка удаления предмета: id - {}, название - {}",
                subject.getId(), subject.getDescription());

        try (PreparedStatement statement =
                     connection.prepareStatement(DELETE_SUBJECT_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            statement.execute();

            LOGGER.info("Предмет успешно удалён");
        } catch (SQLException e) {
            LOGGER.error("Предмет не удалён");
            throw e;
        }
    }

    @Override
    public Collection<Subject> getSubjectByName(String name) throws SQLException {
        LOGGER.info("Попытка получения предмета(-ов) по названию: {}", name);

        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_SUBJECT_BY_NAME_SQL_TEMPLATE)) {
            statement.setString(1, name);

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            List<Subject> subjectList = StatementToList.statementToSubjectList(statement);

            LOGGER.info("Предмет(-ы) получен(-ы)");
            return subjectList;
        } catch (SQLException e) {
            LOGGER.error("Предмет(-ы) не получен(-ы)");
            throw e;
        }
    }
}
