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
              <a href="<%=request.getContextPath()%>/admin/newses">Newses</a>
            </li>
            <li class="breadcrumb-item active">Add</li>
          </ol>

          <!-- DataTables Example -->
          <div class="card mb-3">
            <div class="card-header">
              <i class="fas fa-table"></i>
              Add A News
            </div>
            <div id="category-exist">
            <%
            	String msg = request.getParameter("msg");
            	if ("0".equals(msg)) {
            		out.print("<div class='alert alert-warning danger'><i class='fas fa-times'></i>Have a error.</div>");
            	}
            	String name = "", preview = "", detail = "";
            	if (request.getParameter("name") != null)
            		name = request.getParameter("name");
            	if (request.getParameter("preview") != null)
            		preview = request.getParameter("preview");
            	if (request.getParameter("detail") != null)
            		detail = request.getParameter("detail");
            %>
            </div>
            <div class="card-body">
	          <form action="" method="post" enctype="multipart/form-data">
	          	<!-- Tên tin -->
	            <div class="form-group">
	                <label for="name">News</label>
	                <input type="text" name="name" value="<%=name%>" id="name" class="form-control"
	                 	required autofocus="autofocus" />
	            </div>
	            <!-- Danh mục tin -->
	            <div class="form-group">
				    <label for=category>Category.</label>
				    <select class="form-control" name="category" required="required">
					    <%
					    	//out, idSelected, idDisabled
					    	MenuCreationUtil.createOption(out, -1, -1);
					    %>
				    </select>
				</div>
				<div class="form-group">
				    <label for="image">Choose image</label>
				    <input type="file" class="form-control-file btn btn-outline-secondary" id="image" name="image" accept="image/*" required="required" />
				</div>
				<div class="form-group">
				    <label for="preview">Preview</label>
				    <textarea class="form-control" name="preview" id="preview" rows="3" required="required"><%=preview%></textarea>
				</div>
				<div class="form-group">
				    <label for="detail">Detail</label>
				    <textarea class="form-control" name="detail" id="detail" rows="3" required="required"><%=detail%></textarea>
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
		$('title').html('News Add');
		</script>
        <!-- code script here -->
        <script type="text/javascript">document.getElementById('newses').classList.add('active');</script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/libraries/ckeditor/ckeditor.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/libraries/ckfinder/ckfinder.js"></script>
        <script type="text/javascript">
			var editor = CKEDITOR.replace('detail');
			CKFinder.setupCKEditor(editor, '<%=request.getContextPath()%>/libraries/ckfinder/');
		</script>
        
        <%@ include file="/template/admin/inc/end-html.jsp" %>