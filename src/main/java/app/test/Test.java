package app.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import app.enums.GenEnum;

public class Test {
    private ConcurrentHashMap<Long,Object> locks = new ConcurrentHashMap<>();
    private Map<Long,String> map = new HashMap<>();
    public String getString(Long id) {
        Object lock = locks.computeIfAbsent(id, k->new Object());
        synchronized(lock){
            return null;
        }
    }
    public void putString(Long id,String str) {
        Object lock = locks.computeIfAbsent(id, k->new Object());
        synchronized(lock){
            map.put(id,str);
        }
    }
    public static void main(String...args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        GenEnum g = GenEnum.DB_TYPE_DOUBLE;
        Constructor[] carr = g.getClass().getDeclaredConstructors();
        carr[0].setAccessible(true);
        Object x = carr[0].newInstance("ABC","DEF");
        GenEnum y = (GenEnum)x;
        System.out.println(y.getDbType("pg"));
    }
}
