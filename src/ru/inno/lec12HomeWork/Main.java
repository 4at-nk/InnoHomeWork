/*
 * Фоат Шарафутдинов
 * Домашняя работа двенадцатого и тринадцатого занятия
 */
package ru.inno.lec12HomeWork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.lec12HomeWork.dao.*;
import ru.inno.lec12HomeWork.entity.Person;
import ru.inno.lec12HomeWork.entity.Subject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Класс демонстрирующий работу DAO-слоя
 * <p>
 * **************************************
 * Скрипт для создания таблицы person:
 * <p>
 * create table person
 * (
 * person_id serial not null
 * constraint person_pk
 * primary key,
 * name varchar(20) not null,
 * birth_date timestamp not null
 * );
 * <p>
 * alter table person owner to postgres;
 * <p>
 * *************************************
 * Скрипт для создания таблицы subject:
 * <p>
 * create table subject
 * (
 * subject_id serial not null
 * constraint subject_pk
 * primary key,
 * description varchar(40) not null
 * );
 * <p>
 * alter table subject owner to postgres;
 * <p>
 * *************************************
 * Скрипт для создания таблицы course:
 * <p>
 * create table course
 * (
 * person_id integer not null
 * constraint course_person_person_id_fk
 * references person
 * on update restrict on delete restrict,
 * subject_id integer not null
 * constraint course_subject_subject_id_fk
 * references subject
 * on update restrict on delete restrict,
 * constraint course_pk
 * primary key (person_id, subject_id)
 * );
 * <p>
 * alter table course owner to postgres;
 */
public class Main {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Main.class);

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "password";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);

        LOGGER.info("Попытка подключения к БД: driver - {}, url - {}, login - {}, password - {}",
                DRIVER, URL, LOGIN, PASSWORD);

        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

            LOGGER.info("Успешное подключеник к БД");

            String[] persons = {
                    "John", "Max", "Kate", "Jane", "Tom", "Hank", "Homer", "Stephen",
                    "Maria", "Nathan", "Jacob", "Dory", "Barny", "Poul", "Ethan", "Jerry", "Gloria",
                    "Alex", "Peter", "Emma", "Jim", "Leon"
            };

            PersonDAO personDAO = new PersonDAOImpl(connection);
            for (String name : persons) {
                Person person = new Person();
                person.setName(name);
                person.setBirthDate(System.currentTimeMillis());
                personDAO.addPerson(person);
            }

            System.out.println("Все студенты: ");
            Collection<Person> allPersons = personDAO.getAllPersons();
            for (Person person : allPersons) {
                System.out.println(person);
            }

            System.out.println();
            System.out.println("Студент Homer:");
            List<Person> homers = (List<Person>) personDAO.getPersonByName("Homer");
            Person homer = homers.get(0);
            System.out.println(homer);

            //*************

            String[] subjects = {
                    "Biology", "Geography", "Math", "Chemistry", "Physics", "Programming",
                    "Geometry", "History", "Art", "Astronomy", "English", "Java EE"
            };

            SubjectDAO subjectDAO = new SubjectDAOImpl(connection);
            for (String descriptions : subjects) {
                Subject subject = new Subject();
                subject.setDescription(descriptions);
                subjectDAO.addSubject(subject);
            }

            System.out.println();
            System.out.println("Все предметы: ");
            Collection<Subject> allSubjects = subjectDAO.getAllSubjects();
            for (Subject subject : allSubjects) {
                System.out.println(subject);
            }

            System.out.println();
            System.out.println("Предмет Art: ");
            List<Subject> arts = (List<Subject>) subjectDAO.getSubjectByName("Art");
            Subject art = arts.get(0);
            System.out.println(art);

            //**********************

            CourseDAO courseDAO = new CourseDAOImpl(connection);

            showPersonSubjects(courseDAO, homer);
            System.out.println();
            System.out.println("Записываем Homer на Art...");
            courseDAO.linkPersonToSubjects(homer, art);
            showPersonSubjects(courseDAO, homer);
            System.out.println();
            System.out.println("Записываем Homer на все предметы...");
            courseDAO.linkPersonToSubjects(homer, allSubjects.toArray(new Subject[0]));
            showPersonSubjects(courseDAO, homer);
            System.out.println();
            System.out.println("Отписываем Homer от Art");
            courseDAO.unlinkPersonFromSubjects(homer, art);
            showPersonSubjects(courseDAO, homer);

            System.out.println();
            System.out.println("Записываем на Art студента Homer...");
            courseDAO.linkSubjectToPersons(art, homer);
            showSubjectPersons(courseDAO, art);
            System.out.println();
            System.out.println("Записываем на Art всех студентов...");
            courseDAO.linkSubjectToPersons(art, allPersons.toArray(new Person[0]));
            showSubjectPersons(courseDAO, art);
            System.out.println();
            System.out.println("Отписываем от Art студента Homer");
            courseDAO.unlinkSubjectFromPersons(art, homer);
            showSubjectPersons(courseDAO, art);
        } catch (SQLException e) {
            LOGGER.error("Не удалось подключиться к БД");
            throw e;
        }
    }

    /**
     * выводит на консоль все предметы студента
     *
     * @param courseDAO DAO-объект для работы с таблицей course
     * @param person    студент, чьи предметы отобразятся
     */
    private static void showPersonSubjects(CourseDAO courseDAO, Person person) throws SQLException {
        List<Subject> subjectsList = (List<Subject>) courseDAO.getSubjectsByPerson(person);
        System.out.println();
        System.out.println("Предметы студента " + person.getName() + ": ");
        for (Subject subject : subjectsList) {
            System.out.println(subject);
        }
    }

    /**
     * выводит на консоль всех студентов предмета
     *
     * @param courseDAO DAO-объект для работы с таблицей course
     * @param subject   предмет, чьи студенты отобразятся
     */
    private static void showSubjectPersons(CourseDAO courseDAO, Subject subject) throws SQLException {
        List<Person> personList = (List<Person>) courseDAO.getPersonsBySubject(subject);
        System.out.println();
        System.out.println("Студенты на предмете " + subject.getDescription() + ": ");
        for (Person person : personList) {
            System.out.println(person);
        }
    }
}
