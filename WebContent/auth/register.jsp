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

    <title>SB Admin - Register</title>
    
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
      <div class="card card-register mx-auto mt-5">
        <div class="card-header">Register an Account</div>
        <div class="card-body">
          <form action="" method="post">
          	<div id="message"></div>
          	<%
          		String msg = request.getParameter("msg");
    			if (msg != null) {
          	%>
            <div class="form-group">
            	<%
            		if ("1".equals(msg))
            			out.print("<div class='alert alert-success message'>Register succesful. Please check your email.</div>");
            		else if ("3".equals(msg)) {
            			out.print("<div class='alert alert-warning message'>Email not existed.</div>");
            		}
            		else if ("0".equals(msg)) {
            			out.print("<div class='alert alert-danger message'>Have a error.</div>");
            		}
            		else if ("2".equals(msg)) {
            			out.print("<div class='alert alert-warning message'><i class='fas fa-times'></i>  Username is existed.");
            		}
            	%>
            </div>
            <%
    			}
            %>
            <div class="form-group">
              <div class="form-label-group">
                <input type="text" name="username" id="inputUsername" class="form-control" placeholder="Username" required="required" onchange="checkUsernameExist()" /> <!-- ajax username exist -->
                <label for="inputUsername">Username</label>
              </div>
            </div>
            <div class="form-group">
              <div class="form-row">
                <div class="col-md-6">
                  <div class="form-label-group">
                    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required="required">
                    <label for="inputPassword">Password</label>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-label-group">
                    <input type="password" id="confirmPassword" class="form-control" placeholder="Confirm password" required="required">
                    <label for="confirmPassword">Confirm password</label>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-group">
              <div class="form-label-group">
                <input type="text" name="fullname" id="inputFullName" class="form-control" placeholder="Fullname" required="required">
                <label for="inputFullName">Fullname</label>
              </div>
            </div>
            <div class="form-group">
              <div class="form-label-group">
                <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email address" required="required">
                <label for="inputEmail">Email address</label>
              </div>
            </div>
            <div class="form-group">
            	<button type="submit" class="btn btn-primary btn-block">Register</button>
            </div>
          </form>
          <div class="text-center">
            <a class="d-block small mt-3" href="<%=request.getContextPath()%>/login">Login Page</a>
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
	<script>
	/* confirm password */
	var password = document.getElementById("inputPassword"), 
	    confirm_password = document.getElementById("confirmPassword");
	
	function validatePassword(){
		//console.log(password.value + "-" + confirm_password.value);	
		if (password.value != confirm_password.value) {
		  confirm_password.setCustomValidity("Passwords Don't Match");
		  confirm_password.reportValidity();
		} else {
		  confirm_password.setCustomValidity('');
		}
	}
	password.onchange = validatePassword;
	confirm_password.onkeyup = validatePassword;
	
	
	/* ajax username exist when onchange input username in register form */
	function checkUsernameExist() {
		var username = $('#inputUsername').val();
		console.log(username);
		$.ajax({
			type: "post",
			/* doPost AdminCheckExistUserController */
		    url: "/shareit/admin/user/checkusername",
		    data: {
		    	ausername: username
		    },
		    dataType: "html",
		    success: function (response) {
		    	console.log(response);
		    	/* response from doPost AdminCheckExistUserController | 1->exist username 0->not exist user*/
		    	if (response == 1) {
		    		$('#message').html("<div class='alert alert-warning message'><i class='fas fa-times'></i>  Username is existed.</div>");
		    	} else {
		    		$('#message').html('');
		    		return false;
		    	}
		    },
		    error: function() {
		    	console.log('lỗi check exist user');
		    }
		});
	};
	</script>
  </body>

</html>
