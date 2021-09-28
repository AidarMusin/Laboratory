package jdbc.connect;

import ru.ufagkb21.Person;

import java.sql.*;
import java.util.logging.Logger;

import static jdbc.connect.JdbcSetings.*;

public class JDBCconnetion {

    private static final Logger LOGGER = Logger.getLogger( JDBCconnetion.class.getName() );

//            ResultSet resultSet = statement.executeQuery("SELECT * FROM Mh_users");
//            while (resultSet.next()) {

    public void appendPeople (Person person, String tableName) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {
            String request = "INSERT INTO "+ tableName + "(full_name, date_birth, passport, date_result, number_report, number_production) VALUES ('"
                    + person.getPatient() + "','" + person.getOnlyDateOfBirth() + "','"
                    + person.getOnlyPasNumber() + "','" + person.getOnlyDateResult() + "','"
                    + person.getOnlyReportNumber() + "','" + person.getOnlyProdNumber() + "');";

            statement.executeUpdate(request);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTable (String nameTable) throws ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE "+ nameTable + " (" +
                    "id MEDIUMINT NOT NULL AUTO_INCREMENT," +
                    "full_name VARCHAR(50) NOT NULL," +
                    "date_birth VARCHAR(10) NOT NULL," +
                    "passport VARCHAR(10) NOT NULL," +
                    "date_result VARCHAR(16) NOT NULL," +
                    "number_report VARCHAR(4) NOT NULL," +
                    "number_production VARCHAR(1) NOT NULL," +
                    "constraint " + nameTable + "_pk PRIMARY KEY (id));");

            System.out.println("Создана база данных с именем " + nameTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
