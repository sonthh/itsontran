<%@page import="java.util.Date"%>
<%@page import="util.DateUtil"%>
<%@page import="model.bean.User"%>
<%@page import="util.AuthUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>itsontran.com - admin</title>
    
    <!-- Favicon-->
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/template/public/img/favicon.ico">

    <!-- Bootstrap core CSS-->
    <link href="<%=request.getContextPath()%>/template/admin/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="<%=request.getContextPath()%>/template/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Page level plugin CSS-->
    <link href="<%=request.getContextPath()%>/template/admin/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="<%=request.getContextPath()%>/template/admin/css/sb-admin.css" rel="stylesheet">

  </head>

  <body id="page-top">

    <nav class="navbar navbar-expand navbar-dark bg-dark static-top">

      <a class="navbar-brand mr-1" href="<%=request.getContextPath()%>/admin">itsontran.com</a>

      <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle">
        <i class="fas fa-bars"></i>
      </button>

      <!-- Navbar Search -->
      <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
        <!-- <div class="input-group">
          <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
          <div class="input-group-append">
            <button class="btn btn-primary" type="button">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </div> -->
      </form>

      <!-- Navbar -->
      <ul class="navbar-nav ml-auto ml-md-0">
      	<!-- <li class="nav-item dropdown no-arrow mx-1">
          <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-bell fa-fw"></i>
            <span class="badge badge-danger">9+</span>
          </a>
          <div class="dropdown-menu dropdown-menu-right" aria-labelledby="alertsDropdown">
            <a class="dropdown-item" href="#">Action</a>
            <a class="dropdown-item" href="#">Another action</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">Something else here</a>
          </div>
        </li>
        <li class="nav-item dropdown no-arrow mx-1">
          <a class="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-envelope fa-fw"></i>
            <span class="badge badge-danger">7</span>
          </a>
          <div class="dropdown-menu dropdown-menu-right" aria-labelledby="messagesDropdown">
            <a class="dropdown-item" href="#">Action</a>
            <a class="dropdown-item" href="#">Another action</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">Something else here</a>
          </div>
        </li> -->
        <%
        	User userLogin = AuthUtil.getUserLogin(request);
        	if (userLogin != null) {
        %>
        <li class="nav-item dropdown no-arrow user-logo">
          <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-user-circle fa-fw"></i>
          </a>
          <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
            <a class="dropdown-item text-dark" href="" style="pointer-events: none;"><%if(userLogin != null)out.print(userLogin.getUsername());%></a>
            <!-- ajax when click and chang html ex: Login 10 hours ago -->
            <a class="dropdown-item text-secondary small" id="how-long-ago" href="" style="pointer-events: none;"></a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item text-primary" href="<%=request.getContextPath()%>/logout">Logout
            	<i class="fas fa-sign-out-alt"></i>
            </a>
          </div>
        </li>
        <%
        	}
        %>
      </ul>

    </nav>
    <div id="wrapper">
	  <%
	  	/* Phân quyền user và hiển thị thanh left_bar */
	  	boolean accessDashboard = true;
	  	boolean accessCategories = true;
	  	boolean accessNewses = true;
	  	boolean accessUsers = true;
	  	boolean accessSlides = true;
	  	boolean accessContacts = true;
	  	
	  	if (userLogin != null) {
	  		switch (userLogin.getUserPermissions().getName()) {
	  		case "admin": 
	  			break;
	  		case "editor":
	  			accessUsers = false;
	  			accessCategories = false;
	  			accessSlides = false;
	  			accessContacts = false;
	  			break;
	  		case "user":
	  			accessUsers = false;
	  			accessCategories = false;
	  			accessDashboard = false;
	  			accessNewses = false;
	  			accessSlides = false;
	  			accessContacts = false;
	  			break;
	  		default:
	  			break;
	  		}
	  	}
	  %>
      <!-- Sidebar -->
      <ul class="sidebar navbar-nav">
      	<% if (accessDashboard) { %>
        <li class="nav-item" id="dashboard">
          <a class="nav-link" href="<%=request.getContextPath()%>/admin">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Dashboard</span>
          </a>
        </li>
        <% }
        %>
        <% if (accessCategories) { %>
        <li class="nav-item" id="categories">
          <a class="nav-link" href="<%=request.getContextPath()%>/admin/categories">
            <i class="fas fa-sitemap"></i>
            <span>Categories</span></a>
        </li>
        <% } %>
        <% if (accessNewses) { %>
        <li class="nav-item" id="newses">
          <a class="nav-link" href="<%=request.getContextPath()%>/admin/newses">
            <i class="far fa-newspaper"></i>
            <span>Newes</span></a>
        </li>
        <% } %>
        <% if (accessUsers) { %>
        <li class="nav-item" id="users">
          <a class="nav-link" href="<%=request.getContextPath()%>/admin/users">
            <i class="far fa-user"></i>
            <span>Users</span></a>
        </li>
        <% } %>
        <% if (accessSlides) { %>
        <li class="nav-item" id="slides">
          <a class="nav-link" href="<%=request.getContextPath()%>/admin/slides">
            <i class="far fa-image"></i>
            <span>Slides</span></a>
        </li>
        <% } %>
        <% if (accessContacts) { %>
        <li class="nav-item" id="contacts">
          <a class="nav-link" href="<%=request.getContextPath()%>/admin/contacts">
            <i class="far fa-address-card"></i>
            <span>Contacts</span></a>
        </li>
        <% } %>
      </ul>