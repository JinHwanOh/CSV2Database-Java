package csv;
/**
 * 
 * Programmed By: Jin Hwan Oh
    Date: 12 August 2015
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jin Hwan Oh Student Id: ohjinh
 */
public class JdbcHelper {

    // To store the connection
    private Connection connection;

    // To store the last used PreparedStatement for re-use
    private PreparedStatement activeStatement;

    // Stores the last sql statement to create the PreparedStatement
    private String activeSql = "";

    // Store a reference to the last used ResultSet
    private ResultSet resultSet;

    private String errorMessage = "";

    /**
     * Establish connection with given parameters
     *
     * @param url (String) url address
     * @param user (String) user id
     * @param pass (String) user password
     * @return boolean value of connection status
     */
    public boolean connect(String url, String user, String pass) {
        errorMessage = "";
        try {
            // Tomcate requres Class.forName() to load driver properly
            Class.forName("com.mysql.jdbc.Driver");
            // Connect to the url
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            errorMessage = ex.getSQLState() + ": " + ex.getMessage();
            System.err.println(ex.getMessage());
            return false;
        } catch (ClassNotFoundException ex) {
            errorMessage = ex.getMessage();
            System.err.println(errorMessage);
            return false;
        }
        return true;
    }

    /**
     * Disconnect PreparedStatement, ResultSet, and Connection
     */
    public void disconnect() {
        activeSql = "";
        try {
            resultSet.close();
        } catch (Exception ignore) {
        }
        try {
            activeStatement.close();
        } catch (Exception ignore) {
        }
        try {
            connection.close();
        } catch (Exception ignore) {
        }
    }

    /**
     * Find first, last name of students from the given course (parameter) from
     * the database "HotSummer" Parameter course must be one of the values from
     * Course
     *
     * @param sql SQL statement
     * @param params ArrayLists which contains all the parameters for SQL
     * statement
     * @return ResultSet if found, otherwise returns null. If connection is not
     * established, returns null
     */
    public ResultSet query(String sql, ArrayList<Object> params) {
        try {
            // clear error message
            errorMessage = "";

            // if sql statement changed, need to create new PrepardStatement
            if (!sql.equals(activeSql)) {
                activeStatement = connection.prepareStatement(sql);
                activeSql = sql;
            }

            // set all parameter values of prepard statemnet
            if (params != null) {
                setParameters(params);
            }

            // execute the prepared statement
            resultSet = activeStatement.executeQuery();
        } catch (SQLException ex) {
            errorMessage = ex.getSQLState() + ": " + ex.getMessage();
            System.err.println(errorMessage);
            return null;
        }
        return resultSet;
    }

    public int update(String sql, ArrayList<Object> params) {
        int result = -1;
        try {
            errorMessage = "";

            // if sql statement changed, need to create new prepared statement
            if (!sql.equals(activeSql)) {
                activeStatement = connection.prepareStatement(sql);
                activeSql = sql;
            }
            
            // set all parameter values of prepard statemnet
            if (params != null) {
                setParameters(params);
            }

            // execute the prepared statement
            result = activeStatement.executeUpdate();
        } catch (SQLException ex) {
            errorMessage = ex.getSQLState() + ": " + ex.getMessage();
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
            System.err.println(errorMessage);
        }
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set the parameters of the prepared statement. It will cast each param to
     * the proper data type before calling setXXX().
     *
     * @param params An ArrayList of Objects to use as parameters. Note, you
     * must use the wrapper classes for primitive types.
     *
     * @throws SQLException
     */
    private void setParameters(ArrayList<Object> params)
            throws SQLException {
        for (int i = 0; i < params.size(); ++i) {
            Object param = params.get(i);
            if (param instanceof Integer) {
                activeStatement.setInt(i + 1, (int) param);
            }
            else if (param instanceof Double) {
                activeStatement.setDouble(i + 1, (double) param);
            }
            else if (param instanceof String) {
                activeStatement.setString(i + 1, (String) param);
            }
        }
    }
}
