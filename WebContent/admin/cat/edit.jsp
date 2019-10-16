<%@page import="util.MenuCreationUtil"%>
<%@page import="model.bean.Category"%>
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
              <a href="<%=request.getContextPath()%>/admin/categories">Categories</a>
            </li>
            <li class="breadcrumb-item active">Edit</li>
          </ol>

          <!-- DataTables Example -->
          <div class="card mb-3">
            <div class="card-header">
              <i class="fas fa-table"></i>
              Edit A Category
            </div>
            <div id="category-exist">
            <%
            	String msg = request.getParameter("msg");
            	if ("0".equals(msg)) {
            		out.print("<div class='alert alert-warning message'><i class='fas fa-times'></i>  Category is existed.</div>");
            	}
            %>
            </div>
            <div class="card-body">
	          <form action="" method="post">
	            <div class="form-group">
	              <%
	            	Category category = (Category) request.getAttribute("category");
	                int id = 0;
	                String name = "";
	                if (category != null) {
	                	id= category.getParentCategoryId();
	                	name = category.getName();
	                }
	                
	              %>
	              <div class="form-label-group">
	                <input type="text" name="name" value="<%=name%>" id="inputName" class="form-control"
	                 	placeholder="Category" pattern=".{3,32}" title="Category name from 3 to 32 letter" 
	                 	maxlength="32" required autofocus="autofocus" />
	                <label for="inputName">Category</label>
	              </div>
	            </div>
	            <div class="form-group">
				    <label for="categoriesList">Parent category.</label>
				    <select class="form-control" name="parent-category">
				    	<option value="0">Menu Level 0</option>
					    <%
					    	//id là cái selected, category.getId() là cái disable vì 1 danh mục không thể chọn chính nó làm cha 
					    	MenuCreationUtil.createOption(out, id, category.getId());
					    %>
				    </select>
				</div>
	            <div class="form-group d-inline-block">
	            	<button type="submit" id="submit" class="btn btn-primary" name="submit">
	            		<i class="fas fa-edit"></i>
	            		Edit
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
		$('title').html('Category Edit');
		</script>
        <!-- code script here -->
        <script type="text/javascript">document.getElementById('categories').classList.add('active');</script>
        
        <%@ include file="/template/admin/inc/end-html.jsp" %>