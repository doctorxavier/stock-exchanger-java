package com.arsios.exchange.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Map;

public final class Utilities {

	public static final int	MILLISECONDS	= 1000;
	public static final int	MINUTES			= 60;
	public static final int	SECONDS			= 60;
	public static final int	HOURS			= 24;
	public static final int	MONTHDAYS_V1	= 31;
	public static final int	MONTHDAYS_V2	= 32;
	public static final int	WEEKDAYS		= 7;

	private Utilities() {

	}

	public static String parseMilliseconds(long elapsed) {
		long elapsedAux = elapsed;
		int ms = (int) (elapsedAux % MILLISECONDS);
		elapsedAux /= MILLISECONDS;
		int seconds = (int) (elapsedAux % SECONDS);
		elapsedAux /= SECONDS;
		int minutes = (int) (elapsedAux % MINUTES);
		elapsedAux /= MINUTES;
		int hours = (int) (elapsedAux % HOURS);
		return String.format("%02d:%02d:%02d:%04d", hours, minutes, seconds, ms);
	}
	
	public static String buildQueryString(Map<String, String> args) throws UnsupportedEncodingException {
		String result = new String();
		for (String hashkey : args.keySet()) {
			if (result.length() > 0) {
				result += '&';
			}
			result += URLEncoder.encode(hashkey, "UTF-8") + "=" + URLEncoder.encode((String) args.get(hashkey), "UTF-8");
		}
		return result;
	}
	
	public static double formatBigDecimal(BigDecimal bigd, Integer precision) {
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(precision);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.DOWN);
		return Double.valueOf(formater.format(bigd.doubleValue()));
	}

}
