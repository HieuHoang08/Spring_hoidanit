<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                                    <li class="breadcrumb-item active">Delete</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                                <div class="d-flex justify-content-between align-items-center mb-3">
                                                        <h3>Delete the user with id ${id}</h3>
                                                </div>
                                                <hr/>
                                                <div class = "alert alert-danger"> Are you sure to delete this user ? </div>
                                                <form:form method="post" action="/admin/user/delete/{id}" modelAttribute="newUser">
                                                    <div class="mb-3" >
                                                        <label class="form-label">Id:</label>
                                                        <form:input value = "${id}" type="hidden" class="form-control form-control-lg" path="id" />
                                                    </div>
                                                    <button class  ="btn btn-danger" type="submit" >Delete</button>
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
