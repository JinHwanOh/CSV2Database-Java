/*
 Programmed By: Jin Hwan OH
 Date: 12 August 2015
 */
package csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jin Oh
 */
public class Csv2Database {

    private ArrayList<Person> parsedPersons;
    private ArrayList<Person> addedPersons;
    private String message;

    final String url = "jdbc:mysql://localhost/ejd";
    final String user = "root";
    final String pass = "PROG32758";
    private JdbcHelper helper;

    // Database information
    public Csv2Database() {
        parsedPersons = new ArrayList<>();
        addedPersons = new ArrayList<>();
        helper = new JdbcHelper();
    }

    // read csv file, and returns parsedPerson list.
    public ArrayList<Person> readCsv(InputStream is) {
        String[] output = new String[11];
        ArrayList<Person> parsedPersonsOutput = new ArrayList<>();
        Person person;
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            ArrayList<String> lines = new ArrayList<>();
            // parse each line and store to an array
            String line;
            try {
                //skip header
                reader.readLine();
                while ((line = reader.readLine()) != null) {

                    person = stringToPerson(line);
                    parsedPersonsOutput.add(person);
                }
                // close BufferedReader
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Csv2Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.parsedPersons = parsedPersonsOutput;

        return parsedPersonsOutput;
    }

    public Person stringToPerson(String line) {
        Person person;
        String[] parts = new String[11];
        int j = 0; // index for parts[j]
        int numDoubleQuote = 0;
        int startingIndex = 0;
        int endingIndex = 0;

        for (int i = 0; i < line.length(); i++) {

            boolean found = false;
            // Count number of double quote(s)
            if (line.charAt(i) == '"') {
                // Get the starting index of the string value
                if (numDoubleQuote == 0) {
                    startingIndex = i + 1;
                }

                else if (numDoubleQuote == 1) { // last index of the string
                    endingIndex = i;
                    found = true; // once it ending index is set, set duplicateFound = true
                }

                // Reset number of double quote every 2nd duplicateFound
                if (numDoubleQuote == 1) {
                    numDoubleQuote = 0;
                }
                else {
                    numDoubleQuote = 1;
                }
            }
            // if found, substring from starting index to ending index
            if (found) {
                parts[j++] = line.substring(startingIndex, endingIndex);
            }
        }

        // Create new person with this data
        person = new Person(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9], parts[10]);

        return person;
    }

    // Add the given record set to database.
    public ArrayList<Person> addPersons(ArrayList<Person> persons) {
        // Add to database

        // Establish connection
        boolean connected = helper.connect(url, user, pass);
        if (!connected) {
            message += "Not comneected";
        }

        addedPersons = getPersonsFromDatabase();
        boolean duplicateFound;
        for (int i = 0; i < persons.size(); i++) {
            duplicateFound = false; // set initial duplicateFound = false
            Person person = persons.get(i); // get each line of person
            for (int j = 0; j < addedPersons.size(); j++) {
                if (addedPersons.get(j).equals(person)) {
                    // if person is exist, break
                    duplicateFound = true;
                    break;
                }
            }
            if (!duplicateFound) {
                // if no duplicate person, add it to addedPersons
                addedPersons.add(person);

                // Get attribute of this person
                String firstName = person.getFirstName();
                String lastName = person.getLastName();
                String companyName = person.getCompanyName();
                String address = person.getAddress();
                String city = person.getCity();
                String province = person.getProvince();
                String postal = person.getPostal();
                String phone1 = person.getPhone1();
                String phone2 = person.getPhone2();
                String email = person.getEmail();
                String web = person.getWeb();

                // create ArrayList<Object> which stores the parameters for person's attribute
                ArrayList<Object> personAttribute = new ArrayList<>();
                personAttribute.add(firstName);
                personAttribute.add(lastName);
                personAttribute.add(companyName);
                personAttribute.add(address);
                personAttribute.add(city);
                personAttribute.add(province);
                personAttribute.add(postal);
                personAttribute.add(phone1);
                personAttribute.add(phone2);
                personAttribute.add(email);
                personAttribute.add(web);

                // create a sql statement
                String sql = "INSERT INTO PERSON(firstName, lastName, companyName, address, city, province, postal, phone1, phone2, email, web)"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // call update
                int update = helper.update(sql, personAttribute);
                message += update + "";
            }
        }
        // disconnect JDBC
        helper.disconnect();
        return addedPersons;
    }

    // Retrieve the existing Person records from database
    public ArrayList<Person> getPersonsFromDatabase() {
        boolean connected = helper.connect(url, user, pass);
        if (!connected) {
            message += "Connection not established";
        }
        String sql = "SELECT * FROM PERSON";
        ResultSet resultSet = helper.query(sql, null);
        ArrayList<Person> persons = new ArrayList<>();
        try {
            // Curser to next resultSet
            while (resultSet.next()) {
                // Get attributes of duplicateFound user
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String companyName = resultSet.getString("companyName");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String province = resultSet.getString("province");
                String postal = resultSet.getString("postal");
                String phone1 = resultSet.getString("phone1");
                String phone2 = resultSet.getString("phone2");
                String email = resultSet.getString("email");
                String web = resultSet.getString("web");

                // Create Person with the attributes
                Person person = new Person(firstName, lastName, companyName, address, city, province, postal, phone1, phone2, email, web);

                // Add person to the arrayList
                persons.add(person);
            }
        } catch (SQLException ex) {
            System.out.println(helper.getErrorMessage());
        }
        // disconnect JDBC
        helper.disconnect();
        return persons;
    }

    // Check if this person exist in database
    public boolean isPersonExist(String firstName, String lastName) {
        boolean connected = helper.connect(url, user, pass);
        if (!connected) {
            message += "Connection not established";
        }
        String sql = "Select count(*) from PERSON where firstName=? and lastName=?";
        ArrayList<Object> names = new ArrayList<>();
        // add names to arraylist
        names.add(firstName);
        names.add(lastName);

        // ResultSet
        ResultSet rs = helper.query(sql, names);
        try {
            rs.next();
            // registered
            if (rs.getInt(1) >= 1) { // get counted number
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Csv2Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        // disconnect JDBC
        helper.disconnect();
        return false;
    }

    // Returns message that stored in message variable
    public String getMessage() {
        return this.message;
    }
}
