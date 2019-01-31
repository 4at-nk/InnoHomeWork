package ru.inno.lec12HomeWork.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.lec12HomeWork.dao.StatementToList.StatementToList;
import ru.inno.lec12HomeWork.entity.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * DAO-класс для работы с сущностью "студент"
 */
public class PersonDAOImpl implements PersonDAO {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PersonDAOImpl.class);

    /**
     * SQL-запрос на выборку всех студентов
     */
    private static final String SELECT_ALL_PERSONS_SQL_TEMPLATE =
            "select * from person";

    /**
     * SQL-запрос на вставку студента с возвратом новоиспечённого id
     */
    private static final String INSERT_PERSON_SQL_TEMPLATE =
            "insert into person (name, birth_date) values (?, ?) returning person_id";

    /**
     * SQL-запрос на удаление студента
     */
    private static final String DELETE_PERSON_SQL_TEMPLATE =
            "delete from person where person_id = ?";

    /**
     * SQL-запрос на редактирование студента с возвратом количества отредактированных
     */
    private static final String UPDATE_PERSON_SQL_TEMPLATE =
            "with rows as (update person set name = ?, birth_date = ? where person_id = ? returning 1) "
                    + "select count(*) from rows";

    /**
     * SQL-запрос на выборку студента по имени
     */
    private static final String SELECT_PERSON_BY_NAME_SQL_TEMPLATE =
            "select * from person where name = ?";

    /**
     * объект-подключение к БД
     */
    private final Connection connection;

    /**
     * Конструктор
     *
     * @param connection объект-подключение к БД
     */
    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Person> getAllPersons() throws SQLException {
        LOGGER.info("Попытка получения списка всех студентов");

        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_ALL_PERSONS_SQL_TEMPLATE)) {
            List<Person> allPersons = StatementToList.statementToPersonList(statement);
            LOGGER.info("Список всех студентов получен");
            return allPersons;
        } catch (SQLException e) {
            LOGGER.error("Список всех студентов не был получен");
            throw e;
        }
    }

    @Override
    public void addPerson(Person person) throws SQLException {
        LOGGER.info("Попытка добавления студента: имя - {}, дата рождения - {}",
                person.getName(), new Date(person.getBirthDate()));

        try (PreparedStatement statement =
                     connection.prepareStatement(INSERT_PERSON_SQL_TEMPLATE)) {
            statement.setString(1, person.getName());
            statement.setDate(2, new java.sql.Date(person.getBirthDate()));

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    int id = res.getInt("person_id");
                    // устанавливаем объекту полученный в таблице id
                    person.setId(id);

                    LOGGER.info("Студент успешно добавлен, person_id = {}", id);
                } else {
                    LOGGER.error("SQL-запрос не вернул person_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Студент не добавлен");
            throw e;
        }
    }

    @Override
    public void updatePerson(Person person) throws SQLException {
        LOGGER.info("Попытка редактирования студента: id - {}, имя - {}, дата рождения - {}",
                person.getId(), person.getName(), new Date(person.getBirthDate()));

        try (PreparedStatement statement =
                     connection.prepareStatement(UPDATE_PERSON_SQL_TEMPLATE)) {
            statement.setString(1, person.getName());
            statement.setDate(2, new java.sql.Date(person.getBirthDate()));
            statement.setInt(3, person.getId());

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    if (res.getInt("count") != 0) {
                        LOGGER.info("Студент успешно отредактирован");
                        return;
                    } else {
                        LOGGER.error("Студент с таким id не найден");
                        throw new SQLException("Редактирование несуществующего студента!");
                    }
                } else {
                    LOGGER.error("SQL-запрос не вернул count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Студент не отредактирован");
            throw e;
        }
    }

    @Override
    public void deletePerson(Person person) throws SQLException {
        LOGGER.info("Попытка удаления студента: id - {}, имя - {}, дата рождения - {}",
                person.getId(), person.getName(), new Date(person.getBirthDate()));

        try (PreparedStatement statement =
                     connection.prepareStatement(DELETE_PERSON_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            statement.execute();

            LOGGER.info("Студент успешно удалён");
        } catch (SQLException e) {
            LOGGER.error("Студент не удалён");
            throw e;
        }
    }

    @Override
    public Collection<Person> getPersonByName(String name) throws SQLException {
        LOGGER.info("Попытка получения студента(-ов) по имени: {}", name);

        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_PERSON_BY_NAME_SQL_TEMPLATE)) {
            statement.setString(1, name);

            LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

            List<Person> personList = StatementToList.statementToPersonList(statement);

            LOGGER.info("Студент(-ы) получен(-ы)");
            return personList;
        } catch (SQLException e) {
            LOGGER.error("Студент(-ы) не получен(-ы)");
            throw e;
        }
    }
}
