<%@page import="util.AuthUtil"%>
<%@page import="model.bean.User"%>
<%@page import="util.MenuCreationUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
		%>
		<!-- Mobile Specific Meta -->
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<!-- Favicon-->
		<link rel="shortcut icon" href="<%=url + "/template/public/img/favicon.ico"%>">
		<!-- Author Meta -->
		<meta name="author" content="colorlib">
		<!-- Meta Description -->
		<meta name="description" content="">
		<!-- Meta Keyword -->
		<meta name="keywords" content="">
		<!-- meta character set -->
		<meta charset="UTF-8">
		<!-- Site Title -->
		<title>itsontran</title>
		<link href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700" rel="stylesheet">
		<!--
		CSS
		============================================= -->
		<link rel="stylesheet" href="<%=url+"/template/public/css/linearicons.css"%>">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/template/public/css/font-awesome.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/template/public/css/bootstrap.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/template/public/css/magnific-popup.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/template/public/css/nice-select.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/template/public/css/animate.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/template/public/css/owl.carousel.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/template/public/css/jquery-ui.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/template/public/css/main.css">
		
		
	</head>
	<body>
		<header>
			
			<div class="header-top">
				<div class="container">
					<div class="row">
						<div class="col-lg-6 col-md-6 col-sm-6 col-6 header-top-left no-padding">
							<ul>
								<li><a target="_blank" href="https://www.facebook.com/tranhuuhongson1998"><i class="fa fa-facebook"></i></a></li>
								<li><a target="_blank" href="https://www.instagram.com/tranhuuhongson/"><i class="fa fa-instagram"></i></a></li>
								<li><a target="_blank" href="https://twitter.com/tranhuuhongson"><i class="fa fa-twitter"></i></a></li>
							</ul>
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-6 header-top-right no-padding">
							<ul class="nav-menu d-flex justify-content-end">
								<li><a href="<%=request.getContextPath()%>/about">About</a></li>
								<li><a href="<%=request.getContextPath()%>/contact">Contact</a></li>
								<%
									User userLogin = AuthUtil.getUserLoginPublic(request);
									if (userLogin == null) {
								%>
								<li><a href="<%=request.getContextPath()%>/login">Login</a></li>
								<%
									} else {
								%>
								<li><a title="Logout <%=userLogin.getUsername()%>" href="<%=request.getContextPath()%>/logout"><%=userLogin.getUsername()%>
									<i class="fa fa-user-circle" style="font-size: 13px;"></i>
								</a></li>
								<%
									}
								%>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="logo-wrap">
				<div class="container">
					<div class="row justify-content-between align-items-center">
						<div class="col-lg-4 col-md-4 col-sm-12 logo-left no-padding">
							<a href="<%=request.getContextPath()%>/">
								<img class="img-fluid" src="<%=request.getContextPath()%>/template/public/img/logo.png" alt="">
							</a>
						</div>
						<div class="col-lg-8 col-md-8 col-sm-12 logo-right no-padding ads-banner">
							<img class="img-fluid" src="<%=request.getContextPath()%>/template/public/img/banner-ad.png" alt="" style="width: 728px; height: 90px;">
						</div>
					</div>
				</div>
			</div>
			<div class="container main-menu" id="main-menu">
				<div class="row align-items-center justify-content-between">
					<nav id="nav-menu-container">
						<!-- Create multiple menu -->
						<%
							try {
								MenuCreationUtil.createMenu(out, request);
							} catch (Exception e) {
								//System.out.print(e.getMessage());
							}
						%>
					</nav><!-- #nav-menu-container -->
					<div class="navbar-right">
						<form class="Search" method="post" action="<%=request.getContextPath()%>/search">
							<input type="text" class="form-control Search-box" name="name" id="Search-box" placeholder="Search" />
							<label for="Search-box" class="Search-box-label">
								<span class="lnr lnr-magnifier"></span>
							</label>
							<span class="Search-close">
								<span class="lnr lnr-cross"></span>
							</span>
						</form>
					</div>
				</div>
			</div>
		</header>