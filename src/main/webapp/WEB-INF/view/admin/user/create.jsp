<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create User</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
    <body>
        <div class="container mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3>Create a user</h3>
                            <hr />
                    <form:form method="post" action="/admin/user/create" modelAttribute="newUser">
                        <div class="mb-3">
                            <label class="form-label">Email:</label>
                            <form:input type="email" class="form-control form-control-lg" path="email" />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Password:</label>
                            <form:input type="password" class="form-control form-control-lg" path="password" />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Phone Number:</label>
                            <form:input type="text" class="form-control form-control-lg" path="phone" />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Full Name:</label>
                            <form:input type="text" class="form-control form-control-lg" path="fullname" />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Address:</label>
                            <form:input type="text" class="form-control form-control-lg" path="address" />
                        </div>
                        <button type="submit" class="btn btn-primary btn-lg">Create</button>
                    </form:form>
                </div>
            </div>
        </div>

    </body>
</html>
