package ru.inno.lec12HomeWork.dao;

import ru.inno.lec12HomeWork.dao.StatementToList.StatementToList;
import ru.inno.lec12HomeWork.entity.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * DAO-класс для работы с сущностью "студент"
 */
public class PersonDAOImpl implements PersonDAO {

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
        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_ALL_PERSONS_SQL_TEMPLATE)) {
            return StatementToList.statementToPersonList(statement);
        }
    }

    @Override
    public void addPerson(Person person) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(INSERT_PERSON_SQL_TEMPLATE)) {
            statement.setString(1, person.getName());
            statement.setDate(2, new java.sql.Date(person.getBirthDate()));

            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    int id = res.getInt("person_id");
                    // устанавливаем объекту полученный в таблице id
                    person.setId(id);
                }
            }
        }
    }

    @Override
    public void updatePerson(Person person) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(UPDATE_PERSON_SQL_TEMPLATE)) {
            statement.setString(1, person.getName());
            statement.setDate(2, new java.sql.Date(person.getBirthDate()));
            statement.setInt(3, person.getId());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                if (res.getInt("count") > 0) {
                    return;
                }
            }
            // если неотредактирован ни один студент
            throw new SQLException("Редактирование несуществующего студента!");
        }
    }

    @Override
    public void deletePerson(Person person) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(DELETE_PERSON_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());
            statement.execute();
        }
    }

    @Override
    public Collection<Person> getPersonByName(String name) throws SQLException {
        try (PreparedStatement statement =
                connection.prepareStatement(SELECT_PERSON_BY_NAME_SQL_TEMPLATE)) {
            statement.setString(1, name);
            return StatementToList.statementToPersonList(statement);
        }
    }
}
