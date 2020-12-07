package com.mer.framework.Config.Redis.KeySet;

/**
 * redis key设置
 * 时间 和 前缀
 * */
public abstract class BasePrefix implements KeyPrefix{
	
	private int expireSeconds;
	
	private String prefix;


	public BasePrefix(String prefix) {//0代表永不过期
		this(0, prefix);
	}

	public BasePrefix( int expireSeconds, String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	
	public int expireSeconds() {//默认0代表永不过期
		return expireSeconds;
	}

	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className+":" + prefix;
	}

}
