package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtil {

	public static String md5(String str) {
		MessageDigest md;
		String result = "";
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			BigInteger bi = new BigInteger(1, md.digest());

			result = bi.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String makeSlug(String title) {
		String slug = Normalizer.normalize(title, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		slug = pattern.matcher(slug).replaceAll("");
		slug = slug.toLowerCase();
		// Thay đ thành d
		slug = slug.replaceAll("đ", "d");
		// Xóa các ký tự đặt biệt
		slug = slug.replaceAll("([^0-9a-z-\\s])", "");
		// Thay space thành dấu gạch ngang
		slug = slug.replaceAll("[\\s]", "-");
		// Đổi nhiều ký tự gạch ngang liên tiếp thành 1 ký tự gạch ngang
		slug = slug.replaceAll("(-+)", "-");
		// Xóa các ký tự gạch ngang ở đầu và cuối
		slug = slug.replaceAll("^-+", "");
		slug = slug.replaceAll("-+$", "");
		return slug;
	}

	// lấy chư mà giới hạn số lượng chữ mà ko bị cắt ngang chữ
	public static String getText(String str, int totalChar) {
		if (totalChar > str.length()) {
			return str;
		}
		String result = "";
		try {
			int endIndex = str.lastIndexOf(" ", totalChar);
			result = str.substring(0, endIndex);
		} catch (Exception e) {
			System.out.println("Lỗi xử lí rút gọn text");
		}
		return result + " ...";
	}

	public static void main(String[] args) {
		// String str1 = "“Nhớ…tiếng mưa rơi ngày xưa lúc đôi ta còn nhau, khi tình yêu…
		// bắt đầu…….”\r\n" +
		// "Những ca từ quen thuộc của ngày nào bổng vang lên giữa một buổi chiều mưa
		// nhẹ rơi…Đã từ rất lâu rồi tôi mới được nghe lại bài hát này. Bài hát khiến
		// tôi nhớ về kỷ niệm một thời mà tôi cứ nghỡ như chuyện mới vừa xãy ra hôm qua
		// vậy…!!!.";
		// System.out.println(getText(str1, 30));
		/*
		 * String str = " tran  huu tran tran hong  son"; int pos = 0;
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		java.util.Date date = new java.util.Date();
		System.out.println(sdf.format(date));
	}

}
