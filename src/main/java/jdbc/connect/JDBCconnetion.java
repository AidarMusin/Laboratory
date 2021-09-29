package jdbc.connect;

import ru.ufagkb21.Person;
import ru.ufagkb21.Study;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public List<Person> findPeople (List<String> findPerson, String dateNew) throws ClassNotFoundException {
        List<Person> persons = new ArrayList<Person>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        int k = 0;
        for (String fullNamePerson : findPerson) {

            try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password)) {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM savepeople WHERE full_name = '" + fullNamePerson + "';");

                while (rs.next()) {

                    int countPeople = 1 + k;
                    Integer numbRep = rs.getInt("number_report");
                    Integer numbProd = rs.getInt("number_production");
                    String[] fullName = rs.getString("full_name").split(" ");
                    String[] passp = rs.getString("passport").split(" ");
                    persons.add( new Person(countPeople, fullName[0], fullName[1],rs.getString("date_birth"),(passp[0] + passp[1]),dateNew,numbRep,numbProd));
                    k ++;
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return persons;
    }

    public void deleteRow (ArrayList<Integer> arrayNumberRow) throws ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {
            for (Integer numberRow : arrayNumberRow) {
                statement.executeUpdate("DELETE FROM savepeople WHERE id=" +  numberRow + ";");
                System.out.println("Удалена строка с номером: " + numberRow);
            }
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
