//$Id$
package server;
import app.exceptions.Xception;
import app.models.Account;
import app.utils.LoggerProvider;
import app.utils.PropLoader;
import redis.clients.jedis.Jedis;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import com.google.gson.Gson;
public class Cache {
    public static class AccountCache extends LinkedHashMap<Long, Account> {
        private static final long serialVersionUID = 1L;
		private final int capacity;

        public AccountCache() {
            super(20, 0.75f, true);
            this.capacity = 20;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Account> eldest) {
            return size() > capacity;
        }

        public Account getAccount(Long accountNumber) {
        	 return super.get(accountNumber);
        }

        public void putAccount(Long accountNumber, Account account) {
        	 super.put(accountNumber, account);        }
    } 
	private final static Map<Long, Object> locks = new ConcurrentHashMap<>();
	private Cache() {
		try {
			redisSocket =  PropLoader.getProp("redis_socket");
		}
		catch(Exception e){
			LoggerProvider.getLogger().log(Level.SEVERE,e.getMessage(),e);
		}
		if(redisSocket==null) {
			accountCache = new AccountCache();
		}
		else {
			String[] socks = redisSocket.split(":");
			this.jedis = new Jedis(socks[0],Integer.parseInt(socks[1]));
		}
	}
	private Jedis jedis;
	private static String redisSocket;
	private static Cache cache;
	private AccountCache accountCache;
	
	
	public static Cache getInstance() {
		if(cache==null) {
			synchronized(Cache.class) {
				if(cache==null) {
					return cache = new Cache();
				}
			}
		}
		return cache;
	}
	public Account getAccount(Long accountNumber) throws Xception {
		if(accountNumber==null) {
			throw new Xception("Account number cant be null");
		}
        Object lock = locks.computeIfAbsent(accountNumber, k -> new Object());
        synchronized (lock) {
    		if(redisSocket!=null) {
    			String json = jedis.get(accountNumber.toString());
    			Gson gson = new Gson();
    			Account account = gson.fromJson(json, Account.class);
    			return account;
    		}
    		else {
    			return accountCache.get(accountNumber);
    		}
        }
	}
	public void putAccount(Account account) throws Xception {
		Xception xception = account == null ? new Xception("Account is null") : (account.getAccountNumber() == null ? new Xception("AccountNumber is null") : null);
		if(xception!=null) {
			throw xception;
		}
        Object lock = locks.computeIfAbsent(account.getAccountNumber(), k -> new Object());
        synchronized (lock) {
    		if(redisSocket!=null) {
    			Gson gson = new Gson();
    			String json = gson.toJson(account);
    			jedis.set(account.getAccountNumber().toString(), json);
    			jedis.expire(account.getAccountNumber().toString(), 3600);
    		}
    		else {
    			accountCache.put(account.getAccountNumber(), account);
    		}
        }
	}
	
}
