///////////////////////////////////////////////////////////////////////////////
// FileUpload.java
// ===============
// Servlet for file upload example
// It reads a file line-by-line and store the lines into an ArrayList,
// then prints out the lines to client.
//
//  Programmed By: Jin Hwan Oh
//   Date: 12 August 2015
///////////////////////////////////////////////////////////////////////////////
package csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "FileUpload", urlPatterns = {"/FileUpload"})
@MultipartConfig
public class CsvUpload extends HttpServlet {

    ///////////////////////////////////////////////////////////////////////////
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        // get printwriter object
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // file handlers
        Part filePart = null;
        InputStream fileStream = null;
        BufferedReader reader = null;

        // container for records
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Person> parsedPersons = new ArrayList<>();
        ArrayList<Person> addedPersons = new ArrayList<>();
        ArrayList<Person> returnPersons = new ArrayList<>();
        Csv2Database csv = new Csv2Database();
        Person person = new Person();

        // get the file from request and create a file reader
        filePart = request.getPart("file");
        fileStream = filePart.getInputStream();
        parsedPersons = csv.readCsv(fileStream);
        addedPersons = csv.addPersons(parsedPersons);
        returnPersons = csv.getPersonsFromDatabase();

        int parsedSize = parsedPersons.size();
        int addedSize = addedPersons.size();
        
        // Set attributes in request scope
        request.setAttribute("parsedSize", parsedSize);
        request.setAttribute("addedSize", addedSize);
        request.setAttribute("addedPersons", returnPersons);
        
        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("csvResult.jsp");
        
        rd.forward(request, response);
        
        // done with file stream, clean up
        if (reader != null) {
            reader.close();
        }
        if (fileStream != null) {
            fileStream.close();
        }
        /*
        // print output
        printHeader(out, "CSV Reader", "");

        // print body
        out.println("<h5>CSV Upload Result</h5><br/>");
        out.println("<div>Parsed Record: " + (parsedPersons.size() - 1) + "</div><br/>");
        out.println("<div>Added Record: " + (addedPersons.size() - 1) + "</div><br/>");

        out.println("<table>");
        for (int i = 0; i < returnPersons.size(); i++) {
            out.println("<tr>");
            if (i == 0) {
                // table header
                out.println("<th>#</th>");
                out.println("<th>" + returnPersons.get(i).getFirstName() + "</th>");
                out.println("<th>" + returnPersons.get(i).getLastName() + "</th>");
                out.println("<th>" + returnPersons.get(i).getCompanyName() + "</th>");
                out.println("<th>" + returnPersons.get(i).getAddress() + "</th>");
                out.println("<th>" + returnPersons.get(i).getCity() + "</th>");
                out.println("<th>" + returnPersons.get(i).getProvince() + "</th>");
                out.println("<th>" + returnPersons.get(i).getPostal() + "</th>");
                out.println("<th>" + returnPersons.get(i).getPhone1() + "</th>");
                out.println("<th>" + returnPersons.get(i).getPhone2() + "</th>");
                out.println("<th>" + returnPersons.get(i).getEmail() + "</th>");
                out.println("<th>" + returnPersons.get(i).getWeb() + "</th>");

            }
            else {
                out.println("<td>" + returnPersons.get(i).getId() + "</td>");
                out.println("<td>" + returnPersons.get(i).getFirstName() + "</td>");
                out.println("<td>" + returnPersons.get(i).getLastName() + "</td>");
                out.println("<td>" + returnPersons.get(i).getCompanyName() + "</td>");
                out.println("<td>" + returnPersons.get(i).getAddress() + "</td>");
                out.println("<td>" + returnPersons.get(i).getCity() + "</td>");
                out.println("<td>" + returnPersons.get(i).getProvince() + "</td>");
                out.println("<td>" + returnPersons.get(i).getPostal() + "</td>");
                out.println("<td>" + returnPersons.get(i).getPhone1() + "</td>");
                out.println("<td>" + returnPersons.get(i).getPhone2() + "</td>");
                out.println("<td>" + returnPersons.get(i).getEmail() + "</td>");
                out.println("<td>" + returnPersons.get(i).getWeb() + "</td>");
            }
            out.println("/<tr>");
            //out.println("<div>" + persons.get(i).getFirstName() + " " + persons.get(i).getLastName() + "</div>");
        }
        out.println("</table>");
        out.println(csv.getMessage());
        printFooter(out);*/
    }

    ///////////////////////////////////////////////////////////////////////////
    // helper methods to print HTML content
    ///////////////////////////////////////////////////////////////////////////
    private void printHeader(PrintWriter out, String title, String css) {
        String header = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "<meta charset=\"utf-8\">\n"
                + "<title>"
                + title
                + "</title>\n"
                + css
                + "</head>\n"
                + "<body>\n";
        out.println(header);
    }

    private void printFooter(PrintWriter out) {
        out.println("\n</body>\n</html>");
    }

    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
