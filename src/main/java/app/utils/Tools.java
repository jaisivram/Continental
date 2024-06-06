package app.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.exceptions.Xception;
public class Tools {
	private static Logger logger = LoggerProvider.getLogger();
	public static void nullCheck(Object obj) throws Xception {
		if(obj==null) {
			throw new Xception("Cant be null");
		}
	}
	public static void nullCheck(Object obj,String message) throws Xception {
		if(obj==null) {
			throw new Xception(message);
		}
	}
	public static ZonedDateTime l2Zdt(long time, ZoneId zone) throws Xception {
		nullCheck(zone);
		Instant instant = Instant.ofEpochMilli(time);
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		return zdt;
	}
	public static Object caster(Class<?> fieldType, Object object) throws Xception {
	    try {
	        Method parseMethod = null;
	        try {
	            parseMethod = fieldType.getDeclaredMethod("parse" + fieldType.getSimpleName(), String.class);
	            Object casted = parseMethod.invoke(null, object.toString());
	            return casted;
	        } catch (NoSuchMethodException e) {
	            Object casted = fieldType.cast(object);
	            return casted;
	        }
	    } catch (Exception e) {
	        throw new Xception(e);
	    }
	}
	public static String hasher(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		}
		catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE,"no hash algo found",e);
		}
		StringBuilder sb = new StringBuilder();
		md.update(str.getBytes());
		byte[] bytes = md.digest(str.getBytes());
		for(byte b : bytes) {
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String genStr(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

	/*
	 * public static Map<String, Object> pojoToMap(Object pojo) throws Xception {
	 * nullCheck(pojo); try { Map<String, Object> resMap = new HashMap<>(); Class<?>
	 * pojoClass = pojo.getClass(); while (pojoClass != null) { Field[] fields =
	 * pojoClass.getDeclaredFields(); for (Field field : fields) {
	 * field.setAccessible(true); resMap.put(field.getName().toLowerCase(),
	 * field.get(pojo)); } pojoClass = pojoClass.getSuperclass(); } return resMap; }
	 * catch (IllegalAccessException e) { throw new Xception(e); } }
	 */
    public static Map<String,Object> pojoToMap(Object pojo,String depth) throws Xception {
    	nullCheck(pojo);
    	try {
    		Map<String,Object> resMap = new HashMap<>();
    		Class<?> pojoClass = pojo.getClass();
    		if(depth.equals("parent")) {
    			pojoClass = pojoClass.getSuperclass();
    		}
    		while(pojoClass!=null) {
        		Method[] methods = pojoClass.getDeclaredMethods();
        		for(Method method : methods) {
                	if(method.getName().startsWith("get")) {
                		String key =method.getName().substring(3).toLowerCase();
                		Object val = method.invoke(pojo);
                		String value = val==null? null : val.toString();
                		resMap.put(key, value);
                	}
                }
        		if(depth.equals("child")) {
        			pojoClass = pojoClass.getSuperclass();
        			depth="";
        		}
        		else {
        			pojoClass=null;
        		}
    		}
            return resMap;

    	}
    	catch(Exception e) {
    		throw new Xception(e);
    	}
    }

	/*
	 * public static Map<String, Object> properPojoToMap(Object pojo) throws
	 * Xception { nullCheck(pojo); try { Map<String, Object> resMap = new
	 * HashMap<>(); Class<?> pojoClass = pojo.getClass(); Method[] methods =
	 * pojoClass.getDeclaredMethods(); for(Method method : methods) {
	 * if(method.getName().startsWith("get")) { String key
	 * =method.getName().substring(3).toLowerCase(); Object val =
	 * method.invoke(pojo); String value = val==null? null : val.toString();
	 * resMap.put(key, value); } } return resMap; } catch (IllegalAccessException |
	 * IllegalArgumentException | InvocationTargetException e) { throw new
	 * Xception(e); } } public static Map<String, Object> altPojoToMap(Object pojo)
	 * throws Xception { nullCheck(pojo); try { Map<String, Object> resMap = new
	 * HashMap<>(); Class<?> pojoClass = pojo.getClass(); Field[] fields =
	 * pojoClass.getDeclaredFields(); for (Field field : fields) {
	 * field.setAccessible(true); resMap.put(field.getName().toLowerCase(),
	 * field.get(pojo)); } return resMap; } catch (IllegalAccessException e) { throw
	 * new Xception(e); } }
	 */
    public static Object mapToPojo(Map<String, Object> map, Object pojo) throws Xception {
        try {
            nullCheck(map);
            nullCheck(pojo);
            Class<?> pojoClass = pojo.getClass();
            while (pojoClass != null) {
                Field[] fields = pojoClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    Class<?> fieldType = field.getType();
                    Object value = map.get(fieldName.toLowerCase());
                    if (value != null) {
                        pojoClass.getDeclaredMethod(setterName, fieldType).invoke(pojo, caster(fieldType,value));
                    }
                }
                pojoClass = pojoClass.getSuperclass();
            }
            return pojo;
        } catch (Exception e) {
            throw new Xception(e);
        }
    }

	public static void logInfo(Object obj) throws Xception {
		nullCheck(obj);
	}
}
