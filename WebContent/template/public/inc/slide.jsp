<%@page import="java.util.Date"%>
<%@page import="util.DateUtil"%>
<%@page import="model.bean.Slide"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.dao.SlideDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	SlideDAO slideDAO = new SlideDAO();
    	ArrayList<Slide> listSlides = slideDAO.getItems(3);
    %>
    
    
			<!-- Start top-post Area -->
			<section class="top-post-area pt-10">
				<div class="container no-padding">
					<div class="row small-gutters d-flex justify-content-center">
						<div id="slide-header" class="carousel slide w-100" style="padding: 5px;" data-ride="carousel">
							<ol class="carousel-indicators">
							<%
								if (listSlides.size() > 0) {
									for (int i = 0; i < listSlides.size(); i++) {
							%>
								<li data-target="#slide-header" data-slide-to="<%=i%>" class="<%if(i == 0)out.print("active");%>"></li>
							<%
									}
								}
							%>
							</ol>
							<div class="carousel-inner">
								<%
									if (listSlides.size() > 0) {
										for (int i = 0; i < listSlides.size(); i++) {
								%>
								<div class="carousel-item <%if(i == 0)out.print("active");%>">
									<div class="single-top-post">
										<div class="feature-image-thumb relative">
											<div class="overlay overlay-bg"></div>
											<img class="img-fluid" src="<%=request.getContextPath()%>/files/<%=listSlides.get(i).getPicture()%>" alt="" style="height: 430px;">
										</div>
										<div class="top-post-details">
											<ul class="tags">
												<li><a href="">Breaking news</a></li>
											</ul>
											<a href="<%=listSlides.get(i).getLink()%>">
												<h4><%=listSlides.get(i).getName()%></h4>
											</a>
											<ul class="meta">
												<li><a href="javascript:void(0)"><span class="lnr lnr-calendar-full"></span><%=DateUtil.getDateFormat(new Date())%></a></li>
											</ul>
										</div>
									</div>
								</div>
								<%
										}
									}
								%>
							</div>
							<a class="carousel-control-prev" href="#slide-header" role="button" data-slide="prev">
								<span class="carousel-control-prev-icon" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							</a>
							<a class="carousel-control-next" href="#slide-header" role="button" data-slide="next">
								<span class="carousel-control-next-icon" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							</a>
						</div>
					</div>
				</div>
			</section>
			<!-- End top-post Area -->