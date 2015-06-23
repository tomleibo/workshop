package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static final String USER_ID_COOKIE_NAME = "userId";
	public static final String SUPER_USER_ID_COOKIE_NAME = "superUserId";
	public static final String FORUM_ID_COOKIE_NAME = "forumId";
	public static final String SUB_FORUM_ID_COOKIE_NAME = "subForumId";

	/**
	 * adds a cookie to the response.
	 * domain, path and isSecure are optional.
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int ttlSeconds, String domain, 
			String path, Boolean isSecure) throws IllegalArgumentException {
		Cookie cookie = new Cookie(name,value);
		cookie.setMaxAge(ttlSeconds);
		if (domain!=null){
			cookie.setDomain(domain);
		}
		if(path!=null) {
			cookie.setPath(path);
		}
		if (isSecure!=null) {
			cookie.setSecure(isSecure);
		}
		else {
			cookie.setSecure(false);
		}
		response.addCookie(cookie);
	}
	
	
	/**
	 * deletes a cookie with given key in the given request.
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String key) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(key)) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}

	/**
	 * deletes all cookies in the given request.
	 */
	public static void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
		}
	}


	
	/**
	 * returns the value of the cookie. 
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName) && cookie.getMaxAge() != 0) {
                    return cookie.getValue();
                }
            }
        }
		return null;
	}

	public static boolean changeCookieValue(HttpServletRequest request, HttpServletResponse response, String cookieName, String newValue) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(cookieName)) {
				cookie.setValue(newValue);
				if(cookie.getMaxAge() == 0)
					cookie.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(cookie);
				return true;
			}
		}

		return false;
	}

	/**
	 * adds a cookie to the response.
	 */
	public static void addInfiniteCookie(HttpServletResponse response, String name, String value) throws IllegalArgumentException {
		addCookie(response, name, value, Integer.MAX_VALUE, null, null, false);
	}

	public static String getUserCookieName(String forumId){
		return forumId+"_"+USER_ID_COOKIE_NAME;
	}

	public static String getUserCookieName(int forumId){
		return getUserCookieName(""+forumId);
	}
}
