package com.ropulva.sidecars.constant;

public class SystemConstants {

	private SystemConstants() {
	}

	public final static long TIME_TO_LIVE_CODE = 10L;

	public final static String SMS_PROVIDER_URL = "https://smsmisr.com/api/OTP/";

	public final static String SMS_TEMPLATE = "e83faf6025ec41d0f40256d2812629f5fa9291d05c8322f31eea834302501da8";

	public static String getOtpSms(String otpCode) {
		return "Your%20OTP%20is%20" + otpCode
				+ " to validate your login. Valid for 10 minutes".replaceAll("\\s+", "%20");
	}

}
