<%@page import="model.bean.Comment"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.dao.UserDAO"%>
<%@page import="util.MenuCreationUtil"%>
<%@page import="model.bean.Category"%>
<%@page import="util.MessageUtil"%>
<%@page import="util.DefineUtil"%>
<%@page import="model.bean.User"%>
<%@page import="model.bean.News"%>
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
              <a href="<%=request.getContextPath()%>/admin/newses">News</a>
            </li>
            <li class="breadcrumb-item active">Comment</li>
          </ol>

		  <%
		  		News news = (News) request.getAttribute("news");
		  		String newsName = "";
		  		if (news != null)
		  			newsName = news.getName();
		  
		  %>
		  
          <!-- DataTables Example -->
          <div class="card mb-3">
            <div class="card-header">
              <!--  -->
              <div class="row">
           		<div class="col-sm-12">
           			<div class='alert alert-primary message mb-0'>Comments of <span class="text-danger"><%=newsName%></span></div>
           		</div>
              </div>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
                	
                	<div class="row">
                		<div class="col-sm-12">
                			<% MessageUtil.getMessage(request, out); %>
                		</div>
                	</div>
                	<div class="row">
                		<div class="col-sm-12">
		                	<table class="table table-hover table-bordered dataTable" id="dataTable" width="100%" cellspacing="0" role="grid" aria-describedby="dataTable_info" style="width: 100%;">
		                  		<thead>
		                    		<tr role="row">
		                    			<th class="sorting_asc" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Name: activate to sort column descending" style="width: 0px;">
		                    				Id
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 100px;">
		                    				User
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 340px;">
		                    				Content
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 160px;">
		                    				Date
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 0px;">
		                    				Active
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 0px;">
		                    				Action
		                    			</th>
		                    		</tr>
		                  		</thead>
		                  		<tbody>
		                  			<%
			                  			int currentPage = 1;
	                            		if (request.getAttribute("currentPage") != null)
	                                		currentPage = Integer.parseInt(request.getAttribute("currentPage").toString());
	                            		
		                  				@SuppressWarnings("unchecked")
		                  				ArrayList<Comment> listComments =  (ArrayList<Comment>) request.getAttribute("listComments");
		                  				if (listComments != null && listComments.size() > 0) {
		                  					UserDAO userDAO = new UserDAO();
		                  					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
		                  					for (Comment itemComment : listComments) {
		                  			%>
		                  			<tr role="row" class="odd <%if(itemComment.getParentId() == 0) out.print("table-secondary");%>">
					                    <td class="sorting_1"><%=itemComment.getId()%></td>
					                    <td><%=userDAO.getItem(itemComment.getUserId()).getUsername()%></td>
					                    <td><%=itemComment.getContent()%></td>
					                    <td><%=DateUtil.getDateFormatDetail(itemComment.getDateCreate())%></td>
					                    <td class="status">
					                   		<span class="status-comment-<%=itemComment.getId()%> <%if(itemComment.getActive()==1)out.print("active");%>" 
					                   			onclick="changeStatusCommentOfNews(<%=itemComment.getId()%>, <%=news.getId()%>, <%=itemComment.getActive()%>)">Active</span>
					                    </td>
					                    <td>
											<a href="<%=request.getContextPath()%>/admin/news/comment/del?page=<%=currentPage%>&id=<%=itemComment.getId()%>" class="btn btn-danger btn-sm"
												onclick="return confirm('Are you want to delete <%=itemComment.getId()%>?')">
								          		<i class="fas fa-trash-alt"></i>Delete</a>
										</td>
		                   			</tr>
		                   			<%
		                  						}
		                  					} else {
		                    		%>
		                    		<tr><td colspan="8" style="text-align: center"><span>No comment in this item.</span></td></tr>
		                    		<%
		                  				}
		                    		%>
								</tbody>
		                	</table>
                		</div>
               		</div>
               		
               	</div>
              </div>
            </div>
          <div class="card-footer small text-muted">Hi <%=userLogin.getUsername()%>. Have a nice day.</div>
        </div>

        </div>
        <!-- /.container-fluid -->

        <%@ include file="/template/admin/inc/footer.jsp" %>
        <%@ include file="/template/admin/inc/script.jsp" %>
        <script>
		$('title').html('News Comment');
		</script>
        <!-- code script here -->
        <script>document.getElementById('newses').classList.add('active');</script>
        
        <%@ include file="/template/admin/inc/end-html.jsp" %>
        