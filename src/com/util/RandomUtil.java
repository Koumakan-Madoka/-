package com.util;
public final class RandomUtil {

	/** 数字. */
	public static final String NUMBER_ALL = "0123456789";
	/** 数字.去掉混淆的 01*/
	public static final String NUMBER_ALL_LACK = "23456789";
	/** 小写字母. */
	public static final String LOWER_LETTER = "abcdefghijklmnopqrstuvwxyz";
	/** 小写字母. 去掉混淆的 lo*/
	public static final String LOWER_LETTER_LACK = "abcdefghijkmnpqrstuvwxyz";
	/** 大写字母 */
	public static final String UPPER_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/** 大写字母  去掉混淆  IO*/
	public static final String UPPER_LETTER_LACK = "ABCDEFGHJKLMNPQRSTUVWXYZ";
	/** 特殊字符 */
	public static final String SPECIAL_Str = ";!#:.,";

	/*
	 * 获取随机字符串（纯数字）
	 */
	public static String getRadomStringByN(int len)
	{
		return getRadomString(NUMBER_ALL,len);
	}
	/*
	 * 获取随机字符串（小写字母）
	 */
	public static String getRadomStringByL(int len)
	{
		return getRadomString(LOWER_LETTER,len);
	}
	/*
	 * 获取随机字符串（大写字母）
	 */
	public static String getRadomStringByU(int len)
	{
		return getRadomString(UPPER_LETTER,len);
	}
	/*
	 * 获取随机字符串（小写字母+数字）
	 */
	public static String getRadomStringByLN(int len)
	{
		return getRadomString(LOWER_LETTER+NUMBER_ALL,len);
	}
	/*
	 * 获取随机字符串（小写字母+数字）（去掉混淆字符o0l1）
	 */
	public static String getRadomStringByLN2(int len)
	{
		return getRadomString(LOWER_LETTER_LACK+NUMBER_ALL_LACK,len);
	}
	/*
	 * 获取随机字符串（大写字母+数字）
	 */
	public static String getRadomStringByUN(int len)
	{
		return getRadomString(UPPER_LETTER+NUMBER_ALL,len);
	}
	/*
	 * 获取随机字符串（大写字母+数字）去掉混淆字符（0O）
	 */
	public static String getRadomStringByUN2(int len)
	{
		return getRadomString(UPPER_LETTER_LACK+NUMBER_ALL_LACK,len);
	}
	/*
	 * 获取随机字符串（大、小写字母+数字）
	 */
	public static String getRadomStringByULN(int len)
	{
		String str = UPPER_LETTER+NUMBER_ALL+LOWER_LETTER;
		return getRadomString(str,len);
	}
	/*
	 * 获取随机字符串（大、小写字母+数字）
	 */
	public static String getRadomStringByULN2(int len)
	{
		String str = UPPER_LETTER_LACK+NUMBER_ALL_LACK+LOWER_LETTER_LACK;
		return getRadomString(str,len);
	}
	
	private static String getRadomString(String strIn,int len){
		String str = "";
		char[] m = strIn.toCharArray();
		int forI = m.length;
		for (int i = 0; i < len; i++) {
			char c=m[(int)(Math.random()*forI)];
			str=str+c;
		}
		return str;
	}
}
