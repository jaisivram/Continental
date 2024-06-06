package server;
public class Local {
    private static ThreadLocal<Long> threadLocalUserId = new ThreadLocal<>();
    private static ThreadLocal<String> threadLocalAPI = new ThreadLocal<>();

    // Set the userid in the ThreadLocal
    public static void setUser(Long userId) {
        threadLocalUserId.set(userId);
    }

    // Get the userid from the ThreadLocal
    public static Long getUser() {
        return threadLocalUserId.get()==null? -1 : threadLocalUserId.get();
    }
    public static void setApi(String api) {
    	threadLocalAPI.set(api);
    }
    public static String getApi() {
        return threadLocalAPI.get()==null? ""  : threadLocalAPI.get();
    }
}
