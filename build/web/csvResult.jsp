<%-- 
    Programmed By: Jin Hwan Oh
    Student Number: 991 381 235
    Date: 12 August 2015
--%>

<%@ page language="java" contentType="text/html" pageEncoding="utf-8"%>

<%-- define tag libs being used --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Csv2Database Result</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <h2>CSV Upload Result</h2>
        <div>Parsed Records: ${parsedSize}</div><br/>
        <div>Added Records: ${addedSize}</div><br/>
        <table>
            <tr>
                <th>#</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Company Name</th>
                <th>Address</th>
                <th>City</th>
                <th>Province</th>
                <th>Postal</th>
                <th>Phone1</th>
                <th>Phone2</th>
                <th>Email</th>
                <th>Web</th>
            </tr>
            <c:forEach var="person" items="${addedPersons}" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${person.firstName}</td>
                    <td>${person.lastName}</td>
                    <td>${person.companyName}</td>
                    <td>${person.address}</td>
                    <td>${person.city}</td>
                    <td>${person.province}</td>
                    <td>${person.postal}</td>
                    <td>${person.phone1}</td>
                    <td>${person.phone2}</td>
                    <td>${person.email}</td>
                    <td>${person.web}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>