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
            <li class="breadcrumb-item active">News</li>
          </ol>

          <!-- DataTables Example -->
          <div class="card mb-3">
            <div class="card-header">
              <i class="fas fa-table"></i>
              News List
             </div>
            <div class="card-body">
              <div class="table-responsive">
                <div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
                	<div class="row justify-content-between">
                		<div class="col-sm-12 col-md-2">
		                	<!-- Button add -->
						    <a href="<%=request.getContextPath()%>/admin/news/add" id="btn-add" class="btn btn-primary">
						    	<i class="fas fa-plus"></i>
						    	Add
						    </a>
                		</div>
                		<div class="col-sm-12 col-md-5">
                		<%
                			/* message msg=0|1|2|3|4|5 */
                			MessageUtil.getMessage(request, out);
                		%>
                		</div>
	                	<div class="col-sm-12 col-md-5">
	                		<form action="<%=request.getContextPath()%>/admin/news/search" method="post" class="d-flex input-group" id="search-form">
	                			<%
	                				String name = "";
	                				if (request.getParameter("name") != null) {
	                					name = request.getParameter("name");
	                				}
	                			%>
					        	<input type="text" class="form-control" value="<%=name%>" name="name" required placeholder="Search newses" aria-label="Search" aria-describedby="basic-addon2" />
					          	<div class="input-group-append">
					            	<button class="btn btn-primary" type="submit">
						            	<i class="fas fa-search"></i>
						            </button>
					          	</div>
				          	</form>
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
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 540px;">
		                    				Name
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 170px;">
		                    				Date
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 680px;">
		                    				Picture
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 175px;">
		                    				Create by
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 0px;">
		                    				Category
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 0px;">
		                    				Active
		                    			</th>
		                    			<th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Position: activate to sort column ascending" style="width: 420px;">
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
		                  				ArrayList<News> listNewses =  (ArrayList<News>) request.getAttribute("listNewses");
		                  				if (listNewses != null && listNewses.size() > 0) {
		                  					UserDAO userDAO = new UserDAO();
		                  					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
		                  					for (News itemNews : listNewses) {
		                  						boolean check = userLogin.getUsername().equals("admin") || userLogin.getId() == itemNews.getCreateBy();
		                  						if (check) {
		                  			%>
		                  			<tr role="row" class="odd">
					                    <td class="sorting_1"><%=itemNews.getId()%></td>
					                    <td><%=itemNews.getName()%></td>
					                    <td><%=DateUtil.getDateFormatDetail(itemNews.getDateCreate())%></td>
					                    <td>
					                    	<%if(!itemNews.getPicture().equals("")){%>
					                    	<img src="<%=request.getContextPath()%>/files/<%=itemNews.getPicture()%>" alt="" class="img-thumbnail" style="width: 180px;height: 120px">
					                    	<%}else{%>
					                    	<strong>No image</strong>
					                    	<%}%>
					                    </td>
					                    <td><%=userDAO.getItem(itemNews.getCreateBy()).getUsername()%></td>
					                    <td><%=itemNews.getCategory().getName()%></td>
					                    <td class="status">
					                   		<span class="status-news-<%=itemNews.getId()%> <%if(itemNews.getActive() == 1) out.print("active");%>" onclick='changeStatusNews(<%=itemNews.getId()%>, <%=itemNews.getActive()%>)'>Active</span>
					                    </td>
					                    <td>
											<a href="<%=request.getContextPath()%>/admin/news/edit?page=<%=currentPage%>&id=<%=itemNews.getId()%>" class="btn btn-info btn-sm">
								          		<i class="fas fa-edit"></i>Edit</a>
											<a href="<%=request.getContextPath()%>/admin/news/del?page=<%=currentPage%>&id=<%=itemNews.getId()%>" class="btn btn-danger btn-sm"
												onclick="return confirm('Are you want to delete <%=itemNews.getName()%>?')">
								          		<i class="fas fa-trash-alt"></i>Delete</a>
							          		<a href="<%=request.getContextPath()%>/admin/news/comments?page=<%=currentPage%>&id=<%=itemNews.getId()%>" class="btn btn-primary btn-sm mt-1">
							          		<i class="fas fa-comments"></i>Comment</a>
										</td>
		                   			</tr>
		                   			<%
		                  						}
		                  					}
		                  				} else {
		                    		%>
		                    		<tr><td colspan="8" style="text-align: center"><strong>O news.</strong></td></tr>
		                    		<%
		                  				}
		                    		%>
								</tbody>
		                	</table>
                		</div>
               		</div>
               		<%
                            	
                    	if (listNewses != null && listNewses.size() > 0) {
                    		int numberOfPages = Integer.parseInt(request.getAttribute("numberOfPages").toString());
                        	int numberOfItems = Integer.parseInt(request.getAttribute("numberOfItems").toString());
                    %>
               		<div class="row">
               			<div class="col-sm-12 col-md-5">
               				<div class="dataTables_info" id="dataTable_info" role="status" aria-live="polite">
               					<%
               						int from = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE + 1;
               						int to = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE + listNewses.size();
               					%>
               					Showing <%=from%> to <%=to%> of <%=numberOfItems%> newses.
               				</div>
               			</div>
               			<div class="col-sm-12 col-md-7">
               				<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate">
               					<ul class="pagination">
	               					<%
	                              		String href = request.getContextPath() + "/admin/newses?page=";
	                              	%>
	                              	<!-- Xử lí nut previous -->
               						<li class="paginate_button page-item previous <%if(currentPage == 1) out.print("disabled");%>" id="dataTable_previous">
               							<a href="<%=href + (currentPage - 1)%>" aria-controls="dataTable" data-dt-idx="0" tabindex="0" class="page-link">
               								Previous
               							</a>
               						</li>
               						
               						<!-- Xử lí những nút ở giữa -->
               						<%
               							if (currentPage <= DefineUtil.NUMBER_PAGINATION_PER_PAGE) { /* Trường hợp 1 */
                                    		int len = numberOfPages < DefineUtil.NUMBER_PAGINATION_PER_PAGE ? numberOfPages : DefineUtil.NUMBER_PAGINATION_PER_PAGE;
                                        	for (int i = 1; i <= len; i++) {
               						%>
               						<li class="paginate_button page-item <%if(currentPage == i) out.print("active");%>">
               							<a href="<%=href + i%>" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">
               								<%=i%>
               							</a>
               						</li>
               						<%
                                        	}
               								if (numberOfPages > DefineUtil.NUMBER_PAGINATION_PER_PAGE) {
               						%>
               						<li class="paginate_button page-item">
               							<a href="<%=href + numberOfPages%>" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">
               								End
               							</a>
               						</li>
               						<%
               								}
               							} else if (currentPage > numberOfPages - DefineUtil.NUMBER_PAGINATION_PER_PAGE) { /* Trường hợp 2 */
               						%>
               						<li class="paginate_button page-item">
               							<a href="<%=href + 1%>" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">
               								One
               							</a>
               						</li>
               						<%
	                                 		for (int i = numberOfPages - DefineUtil.NUMBER_PAGINATION_PER_PAGE + 1; i <= numberOfPages; i++) {
	                                %>
	                                <li class="paginate_button page-item <%if(currentPage == i) out.print("active");%>">
               							<a href="<%=href + i%>" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">
               								<%=i%>
               							</a>
               						</li>
	                                <%
	                                 		}
	                                %>
               						<%
               							} else {
               						%>
               						<li class="paginate_button page-item">
               							<a href="<%=href + 1%>" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">
               								One
               							</a>
               						</li>
               						<%
               								for (int i = currentPage - DefineUtil.NUMBER_PAGINATION_PER_PAGE / 2; i <= currentPage + DefineUtil.NUMBER_PAGINATION_PER_PAGE / 2; i++) {
               						%>
               						<li class="paginate_button page-item <%if(currentPage == i) out.print("active");%>">
               							<a href="<%=href + i%>" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">
               								<%=i%>
               							</a>
               						</li>
               						<%
               								}
               						%>
               						<li class="paginate_button page-item">
               							<a href="<%=href + numberOfPages%>" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">
               								End
               							</a>
               						</li>
               						<%
               							}
               						%>
               						
               						<!-- Xử lí nut next -->
               						<li class="paginate_button page-item next <%if(currentPage == numberOfPages) out.print("disabled");%>" id="dataTable_next">
               							<a href="<%=href + (currentPage + 1)%>" aria-controls="dataTable" data-dt-idx="7" tabindex="0" class="page-link">
               								Next
               							</a>
               						</li>
               					</ul>
               				</div>
               			</div>
               		</div>
               		<%}%>
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
		$('title').html('News');
		</script>
        <!-- code script here -->
        <script>document.getElementById('newses').classList.add('active');</script>
        
        <%@ include file="/template/admin/inc/end-html.jsp" %>
        