package com.wangcai.lottery.util;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtils {
	
	static MessageDigest getDigest(String algorithm){
		try{
			return MessageDigest.getInstance(algorithm); 
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private static MessageDigest getMd5Digest(){
		return getDigest("MD5");
	}

	public static byte[] md5(byte[] data){
		return getMd5Digest().digest(data);
	}


	public static String md5Hex(byte[] data){
		return Hex.encodeHexString(md5(data));
	}

	public static String toMosaic(Context context,String name,String idc){
		String b="";
		if(!name.trim().equals("")||!idc.trim().equals("")){
			b ="customer_name^"+name+"|"+"customer_idNumber^"+idc;
		}
		return b;
	}
}
