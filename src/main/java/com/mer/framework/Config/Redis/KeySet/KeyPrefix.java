package com.mer.framework.Config.Redis.KeySet;

/***
 * 设置key
 * */
public interface KeyPrefix {

	//设置过期时间
	 int expireSeconds();
	//前缀
	 String getPrefix();
	
}
