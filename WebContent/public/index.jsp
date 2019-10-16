<%@page import="model.dao.CommentDAO"%>
<%@page import="model.bean.Category"%>
<%@page import="util.StringUtil"%>
<%@page import="util.DefineUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.dao.UserDAO"%>
<%@page import="model.bean.News"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/template/public/inc/header.jsp" %>

		<div class="site-main-container">
		
			<%@ include file="/template/public/inc/slide.jsp" %>
			
			<%
				UserDAO userDAO = new UserDAO();
				CommentDAO commentDAO = new CommentDAO();
			%>
			<!-- Start latest-post Area -->
			<section class="latest-post-area pb-120">
				<div class="container no-padding">
					<div class="row">
						<div class="col-lg-8 post-list">
							<!-- Start latest-post Area -->
							<div class="latest-post-wrap">
								<h4 class="cat-title">Latest News</h4>
								<%
									
									@SuppressWarnings("unchecked")
									ArrayList<News> listNewses = (ArrayList<News>) request.getAttribute("listNewses");
									if (listNewses != null && listNewses.size() > 0) {
										for (News itemNews : listNewses) {
								%>
								<div class="single-latest-post row align-items-center">
									<div class="col-lg-5 post-left">
										<div class="feature-img relative">
											<div class="overlay overlay-bg"></div>
											<img class="img-fluid" src="<%=request.getContextPath()%>/files/<%=itemNews.getPicture()%>" alt="" style="height: 190px">
										</div>
										<ul class="tags">
										<%
											Category category = itemNews.getCategory();
											String href = request.getContextPath() + "/category/" + StringUtil.makeSlug(category.getName()) + "-" + category.getId(); 
										%>
											<li><a href="<%=href%>"><%=itemNews.getCategory().getName()%></a></li>
										</ul>
									</div>
									<div class="col-lg-7 post-right">
										<a href="<%=request.getContextPath()%>/detail/<%=StringUtil.makeSlug(itemNews.getName())%>-<%=itemNews.getId()%>">
											<h4><%=StringUtil.getText(itemNews.getName(), 60)%></h4>
										</a>
										<ul class="meta">
											<li><a href="#"><span class="lnr lnr-user"></span><%=userDAO.getItem(itemNews.getCreateBy()).getUsername()%></a></li>
											<li><a href="#"><span class="lnr lnr-calendar-full"></span><%=DateUtil.getDateFormat(itemNews.getDateCreate())%></a></li>
											<li><a href="#"><span class="lnr lnr-bubble"></span><%=commentDAO.countCommentByNewsId(itemNews.getId())%> Comments</a></li>
										</ul>
										<p><%=StringUtil.getText(itemNews.getPreview(), 170)%></p>
									</div>
								</div>
								<%
										}
									}
								%>
								<%
			                    	if (listNewses != null && listNewses.size() > 0) {
			                    		int currentPage = Integer.parseInt(request.getAttribute("currentPage").toString());
			                    		int numberOfPages = Integer.parseInt(request.getAttribute("numberOfPages").toString());
			                        	int numberOfItems = Integer.parseInt(request.getAttribute("numberOfItems").toString());
			                        	String href = request.getContextPath() + "/page/";
			                    %>
								<!-- pagination -->
								<nav aria-label="pagination-news">
									<ul class="pagination justify-content-end">
										<!-- previous -->
								    	<li class="page-item <%if(currentPage == 1)out.print("disabled");%>"><a class="page-link" href="<%=href + (currentPage - 1)%>">Previous</a></li>
								    	
								    	<!-- pagination button -->
								    	<%
	               							if (currentPage <= DefineUtil.NUMBER_PAGINATION_PER_PAGE) { /* Trường hợp 1: start segment */
	                                    		int len = numberOfPages < DefineUtil.NUMBER_PAGINATION_PER_PAGE ? numberOfPages : DefineUtil.NUMBER_PAGINATION_PER_PAGE;
	                                        	for (int i = 1; i <= len; i++) {
	               						%>
								    	<li class="page-item<%if(currentPage == i) out.print(" active");%>"><a class="page-link" href="<%=href + i%>"><%=i%></a></li>
								    	<%
								    		
	                                        	}
	                                        	if (numberOfPages > DefineUtil.NUMBER_PAGINATION_PER_PAGE) {
	                                    %>
	                                    <li class="page-item"><a class="page-link" href="<%=href + numberOfPages%>">End</a></li>
	                                    <%
	                                        	}
	               							} else if (currentPage > numberOfPages - DefineUtil.NUMBER_PAGINATION_PER_PAGE) { /* Trường hợp 2: between segment */
	                                    %>
	                                    <li class="page-item"><a class="page-link" href="<%=href + 1%>">One</a></li>
								    	<%
								    			for (int i = numberOfPages - DefineUtil.NUMBER_PAGINATION_PER_PAGE + 1; i <= numberOfPages; i++) {
								    	%>
								    	<li class="page-item<%if(currentPage == i) out.print(" active");%>"><a class="page-link" href="<%=href + i%>"><%=i%></a></li>
								    	<%
								    			}
	               							} else { /* end segment */
	               						%>
	               						<li class="page-item"><a class="page-link" href="<%=href + 1%>">One</a></li>
	               						<%
	               								for (int i = currentPage - DefineUtil.NUMBER_PAGINATION_PER_PAGE / 2; i <= currentPage + DefineUtil.NUMBER_PAGINATION_PER_PAGE / 2; i++) {
	               						%>
	               						<li class="page-item<%if(currentPage == i) out.print(" active");%>"><a class="page-link" href="<%=href + i%>"><%=i%></a></li>	
	               						<%
	               								}
	               						%>
	               						<li class="page-item"><a class="page-link" href="<%=href + numberOfPages%>">End</a></li>
	               						<%
	               							}
								    	%>
								    	
								    	<!-- next -->
								    	<li class="page-item <%if(currentPage == numberOfPages)out.print("disabled");%>">
								      		<a class="page-link" href="<%=href + (currentPage + 1)%>">Next</a>
								    	</li>
								  	</ul>
								</nav>
								<%}%>
							</div>
							<!-- End latest-post Area -->
						</div>
						
						<%@ include file="/template/public/inc/right_bar.jsp" %>
						
					</div>
				</div>
			</section>
			<!-- End latest-post Area -->
		</div>
		<script src="<%=request.getContextPath()%>/template/public/js/vendor/jquery-2.2.4.min.js"></script>
		<%@ include file="/template/public/inc/footer.jsp" %>