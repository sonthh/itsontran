/*
 * Javascript for front-end
 * Author: Internet
 * 
 */
(function($) {
  "use strict"; // Start of use strict

  // Toggle the side navigation
  $("#sidebarToggle").click(function(e) {
    e.preventDefault();
    $("body").toggleClass("sidebar-toggled");
    $(".sidebar").toggleClass("toggled");
  });

  // Prevent the content wrapper from scrolling when the fixed side navigation hovered over
  $('body.fixed-nav .sidebar').on('mousewheel DOMMouseScroll wheel', function(e) {
    if ($window.width() > 768) {
      var e0 = e.originalEvent,
        delta = e0.wheelDelta || -e0.detail;
      this.scrollTop += (delta < 0 ? 1 : -1) * 30;
      e.preventDefault();
    }
  });

  // Scroll to top button appear
  $(document).scroll(function() {
    var scrollDistance = $(this).scrollTop();
    if (scrollDistance > 100) {
      $('.scroll-to-top').fadeIn();
    } else {
      $('.scroll-to-top').fadeOut();
    }
  });

  // Smooth scrolling using jQuery easing
  $(document).on('click', 'a.scroll-to-top', function(event) {
    var $anchor = $(this);
    $('html, body').stop().animate({
      scrollTop: ($($anchor.attr('href')).offset().top)
    }, 1000, 'easeInOutExpo');
    event.preventDefault();
  });
  

})(jQuery); // End of use strict


/*
 * Javascript for front end
 * Author: itsontran.com
 */

$(document).ready(function() {
	/*$('table td ul li').hover(function() {
		console.log($(this).prev().css("display", "inline-block"));
	});*/
});


/*
 * Javascript for back-end
 * Author: itsontran.com
 */

var website = '';
//var website = '/shareit';
/* ajax change status user in admin */
function changeStatusUser(id, status) {
//	console.log(id + "-" + status);
	$.ajax({
		type: "post",
		/* doPost AdminActiveUserController */
	    url: website + "/admin/user/active",
	    data: {
	    	aid: id,
	    	astatus: status
	    },
	    dataType: "html",
	    success: function (response) {
	    	status = status == 1 ? 0 : 1;
	    	/* css for active/deactive button */
	        $('td.status span.status-user-' + id).toggleClass('active');
	        /* change onclick inline a tag (active/deactive) */
	        $('td.status span.status-user-' + id).attr('onClick','changeStatusUser(' + id + ',' + status + ')');
	    },
	    error: function() {
	    	console.log('lỗi ajax active user');
	    }
	});
};

/* ajax change status news in admin */
function changeStatusNews(id, status) {
	//console.log(id + "-" + status);
	$.ajax({
		type: "post",
		/* doPost AdminActiveNewsController */
		url: website + "/admin/news/active",
		data: {
			aid: id,
			astatus: status
		},
		dataType: "html",
		success: function (response) {
			status = status == 1 ? 0 : 1;
			/* css for active/deactive button */
			$('td.status span.status-news-' + id).toggleClass('active');
			/* change onclick inline a tag (active/deactive) */
			$('td.status span.status-news-' + id).attr('onClick','changeStatusNews(' + id + ',' + status + ')');
		},
		error: function() {
			console.log('lỗi ajax active user');
		}
	});
};

/* ajax change status slide in admin */
function changeStatusSlide(id, status) {
	$.ajax({
		type: "post",
		 /*doPost AdminActiveSlideController */
		url: website + "/admin/slide/active",
		data: {
			aid: id,
			astatus: status
		},
		dataType: "html",
		success: function (response) {
			status = status == 1 ? 0 : 1;
			/* css for active/deactive button */
			$('td.status span.status-slide-' + id).toggleClass('active');
			/* change onclick inline a tag (active/deactive)*/ 
			$('td.status span.status-slide-' + id).attr('onClick','changeStatusSlide(' + id + ',' + status + ')');
		},
		error: function() {
			console.log('lỗi ajax active slide');
		}
	});
};

/* ajax username exist when onchange input username in form login */
function checkUsernameExist() {
	var username = $('#inputUsername').val();
	$.ajax({
		type: "post",
		/* doPost AdminCheckExistUserController */
	    url: website + "/admin/user/checkusername",
	    data: {
	    	ausername: username
	    },
	    dataType: "html",
	    success: function (response) {
	    	/* response from doPost AdminCheckExistUserController | 1->exist username 2->not exist user*/
	    	if (response == 1) {
	    		$('#user-exist').html("<div class='alert alert-warning message'><i class='fas fa-times'></i>  Username is existed.</div>");
	    	} else {
	    		$('#user-exist').html('');
	    		return false;
	    	}
	    },
	    error: function() {
	    	console.log('lỗi check exist user');
	    }
	});
}

/* ajax when click user toggle button to show how long login ago */
var statusToggleUser = 1;
$('#userDropdown').click(function() {
	if (statusToggleUser == 1) {
		$.ajax({
			type: "post",
			/* doPost AuthHowLongAgoLoginController */
		    url: website + "/auth/how-long-ago-login",
		    data: {
		    },
		    dataType: "html",
		    success: function (response) {
		    	//console.log(response);
		    	/* doPost AuthHowLongAgoLoginController response ex: Login 10 hours ago. */
		    	$('#how-long-ago').html(response);
		    },
		    error: function() {
		    	console.log('lỗi check ajax lấy thời gian đã đăng nhập');
		    }
		});
	}
	statusToggleUser = statusToggleUser == 1 ? 0 : 1;
});

/* ajax change status commented in admin news */
function changeStatusCommentOfNews(commentId, newsId, status) {
	//console.log("active-deactive commment: " + commentId + "-" + newsId + "-" + status);
	$.ajax({
		type: "post",
		 /*doPost AdminActiveCommentNewsController */
		url: website + "/admin/news/comment/active",
		data: {
			acommentId: commentId,
			anewsId: newsId,
			astatus: status, 
		},
		dataType: "html",
		success: function (response) {
			//console.log('ajax active/deactive news successful');
			//console.log(response);
			var arTemp = response.split(";");
			//console.log(arTemp);
			//console.log(arTemp.length);
			status = status == 1 ? 0 : 1;
			for (var i = 0; i < arTemp.length; i++) {
			   //console.log(arTemp[i]);
			   
			   if (status == 0) {
				   //console.log("truong hop 1");
				   $('.status-comment-' + arTemp[i]).removeClass('active');
			   }
			   else { 
				   //console.log("truong hop 1");
				   $('.status-comment-' + arTemp[i]).addClass('active');
			   }
			   $('.status-comment-' + arTemp[i]).attr('onClick','changeStatusCommentOfNews(' + arTemp[i] + ',' + newsId + ',' + status + ')');
			}
		},
		error: function() {
			console.log('lỗi ajax active slide');
		}
	});
}
