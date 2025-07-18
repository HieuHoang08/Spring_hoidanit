<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
        <!DOCTYPE html>
        <html lang="en">
            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Hoi dan IT</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>
            <body class="sb-nav-fixed">
            <jsp:include page = "../layout/header.jsp" />
                <div id="layoutSidenav">
                    <div id="layoutSidenav_nav">
                        <jsp:include page = "../layout/sidebar.jsp" />
                    </div>
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manager Users</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Users</li>
                                    <li class="breadcrumb-item active">Update</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Update a user</h3>
                                            <hr/>
                                        <form:form method="post" action="/admin/user/update" modelAttribute="newUser">
                                        <div class="mb-3" style="display: none;">
                                            <label class="form-label">Id:</label>
                                            <form:input type="text" class="form-control form-control-lg" path="id" />
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Email:</label>
                                            <form:input type="email" class="form-control form-control-lg" path="email" disabled = "true"/>
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
                                        <button type="submit" class="btn btn-warning btn-lg">Update</button>
                                        </form:form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                            </div>
                        </main>
                            <jsp:include page = "../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>
        </html>
