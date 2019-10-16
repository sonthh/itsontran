<%@page import="model.bean.Slide"%>
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
              <a href="<%=request.getContextPath()%>/admin/slides">Slides</a>
            </li>
            <li class="breadcrumb-item active">Add</li>
          </ol>

          <!-- DataTables Example -->
          <div class="card mb-3">
            <div class="card-header">
              <i class="fas fa-table"></i>
              Eid A Slide
            </div>
            <%
	            String msg = request.getParameter("msg");
	        	if ("0".equals(msg)) {
	        		out.print("<div class='alert alert-warning danger'><i class='fas fa-times'></i>Have a error.</div>");
	        	}
	        	
	        	String link = "", sort = "", picture = "", name="";
	        	Slide slide = (Slide) request.getAttribute("slide");
	        	if (slide != null) {
	        		link = slide.getLink();
	        		sort = slide.getSort() + "";
	        		picture = slide.getPicture();
	        		name = slide.getName();
	        	}
	        	if (request.getParameter("link") != null)
	        		link = request.getParameter("name");
	        	if (request.getParameter("sort") != null)
	        		sort = request.getParameter("sort");
	        	if (request.getParameter("name") != null)
	        		sort = request.getParameter("name");
            %>
            <div class="card-body">
	          <form action="" method="post" enctype="multipart/form-data">
	          	<div class="form-group">
				    <label for="image">Choose image</label>
				    <input type="file" class="form-control-file btn btn-outline-secondary" id="image" name="image" accept="image/*" />
				</div>
				<%
					if (!"".equals(picture)) {
				%>
				<div class="form-group">
	            	<img src="<%=request.getContextPath()%>/files/<%=picture%>" alt="" class="img-thumbnail" style="width: 200px;">
				</div>
				<%
					}
				%>
	            <div class="form-group">
	              <div class="form-label-group">
	                <input type="text" name="link" value="<%=link%>" id="inputLink" class="form-control"
	                 	placeholder="Link" required="required" /> 
	                <label for="inputLink">Link</label>
	              </div>
	            </div>
	            <div class="form-group">
	              <div class="form-label-group">
	                <input type="text" name="name" value="<%=name%>" id="inputName" class="form-control"
	                 	placeholder="Name" required="required" /> 
	                <label for="inputName">Name</label>
	              </div>
	            </div>
				<div class="form-group">
	              <div class="form-label-group">
	                <input type="number" min="1" name="sort" value="<%=sort%>" id="inputSort" class="form-control"
	                 	placeholder="Sort" required="required" /> 
	                <label for="inputSort">Sort</label>
	              </div>
	            </div>
				
	            <div class="form-group d-inline-block">
	            	<button type="submit" id="submit" class="btn btn-primary" name="submit">
	            		<i class="fas fa-plus"></i>
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
		$('title').html('Slide Edit');
		</script>
        <!-- code script here -->
        <script type="text/javascript">document.getElementById('slides').classList.add('active');</script>
        
        <%@ include file="/template/admin/inc/end-html.jsp" %>