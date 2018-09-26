package com.cm.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ListDataMd5code {

	static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String md5sum(List list) throws NoSuchAlgorithmException {
		byte[] buf = list.toString().getBytes();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(buf);
		byte[] tmp = md5.digest();

		int j = tmp.length;
		char s[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = tmp[i];
			s[k++] = hexDigits[byte0 >>> 4 & 0xf];
			s[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(s);
	}
}
