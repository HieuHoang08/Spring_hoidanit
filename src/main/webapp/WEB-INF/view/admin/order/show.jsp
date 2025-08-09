<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                                <h1 class="mt-4">Manager Orders</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Orders</li>
                                </ol>
                                <div class="mt-5">
                                        <div class="row">
                                            <div class="col-12 mx-auto">
                                                <div class="d-flex justify-content-between align-items-center mb-3">
                                                    <h3>Table Orders</h3>
                                                </div>
                                                <hr />
                                                <table class = "table table-bordered table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">ID</th>
                                                            <th scope="col">Tổng giá</th>
                                                            <th scope="col">Người dùng</th>
                                                            <th scope="col">Tình trạng </th>
                                                            <th scope="col">Hành động</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="order" items="${order}">
                                                            <tr>
                                                                <th>${order.id}</th>
                                                                <td>
                                                                    <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true" maxFractionDigits="0" /> đ
                                                                </td>

                                                                <td>${order.user.fullname}</td>
                                                                <td>${order.status}</td>
                                                                <td>
                                                                    <a href="/admin/order/${order.id}" class="btn btn-success">  View  </a>
                                                                    <a href="/admin/order/update/${order.id}" class="btn btn-primary mx-2">  Update  </a>
                                                                    <a href="/admin/order/delete/${order.id}" class="btn btn-danger mx-2">  Delete  </a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
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
