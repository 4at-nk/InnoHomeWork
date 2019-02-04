package ru.inno.lec12HomeWork.dao.StatementToList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.lec12HomeWork.entity.Person;
import ru.inno.lec12HomeWork.entity.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * класс для преобразования массивов сущностей, полученных в
 * результате выполнения запроса, в коллекцию, описывающих их объектов
 */
public class StatementToList {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(StatementToList.class);

    /**
     * преобразует массив сущностей "студент" в коллекцию объектов Person
     *
     * @param statement готовый к выполнению запрос на получение массива сущностей "студент"
     * @return коллекция объектов Person
     */
    public static List<Person> statementToPersonList(PreparedStatement statement) throws SQLException {
        LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

        try (ResultSet res = statement.executeQuery()) {
            List<Person> resultList = new ArrayList<>();
            while (res.next()) {
                Person person = new Person();
                person.setId(res.getInt("person_id"));
                person.setName(res.getString("name"));
                person.setBirthDate(res.getDate("birth_date").getTime());
                resultList.add(person);
            }
            return resultList;
        }
    }

    /**
     * преобразует массив сущностей "предмет" в коллекцию объектов Subject
     *
     * @param statement готовый к выполнению запрос на получение массива сущностей "предмет"
     * @return коллекция объектов Subject
     */
    public static List<Subject> statementToSubjectList(PreparedStatement statement) throws SQLException {
        LOGGER.info("Попытка выполнить SQL-запрос: {}", statement);

        try (ResultSet res = statement.executeQuery()) {
            List<Subject> resultList = new ArrayList<>();
            while (res.next()) {
                Subject subject = new Subject();
                subject.setId(res.getInt("subject_id"));
                subject.setDescription(res.getString("description"));
                resultList.add(subject);
            }
            return resultList;
        }
    }
}
