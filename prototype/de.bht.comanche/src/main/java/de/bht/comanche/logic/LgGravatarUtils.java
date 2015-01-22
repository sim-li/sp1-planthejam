package de.bht.comanche.logic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LgGravatarUtils {
	private final String PTJ_HASHPREFIX = "PTJRULES::WEWILLHASHYOU::THISISNOTANEMAIL::RAyyaXZZSADANnnnna";
	private final String GRAV_BASEURL = "http://www.gravatar.com/avatar/";
	private final String URL_QUES = "?";
	private final String GRAV_PIXELART = "d=retro";
	private final String GRAV_IDENTICON = "d=identicon";
	private final String MD5_ENC = "UTF-8";
	private final String MD5_ALG_NAME = "MD5";
	
	protected String getGroupUrl(String name) {
		return GRAV_BASEURL
			+ generateMd5Hash(PTJ_HASHPREFIX + name) 
			+ URL_QUES 
			+ GRAV_IDENTICON;
	}
	
	protected String getUserUrl(String email) {
		return GRAV_BASEURL
			+ generateMd5Hash(email) 
			+ URL_QUES 
			+ GRAV_PIXELART;
	}
	
	private String generateMd5Hash(String email) {
		try {
			final byte[] emailBytes = email.getBytes(MD5_ENC);
			final MessageDigest md = MessageDigest.getInstance(MD5_ALG_NAME);
			final byte[] digest = md.digest(emailBytes);
			StringBuilder md5Message = new StringBuilder();
			for (byte b : digest) {
				String conv = Integer.toString(b & 0xFF, 16);
				while (conv.length() < 2) {
					conv = "0" + conv;
				}
				md5Message.append(conv);
			}
			return md5Message.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return "";
	}
	
	public static void main (String[] args) {
		System.out.println((new LgGravatarUtils()).generateMd5Hash("simon@a-studios.org"));
	}
}
