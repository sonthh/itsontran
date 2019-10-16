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
								<h1 class="text-white">Contact Us</h1>
								<p class="text-white link-nav"><a href="index.html">Home </a>  <span class="lnr lnr-arrow-right"></span><a href="contact.html">Contact Us </a></p>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!-- End top-post Area -->
			<!-- Start contact-page Area -->
			<section class="contact-page-area pt-50 pb-120">
				<div class="container">
					<div class="row contact-wrap">
						
						<!-- insert google map -->
						<!-- <div class="map-wrap" style="width:100%; height: 445px;" id="map"></div> -->
						
						<div class="col-lg-3 d-flex flex-column address-wrap">
							<div class="single-contact-address d-flex flex-row">
								<div class="icon">
									<span class="lnr lnr-home"></span>
								</div>
								<div class="contact-details">
									<h5>Da Nang, Viet Nam</h5>
									<p>
										52/7, Nguyen Luong Bang
									</p>
								</div>
							</div>
							<div class="single-contact-address d-flex flex-row">
								<div class="icon">
									<span class="lnr lnr-phone-handset"></span>
								</div>
								<div class="contact-details">
									<h5>+84 162 705 3245</h5>
									<p>Mon to Fri 7am to 7 pm</p>
								</div>
							</div>
							<div class="single-contact-address d-flex flex-row">
								<div class="icon">
									<span class="lnr lnr-envelope"></span>
								</div>
								<div class="contact-details">
									<h5>itsontran@gmail.com</h5>
									<p>Send us your query</p>
								</div>
							</div>
						</div>
						<div class="col-lg-9">
							<form class="form-area contact-form text-right" id="myForm" action="mail.php" method="post">
								<div class="row">
									<div class="col-lg-6">
										<input name="name" placeholder="Enter your name" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Enter your name'" class="common-input mb-20 form-control" required="required" type="text">
										
										<input name="email" placeholder="Enter email address" pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,63}$" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Enter email address'" class="common-input mb-20 form-control" required="required" type="email">
										<input name="subject" placeholder="Enter subject" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Enter subject'" class="common-input mb-20 form-control" required="required" type="text">
									</div>
									<div class="col-lg-6">
										<textarea name="message" class="common-textarea form-control" placeholder="Enter Messege" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Enter Messege'" required="required"></textarea>
									</div>
									<div class="col-lg-12">
										<div class="alert-msg" style="text-align: left;"></div>
										<button class="primary-btn primary" style="float: right;">Send Message</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</section>
			<!-- End contact-page Area -->
		</div>
		<script src="<%=request.getContextPath()%>/template/public/js/vendor/jquery-2.2.4.min.js"></script>
		<script>
		$('title').html('Contact itsontran');
		</script>
		<%@ include file="/template/public/inc/footer.jsp" %>