package de.bht.comanche.logic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Utility for generating urls for gravatar. The providede urls
 * are used to retrieve icon from gravatar.
 * 
 * @author Simon Lischka
 *
 */
public class LgGravatarUtils {
	private final String PTJ_HASHPREFIX = "PTJRULES::WEWILLHASHYOU::THISISNOTANEMAIL::RAyyaXZZSADANnnnna";
	private final String GRAV_BASEURL = "http://www.gravatar.com/avatar/";
	private final String URL_QUES = "?";
	private final String GRAV_PIXELART = "d=retro";
	private final String GRAV_IDENTICON = "d=identicon";
	private final String MD5_ENC = "UTF-8";
	private final String MD5_ALG_NAME = "MD5";
	
	/**
	 * Retrieves an URL by group name. Adds a random hash prefix to ensure 
	 * we are not fetching other peoples icons.
	 * 
	 * @param name of user
	 * @return gravatar icon url
	 */
	protected String getGroupUrl(String name) {
		return GRAV_BASEURL
			+ generateMd5Hash(PTJ_HASHPREFIX + name) 
			+ URL_QUES 
			+ GRAV_IDENTICON;
	}
	
	/**
	 * Retrieves an URL for a user by email.
	 * 
	 * @param email of user
	 * @return gravatar icon url
	 */
	protected String getUserUrl(String email) {
		return GRAV_BASEURL
			+ generateMd5Hash(email) 
			+ URL_QUES 
			+ GRAV_PIXELART;
	}
	
	/**
	 * Algorithm to determine md5 hash for string
	 * @param inputstring String to be hashed
	 * @return md5 hash or empty string if error when generating
	 */
	private String generateMd5Hash(String inputstring) {
		try {
			final byte[] emailBytes = inputstring.getBytes(MD5_ENC);
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
}
