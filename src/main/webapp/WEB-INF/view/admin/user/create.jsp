<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
                <style>
                    * {
                        padding: 0;
                        margin: 0;
                        box-sizing: border-box;
                    }

                    .container {
                        width: 600px;
                        margin-left: auto;
                        margin-right: auto;
                        padding-left: 20px;
                        padding-right: 20px;
                    }

                    .container h1 {
                        margin: 20px 0px;
                    }

                    .mb-3 {
                        margin: 15px 0px;
                    }

                    .form-label {
                        display: block;
                    }

                    .mb-3 input {
                        width: 100%;
                        height: 40px;
                        border: 1px solid gainsboro;
                        border-radius: 4px;
                        padding: 0px 10px;
                    }

                    .container button {
                        background-color: rgb(121, 121, 252);
                        width: 80px;
                        height: 40px;
                        border-radius: 6px;
                        border: none;
                        color: white;
                        font-size: 16px;
                    }
                </style>
            </head>

            <body>
                <div class="container">
                    <h1>Create a user</h1>
                    <hr>
                    <form:form method="post" action="/admin/user/create1" modelAttribute="newUser">
                        <div class="mb-3">
                            <label class="form-label">Email address:</label>
                            <br>
                            <form:input type="email" class="form-control" path="email" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Password:</label>
                            <br>
                            <form:input type="password" class="form-control" path="password" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Phone number:</label>
                            <br>
                            <form:input type="tel" class="form-control" path="phone" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Full name:</label>
                            <br>
                            <form:input type="text" class="form-control" path="fullName" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Address:</label>
                            <br>
                            <form:input type="text" class="form-control" path="address" />
                        </div>

                        <button type="submit" class="btn btn-primary">Create</button>
                    </form:form>
                </div>
            </body>

            </html>