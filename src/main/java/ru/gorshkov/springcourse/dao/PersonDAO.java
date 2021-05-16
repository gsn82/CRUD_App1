package ru.gorshkov.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.gorshkov.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "postgrestoor";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }

    public Person show(int id) {
        //вначале человека делаем null
        Person person = null;

        try {
            //созвдем sql запрос
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM Person WHERE id=?");
            // указываем id
            preparedStatement.setInt(1, id);
             // выполняем запрос к базе
            ResultSet resultSet = preparedStatement.executeQuery();
            // смешаем указатель
            resultSet.next();
            person = new Person();
            // получаем поля у челвоека
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return person;
    }

    public void save(Person person) {
        try {
            //созвдем sql запрос
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Person VALUES(1, ?, ?, ?)");
            // указываем поля
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            // обновляем данные (дабавляем запись )
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void update(int id, Person updatedPerson) {

        try {
            //созвдем sql запрос
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Person Set name=?, age=?, email=? WHERE id=?");
            // указываем поля
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);
            // обновляем данные(  обновляем запись)
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void delete(int id) {

        try {
            //созвдем sql запрос
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM Person WHERE id=?");
            // указываем поля
            preparedStatement.setInt(1, id);
            // обновляем данные (в нашем случае удяляем запись )
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
