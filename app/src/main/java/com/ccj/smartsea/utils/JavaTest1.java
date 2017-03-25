package com.ccj.smartsea.utils;

import java.math.BigInteger;
import java.util.Arrays;

public class JavaTest1 {
	
	
	public static void main(String[] args) throws Exception {
					System.out.println("sssss");	
					String h = "10";
			         BigInteger srch = new BigInteger(h, 16);
			         System.out.println("十六进制字符串10000转为2进制后为"+srch.intValue());


			         int i = 89;
			         System.out.println("2.10进制转字节:" + HexUtils.int2Byte(i));
			          
			         byte[] b2 = new byte[]{(byte)0xFF, (byte)0x5F, (byte)0x6, (byte)0x5A};
			         System.out.println("3.字节数组转16进制字符串:" + HexUtils.bytes2HexString(b2));
			          
			         String s1 = new String("1DA47C");
			         System.out.println("4.16进制字符串转字节数组:" + Arrays.toString(HexUtils.hexString2Bytes(s1)));
			          
			         System.out.println("5.字节数组转字符串:" + HexUtils.bytes2String(b2));
			          
			         System.out.println("6.字符串转字节数组:" + Arrays.toString(HexUtils.string2Bytes(s1)));
			          
			         System.out.println("7.16进制字符串转字符串:" +HexUtils. hex2String(s1));
			          
			         String s2 = new String("Hello!");
			         System.out.println("8.字符串转16进制字符串:" + HexUtils.string2HexString(s2));
	
	}
}
