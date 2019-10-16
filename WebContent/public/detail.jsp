<%@page import="java.util.Date"%>
<%@page import="model.dao.CommentDAO"%>
<%@page import="model.bean.Comment"%>
<%@page import="model.bean.Category"%>
<%@page import="model.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/template/public/inc/header.jsp" %>
		<%
			CommentDAO commentDAO = new CommentDAO();
			UserDAO userDAO = new UserDAO();
			News news = (News) request.getAttribute("news");
		%>
		<div class="site-main-container">
			<%@ include file="/template/public/inc/slide.jsp" %>
			
			<!-- Start latest-post Area -->
			<section class="latest-post-area pb-120">
				<div class="container no-padding">
					<div class="row">
						<div class="col-lg-8 post-list">
						<%
							if (news != null) {
						%>
							<!-- Start single-post Area -->
							<div class="single-post-wrap">
								<div class="content-wrap">
									<ul class="tags mt-10">
										<li><a href="<%=request.getContextPath()%>/category/<%=StringUtil.makeSlug(news.getCategory().getName())%>-<%=news.getCategory().getId()%>"><%=news.getCategory().getName()%></a></li>
									</ul>
									<a href="<%=request.getContextPath()%>/detail/<%=StringUtil.makeSlug(news.getName())%>-<%=news.getId()%>">
										<h3><%=news.getName()%></h3>
									</a>
									<ul class="meta pb-20">
										<li><a href="#"><span class="lnr lnr-user"></span><%=userDAO.getItem(news.getCreateBy()).getUsername()%></a></li>
										<li><a href="#"><span class="lnr lnr-calendar-full"></span><%=DateUtil.getDateFormat(news.getDateCreate())%></a></li>
										<li><a href="#"><span class="lnr lnr-bubble"></span><%=commentDAO.countCommentByNewsId(news.getId())%> Comments</a></li>
										<li><a href="#"><span class="fa fa-eye" style="font-size: 14px;margin-right: 10px;"></span><%=news.getViews()%> </a></li>
									</ul>
									<p>
										<%=news.getDetail()%>
									</p>
									<!-- <blockquote></blockquote> -->
								
								
								<div class="comment-sec-area">
									<div class="container">
										<div class="row flex-column">
											
											<%
												@SuppressWarnings("unchecked")
												ArrayList<Comment> listComments = (ArrayList<Comment>) request.getAttribute("listComments");
												if (listComments != null && listComments.size() > 0) {
											%>
											<h6>Comments</h6>
											<%
													for (Comment itemComment : listComments) {  
											%>
											<div class="comment-list-wrapper-<%=itemComment.getId()%>">
												<div class="comment-list">
													<div class="single-comment justify-content-between d-flex">
														<div class="user justify-content-between d-flex">
															<div class="thumb">
																<i class="fa fa-user"></i>
															</div>
															<div class="desc">
																<h5><a href="#"><%=userDAO.getItem(itemComment.getUserId()).getUsername()%></a></h5>
																<p class="date"><%=DateUtil.getDateFormatDetail(itemComment.getDateCreate())%> </p>
																<p class="comment">
																	<%=itemComment.getContent()%>
																</p>
															</div>
														</div>
														<div class="reply-btn">
															<a onclick="return reply(<%=itemComment.getId()%>, '<%=userDAO.getItem(itemComment.getUserId()).getUsername()%>')" href="javascript:void(0)" class="btn-reply text-uppercase">reply</a>
														</div>
													</div>
												</div>
												<%
														//một khối comment đặt trong <div class="comment-list-wrapper"> rồi trong cái này có thêm các comment con nữa
														ArrayList<Comment> list = new CommentDAO().getActiveItemsByCommentParentIdOfNewsAll(itemComment.getId(), news.getId());
														for (Comment item : list) {
															String className = item.getParentId() == 0 ? "" : "left-padding";
												%>
												<div class="comment-list <%=className%>">
													<div class="single-comment justify-content-between d-flex">
														<div class="user justify-content-between d-flex">
															<div class="thumb">
																<i class="fa fa-user"></i>
															</div>
															<div class="desc">
																<h5><a href="#"><%=userDAO.getItem(item.getUserId()).getUsername()%></a></h5>
																<p class="date"><%=DateUtil.getDateFormatDetail(item.getDateCreate())%> </p>
																<p class="comment">
																	<%=item.getContent()%>
																</p>
															</div>
														</div>
														<div class="reply-btn">
															<a onclick="return reply(<%=itemComment.getId()%>, '<%=userDAO.getItem(item.getUserId()).getUsername()%>')" href="javascript:void(0)" class="btn-reply text-uppercase">reply</a>
														</div>
													</div>
												</div>
												<%
														}
												%>
												<div class="comment-list left-padding comment-reply comment-reply-<%=itemComment.getId()%>">
													<div class="single-comment">
														<div class="user d-flex">
															<div class="thumb">
																<i class="fa fa-user"></i>
															</div>
															<div class="desc" style="width: 100%">
																<%
																	if (userLogin != null) {
																%>
																<h5><a href="#"><%=userLogin.getUsername()%></a></h5>
																<%
																	} else {
																%>
																<h5><a href="<%=request.getContextPath()%>/login">Login to comment this post</a></h5>
																<%		
																	}
																%>
																<form class="row" action="javascript:void(0)">
																	<div class="form-group col-sm-8">
																    	<textarea class="form-control" id="" rows="3" name="comment" required="required"></textarea>
																  	</div>
																  	<div class="col-sm-4">
																  		<button type="button" onclick="upComment(<%=news.getId()%>, <%=itemComment.getId()%>, <%if(userLogin == null) out.print("0"); else out.print(userLogin.getId()); %>)" class="btn btn-dark">Ok</button>
																  	</div>
																</form>
															</div>
														</div>
														
													</div>
												</div>
											</div>
											<%
													}
												/* ô comment ngoài cùng */
											%>
											<div class="comment-list-wrapper-">
												<div class="comment-list d-block comment-reply-0">
													<div class="single-comment">
														<div class="user d-flex">
															<div class="thumb">
																<i class="fa fa-user"></i>
															</div>
															<div class="desc" style="width: 100%">
																<%
																	if (userLogin != null) {
																%>
																<h5><a href="#"><%=userLogin.getUsername()%></a></h5>
																<%
																	} else {
																%>
																<h5><a href="<%=request.getContextPath()%>/login">Login to comment this post</a></h5>
																<%		
																	}
																%>
																<form class="row" action="javascript:void(0)">
																	<div class="form-group col-sm-8">
																    	<textarea class="form-control" id="" rows="3" name="comment" required="required"></textarea>
																  	</div>
																  	<div class="col-sm-4">
																  		<button type="button" onclick="upComment(<%=news.getId()%>, 0, <%if(userLogin == null) out.print("0"); else out.print(userLogin.getId()); %>)" class="btn btn-dark">Ok</button>
																  	</div>
																</form>
															</div>
														</div>
														
													</div>
												</div>
											</div>
											<%
												} else { /* Chưa có comment nào thì có ô textarea cho nó nhập */
											%>
											<h6>Comments</h6>
											<div class="comment-list-wrapper-">
												<div class="comment-list left-padding d-block comment-reply-0">
													<div class="single-comment">
														<div class="user d-flex">
															<div class="thumb">
																<i class="fa fa-user"></i>
															</div>
															<div class="desc" style="width: 100%">
																<%
																	if (userLogin != null) {
																%>
																<h5><a href="#"><%=userLogin.getUsername()%></a></h5>
																<%
																	} else {
																%>
																<h5><a href="<%=request.getContextPath()%>/login">Login to comment this post</a></h5>
																<%		
																	}
																%>
																<form class="row" action="javascript:void(0)">
																	<div class="form-group col-sm-8">
																    	<textarea class="form-control" id="" rows="3" name="comment" required="required"></textarea>
																  	</div>
																  	<div class="col-sm-4">
																  		<button type="button" onclick="upComment(<%=news.getId()%>, 0, <%if(userLogin == null) out.print("0"); else out.print(userLogin.getId()); %>)" class="btn btn-dark">Ok</button>
																  	</div>
																</form>
															</div>
														</div>
														
													</div>
												</div>
											</div>
											<%
												}
											%>
										</div>
									</div>
								</div>
							</div>
						</div>
						<%
							}
						%>
						<!-- End single-post Area -->
					</div>
					
					<%@ include file="/template/public/inc/right_bar.jsp" %>
					
				</div>
			</div>
		</section>
		<!-- End latest-post Area -->
	</div>
	<script src="<%=request.getContextPath()%>/template/public/js/vendor/jquery-2.2.4.min.js"></script>
	<script>
	var x = $('.latest-post-area').offset().top - 122;
	$("html, body").animate({ scrollTop: x }, 600);
	$('title').html('<%=news.getName()%>');
	</script>
	<%@ include file="/template/public/inc/footer.jsp" %>