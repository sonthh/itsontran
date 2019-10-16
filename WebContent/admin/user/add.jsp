<%@page import="model.dao.UserPermissionsDAO"%>
<%@page import="model.bean.UserPermissions"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/template/admin/inc/header-leftbar.jsp" %>

      <div id="content-wrapper">

        <div class="container-fluid">

          <!-- Breadcrumbs-->
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <a href="<%=request.getContextPath()%>/admin">Dashboard</a>
            </li>
            <li class="breadcrumb-item">
              <a href="<%=request.getContextPath()%>/admin/users">Users</a>
            </li>
            <li class="breadcrumb-item active">Add</li>
          </ol>

          <!-- DataTables Example -->
          <div class="card mb-3">
            <div class="card-header">
              <i class="fas fa-table"></i>
              Add A User
            </div>
            <div id="user-exist">
            <%
            	String msg = request.getParameter("msg");
            	if ("0".equals(msg)) {
            		out.print("<div class='alert alert-warning message'><i class='fas fa-times'></i>  Username is existed.</div>");
            	}
        		/* from doPost AdminAddUserController when username is existed */
        		String username = request.getParameter("username");
        		String password = request.getParameter("password");
        		String fullname = request.getParameter("fullname");
            		
            %>
            </div>
            <div class="card-body">
	          <form action="" method="post">
	            <div class="form-group">
	              <div class="form-label-group">
	                <input type="text" name="username" value="<%if(username != null) out.print(username);%>" id="inputUsername" class="form-control"
	                 	placeholder="Username" pattern=".{8,32}" title="Username from 8 to 32 letter" 
	                 	maxlength="32" required autofocus="autofocus" onchange="checkUsernameExist()" /> <!-- ajax username exist -->
	                <label for="inputUsername">Username</label>
	              </div>
	            </div>
	            <div class="form-group">
	              <div class="form-label-group">
	                <input type="password" name="password" value="<%if(password != null) out.print(password);%>" id="inputPassword" class="form-control"
	                	placeholder="Password" pattern=".{8,32}" title="Password from 8 to 32 letter" 
	                	maxlength="32" required/>
	                <label for="inputPassword">Password</label>
	              </div>
	            </div>
	            <div class="form-group">
	              <div class="form-label-group">
	                <input type="text" name="fullname" value="<%if(fullname != null) out.print(fullname);%>" id="inputFullname" class="form-control"
	                	placeholder="Fullname" pattern=".{8,32}" title="Fullname from 8 to 32 letter" 
	                 	maxlength="32" placeholder="Fullname" required="required" />
	                <label for="inputFullname">Fullname</label>
	              </div>
	            </div>
	            <div class="form-group">
				    <label for="user-permissions">User permissions</label>
				    <select name="user-permissions" class="form-control" id="user-permissions">
				    <%
				    	UserPermissionsDAO userPermissionsDAO = new UserPermissionsDAO();
				    	ArrayList<UserPermissions> listUserPermissions = userPermissionsDAO.getItems();
				    	if (listUserPermissions.size() > 0) {
				    		for (UserPermissions item : listUserPermissions) {
				    %>
					    <option 
					    	<%if(item.getName().equals("user")) out.print(" selected");%> 
					    	value="<%=item.getId()%>"><%=item.getName()%>
					    </option>
					<%
				    		}
				    	}
					%>
				    </select>
				</div>
	            <div class="form-group d-inline-block">
	            	<button type="submit" id="submit" class="btn btn-primary" name="submit">
	            		<i class="fas fa-plus"></i>
	            		Add
	            	</button>
	            </div>
	          </form>
	        </div>
            
            <div class="card-footer small text-muted">Hi <%=userLogin.getUsername()%>. Have a nice day.</div>
          </div>
          
        </div>
        <!-- /.container-fluid -->
		<%@ include file="/template/admin/inc/footer.jsp" %>
        <%@ include file="/template/admin/inc/script.jsp" %>
        <script>
		$('title').html('User Add');
		</script>
        <!-- code script here -->
        <script type="text/javascript">document.getElementById('users').classList.add('active');</script>
        
        <%@ include file="/template/admin/inc/end-html.jsp" %>