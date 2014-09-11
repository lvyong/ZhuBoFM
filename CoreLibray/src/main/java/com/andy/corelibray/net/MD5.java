package com.andy.corelibray.net;

import java.security.MessageDigest;

public class MD5 {

	public static String generateRegisterToken(String uuid) {
		byte[] REGISTER_TOKEN_MASK = "iStV".getBytes();
		byte[] data = uuid.getBytes();
		byte[] result = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			result[i] = (byte) (data[i] ^ REGISTER_TOKEN_MASK[i
					% REGISTER_TOKEN_MASK.length]);
		}
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(result);

			buf = new StringBuffer("");
			for (byte b : md.digest()) {
				int i = b & 0xff;
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return buf.toString();
	}
}
