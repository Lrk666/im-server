package com.lrk.im.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.alibaba.fastjson.JSON;





public class StringRandomUtil extends StringUtils {

	//加密盐
	private static final String SLAT="COLA666666@#.";
	
	/** 
     * 校验银行卡卡号 
     */  
    public static boolean checkBankCard(String bankCard) {  
             if(bankCard.length() < 15 || bankCard.length() > 19) {
                 return false;
             }
             char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));  
             if(bit == 'N'){  
                 return false;  
             }  
             return bankCard.charAt(bankCard.length() - 1) == bit;  
    }  

    /** 
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位 
     * @param nonCheckCodeBankCard 
     * @return 
     */  
    public static char getBankCardCheckCode(String nonCheckCodeBankCard){  
        if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0  
                || !nonCheckCodeBankCard.matches("\\d+")) {  
            //如果传的不是数据返回N  
            return 'N';  
        }  
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();  
        int luhmSum = 0;  
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {  
            int k = chs[i] - '0';  
            if(j % 2 == 0) {  
                k *= 2;  
                k = k / 10 + k % 10;  
            }  
            luhmSum += k;             
        }  
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0'); 
    }
	
	/**
	 * 判断是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
	

	public static String get20Num() {
		// 生成20位纯数字字符串作为订单编号
		String numStr = "";
		String trandStr = String.valueOf((Math.random() * 9 + 1) * 100000);
		String dataStr = new SimpleDateFormat("yyyyMMddHHMMss").format(new Date());
		numStr = dataStr + trandStr.toString().substring(0, 6);
		return numStr;
	}

	public static String get6Num() {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	public static String encode(String str) {
		String encode = null;
		try {
			encode = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encode;
	}

	/**
	 * 获取UUID，去掉`-`的
	 * 
	 * @return {String}
	 * 
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 要求外部订单号必须唯一。
	 * 
	 * @return {String}
	 */
	public static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		key = key + System.currentTimeMillis();
		key = key.substring(0, 20);
		return key;
	}

	/**
	 * 将字符串中特定模式的字符转换成map中对应的值
	 * 
	 * use: format("my name is ${name}, and i like ${like}!", {"name":"L.cm",
	 * "like": "Java"})
	 * 
	 * @param s
	 *            需要转换的字符串
	 * @param map
	 *            转换所需的键值对集合
	 * @return {String}转换后的字符串
	 */
	public static String format(String s, Map<String, String> map) {
		StringBuilder sb = new StringBuilder((int) (s.length() * 1.5));
		int cursor = 0;
		for (int start, end; (start = s.indexOf("${", cursor)) != -1 && (end = s.indexOf('}', start)) != -1;) {
			sb.append(s.substring(cursor, start));
			String key = s.substring(start + 2, end);
			sb.append(map.get(StringUtils.trim(key)));
			cursor = end + 1;
		}
		sb.append(s.substring(cursor, s.length()));
		return sb.toString();
	}

	/**
	 * 字符串格式化
	 * 
	 * use: format("my name is {0}, and i like {1}!", "L.cm", "java")
	 * 
	 * int long use {0,number,#}
	 * 
	 * @param s
	 * @param args
	 * @return {String}转换后的字符串
	 */
	public static String format(String s, Object... args) {
		return MessageFormat.format(s, args);
	}

	/**
	 * 替换某个字符
	 * 
	 * @param str
	 * @param regex
	 * @param args
	 * @return {String}
	 */
	public static String replace(String str, String regex, String... args) {
		int length = args.length;
		for (int i = 0; i < length; i++) {
			str = str.replaceFirst(regex, args[i]);
		}
		return str;
	}

	 public static Map<String, String> BeanToMap(Object e){ 
		 Map<String, String> map=new HashMap<String, String>();
         Class cls = e.getClass();  
         Field[] fields = cls.getDeclaredFields();  
         for(int i=0; i<fields.length; i++){  
             Field f = fields[i];  
             f.setAccessible(true);  
             try {
            	 if (f.get(e) == null) {
					map.put(f.getName(), "");
				}else {
					map.put(f.getName(),f.get(e).toString());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}  
         } 
         return map;
     } 

	 
	/**
	 * 清理字符串，清理出某些不可见字符
	 * 
	 * @param txt
	 * @return {String}
	 */
	public static String cleanChars(String txt) {
		return txt.replaceAll("[ 　	`·•�\\f\\t\\v]", "");
	}

	// 随机字符串
	private static final String _INT = "0123456789";
	private static final String _STR = "abcdefghijklmnopqrstuvwxyz";
	private static final String _ALL = _INT + _STR;

	private static final Random RANDOM = new Random();

	/**
	 * 生成的随机数类型
	 */
	public static enum RandomType {
		INT, STRING, ALL;
	}

	/**
	 * 随机数生成
	 * 
	 * @param count
	 * @return {String}
	 */
	public static String random(int count, RandomType randomType) {
		if (count == 0)
			return "";
		if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		char[] buffer = new char[count];
		for (int i = 0; i < count; i++) {
			if (randomType.equals(RandomType.INT)) {
				buffer[i] = _INT.charAt(RANDOM.nextInt(_INT.length()));
			} else if (randomType.equals(RandomType.STRING)) {
				buffer[i] = _STR.charAt(RANDOM.nextInt(_STR.length()));
			} else {
				buffer[i] = _ALL.charAt(RANDOM.nextInt(_ALL.length()));
			}
		}
		return new String(buffer);
	}

	public static String passwordToMd5Util(String password) {
		SimpleHash simpleHash = new SimpleHash("md5",password,ByteSource.Util.bytes(SLAT), 2);
		return simpleHash.toString();
	}
	
	public static String getSha1(String str) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
	
	
}
