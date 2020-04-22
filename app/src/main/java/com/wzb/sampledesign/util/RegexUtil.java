package com.wzb.sampledesign.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 2020/4/22
 * Author:Satsuki
 * Description:
 */
public class RegexUtil {
	static Pattern p;
	static Matcher m;
	public static boolean usernameVerify(String username){
		p = Pattern.compile("[a-zA-Z]{1}[a-zA-Z0-9_]{1,15}");
		m = p.matcher(username);
		return m.matches();
	}

	public static boolean passwordVerify(String password){
		p = Pattern.compile("[a-zA-Z0-9]{1,16}");
		m = p.matcher(password);
		return m.matches();
	}

	public static boolean emailVerify(String email){
//		p = Pattern.compile("[@]{1}[a-zA-Z0-9]+[.]+[a-z]+");
		p = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
		m = p.matcher(email);
		return m.matches();
	}

	public static boolean ageVerify(String age){
		p = Pattern.compile("^(?:[1-9][0-9]?|1[01][0-9]|120)$");
		m = p.matcher(age);
		return m.matches();
	}
}
