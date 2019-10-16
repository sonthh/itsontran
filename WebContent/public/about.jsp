<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/template/public/inc/header.jsp" %>
		
		<div class="site-main-container">
			<!-- Start top-post Area -->
			<section class="top-post-area pt-10">
				<div class="container no-padding">
					<div class="row">
						<div class="col-lg-12">
							<div class="hero-nav-area">
								<h1 class="text-white">About me</h1>
								<p class="text-white link-nav"><a href="<%=request.getContextPath()%>/">Home </a>  <span class="lnr lnr-arrow-right"></span><a href="<%=request.getContextPath()%>/about">About Me </a></p>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!-- End top-post Area -->
		</div>
		<!-- Start service Area -->
		<section class="service-area section-gap"  style="padding: 100px 0 40px 0">
			<div class="container">
				<div class="row">
					<div class="col-lg-4">
						<div class="single-service d-flex flex-row">
							<div class="icon">
								<span class="lnr lnr-sun"></span>
							</div>
							<div class="details">
								<a href="#">
									<h4>Front end</h4>
								</a>
								<p>
									Responsive, mobile-first development. JavaScript (jQuery, Bootstrap), HTML/CSS.
								</p>
							</div>
						</div>
					</div>
					<div class="col-lg-4">
						<div class="single-service d-flex flex-row">
							<div class="icon">
								<span class="lnr lnr-code"></span>
							</div>
							<div class="details">
								<a href="#">
									<h4>Back end</h4>
								</a>
								<p>
									0.5 years’ experience in core Java and J2EE (JSP/SERVLET), using Spring framework.
								</p>
							</div>
						</div>
					</div>
					<div class="col-lg-4">
						<div class="single-service d-flex flex-row">
							<div class="icon">
								<span class="lnr lnr-earth"></span>
							</div>
							<div class="details">
								<a href="#">
									<h4>Social skill</h4>
								</a>
								<p>
									Connecting with people, play sport with friends. Can team work and do you work under pressure.
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- End service Area -->
		
		<!-- Start info Area -->
		<!-- End info Area -->
		
		<!-- Start feedback Area -->
		<section class="feedback-area section-gap" id="feedback">
			<div class="container">
				<div class="row justify-content-center">
					<div class="col-md-12 pb-50 header-text text-center">
						<h1 class="mb-10">Sharing to this life become beautiful</h1>
						<p>
							programming so easy if you like it..
						</p>
					</div>
				</div>
				<div class="row feedback-contents justify-content-between align-items-center">
					<div class="col-lg-6 feedback-left">
						<div class="mn-accordion" id="accordion">
							<!--Accordion item-->
							<div class="accordion-item">
								<div class="accordion-heading">
									<h3>Infomation</h3>
									<div class="icon">
										<i class="lnr lnr-chevron-right"></i>
									</div>
								</div>
								<div class="accordion-content">
									<p>I am a student at Da Nang University of Science and Technology (DUT) in IT faculty. I like progamming and i write code everyday.</p>
								</div>
							</div>
							<!--Accordion item-->
							<!--Accordion item-->
							<div class="accordion-item">
								<div class="accordion-heading">
									<h3>Language</h3>
									<div class="icon">
										<i class="lnr lnr-chevron-right"></i>
									</div>
								</div>
								<div class="accordion-content">
									<p>I am studying Java backend and another for web develoment.</p>
								</div>
							</div>
							<!--Accordion item-->
							<!--Accordion item-->
							<div class="accordion-item">
								<div class="accordion-heading">
									<h3>Dream</h3>
									<div class="icon">
										<i class="lnr lnr-chevron-right"></i>
									</div>
								</div>
								<div class="accordion-content">
									<p>My dream is become web developer and work work work... to earn money to get married </p>
								</div>
							</div>
							<!--Accordion item-->
							<!--Accordion item-->
							<div class="accordion-item">
								<div class="accordion-heading">
									<h3>Content</h3>
									<div class="icon">
										<i class="lnr lnr-chevron-right"></i>
									</div>
								</div>
								<div class="accordion-content">
									<p>I will write about my knowledge by my heart - for communication </p>
								</div>
							</div>
							<!--Accordion item-->
						</div>
					</div>
					<div class="col-lg-5 feedback-right relative d-flex justify-content-center align-items-center">
						<div class="overlay overlay-bg"></div>
						<a class="play-btn" href="https://www.youtube.com/watch?v=3xAxSkE9jVI"><img class="img-fluid" src="<%=request.getContextPath()%>/template/public/img/play-btn.png" alt=""></a>
					</div>
				</div>
			</div>
		</section>
		<!-- End feedback Area -->
		
		<!-- Start testimonial Area -->
		<!-- End testimonial Area -->
		<!-- Start brands Area -->
		<!-- End brands Area -->
		<script src="<%=request.getContextPath()%>/template/public/js/vendor/jquery-2.2.4.min.js"></script>
		<script>
		$('title').html('About itsontran');
		</script>
		<%@ include file="/template/public/inc/footer.jsp" %>