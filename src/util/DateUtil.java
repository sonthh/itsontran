package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtil {
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(getDateFormatDetail(date));
	}

	public static String getDateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy", Locale.US);
		return sdf.format(date);
	}
	
	public static String getDateFormatDetail(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy 'at' h:mm a", Locale.US);
		return sdf.format(date);
	}
	
	public static String getHowLongAgo(Date firstDay, Date secondDay) {
		String result = "";
		long secondsAgo = TimeUnit.MILLISECONDS.toSeconds(secondDay.getTime() - firstDay.getTime());
		if (secondsAgo < 60) {
			if (secondsAgo == 1)
				result = secondsAgo + " second ago.";
			else
				result = secondsAgo + " seconds ago.";
		} else {
			long minutesAgo = TimeUnit.MILLISECONDS.toMinutes(secondDay.getTime() - firstDay.getTime());
			if (minutesAgo < 60) {
				if (minutesAgo == 1)
					result = minutesAgo + " minute ago.";
				else
					result = minutesAgo + " minutes ago.";
			} else {
				long hoursAgo = TimeUnit.MILLISECONDS.toHours(secondDay.getTime() - firstDay.getTime());
				if (hoursAgo < 24) {
					if (hoursAgo == 1)
						result = hoursAgo + " hour ago.";
					else
						result = hoursAgo + " hours ago.";
				} else {
					long daysAgo = TimeUnit.MILLISECONDS.toDays(secondDay.getTime() - firstDay.getTime());
					if (daysAgo == 1)
						result = daysAgo + " day ago.";
					else
						result = daysAgo + " days ago.";
				}
			}
		}
		return result;
	}
}
