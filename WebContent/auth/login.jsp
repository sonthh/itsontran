<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Login</title>
    
    <!-- Favicon-->
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/template/public/img/favicon.ico">

    <!-- Bootstrap core CSS-->
    <link href="<%=request.getContextPath()%>/template/admin/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="<%=request.getContextPath()%>/template/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template-->
    <link href="<%=request.getContextPath()%>/template/admin/css/sb-admin.css" rel="stylesheet">

  </head>

  <body class="bg-dark">

    <div class="container">
      <div class="card card-login mx-auto mt-5">
        <div class="card-header">Login</div>
        <div class="card-body">
          <form action="" method="post">
          	<%	
          		/* msg from doPost AuthLoginController when user login incorrect */
          		String msg = request.getParameter("msg");
    			if ("0".equals(msg)) {
          	%>
            <div class="form-group alert alert-danger">
            	<i class="fas fa-times"></i>
              	The username or password is incorrect.
            </div>
            <%
    			}
            %>
            <div class="form-group">
              <div class="form-label-group">
                <input type="text" id="inputEmail" name="username" class="form-control" placeholder="Username"
                	maxlength="32" pattern=".{5,32}" title="Username from 5 to 32 letter" required="required" autofocus="autofocus">
                <label for="inputEmail">Username</label>
              </div>
            </div>
            <div class="form-group">
              <div class="form-label-group">
                <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password"
                	maxlength="32" pattern=".{5,32}" title="Password from 5 to 32 letter" required="required">
                <label for="inputPassword">Password</label>
              </div>
            </div>
            <div class="form-group">
              <div class="checkbox">
                <label>
                  <input name="remember-me" type="checkbox" value="remember-me">
                  Remember Password
                </label>
              </div>
            </div>
            <div class="form-group">
              <button type="submit" class="btn btn-primary btn-block">Login</button>
            </div>
          </form>
          <div class="text-center">
            <a class="d-block small mt-3" href="<%=request.getContextPath()%>/register">Register an Account</a>
            <a class="d-block small" href="<%=request.getContextPath()%>/forgot-password">Forgot Password?</a>
          </div>
        </div>
      </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="<%=request.getContextPath()%>/template/admin/vendor/jquery/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/template/admin/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="<%=request.getContextPath()%>/template/admin/vendor/jquery-easing/jquery.easing.min.js"></script>

  </body>

</html>
