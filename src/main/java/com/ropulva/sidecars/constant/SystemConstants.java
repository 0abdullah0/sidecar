package com.ropulva.sidecars.constant;

public class SystemConstants {

	private SystemConstants() {
	}

	public final static long TIME_TO_LIVE_CODE = 10L;

	public final static String SMS_PROVIDER_URL = "https://smsmisr.com/api/OTP/";

	public final static String SMS_TEMPLATE = "0f9217c9d760c1c0ed47b8afb5425708da7d98729016a8accfc14f9cc8d1ba83";

	public static String getOtpSms(String otpCode) {
		return "Your%20OTP%20is%20" + otpCode
				+ " to validate your login. Valid for 10 minutes".replaceAll("\\s+", "%20");
	}

}
