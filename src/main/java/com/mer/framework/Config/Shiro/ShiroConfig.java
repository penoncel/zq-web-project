package com.mer.framework.Config.Shiro;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.URLUtil;
import com.mer.common.Constant.Constant;
import com.mer.framework.Config.Redis.RedisConfig;
import com.mer.framework.Config.Shiro.Realm.UserRealm;
import com.mer.framework.Config.Shiro.RedisShiro.RedisCacheManager;
import com.mer.framework.Config.Shiro.RedisShiro.RedisSessionDAO;
import com.mer.framework.Config.Shiro.SessionListener.ShiroSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.*;

//https://blog.csdn.net/u011818862/article/details/106619710?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.channel_param#%E6%8E%A5%E5%8F%A3%E5%AE%89%E5%85%A8%E9%98%B2%E4%BB%80%E4%B9%88

//https://www.oschina.net/question/2556911_2268835

/**
 * @author zhoaqi 15701556037
 */
@SuppressWarnings("all")
@Configuration
@Slf4j
public class ShiroConfig {

    @Autowired
    RedisConfig redisConfig;

    /**
     * kickoutSessionFilter 同一个用户多设备登录限制
     */
    public KickoutSessionFilter kickoutSessionFilter(){
        KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //这里我们还是用之前shiro使用的ehcache实现的cacheManager()缓存管理
        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
        kickoutSessionFilter.setCacheManager(redisCacheManager());
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionFilter.setSessionManager(sessionManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
        kickoutSessionFilter.setKickoutAfter(Constant.KickoutAfter);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionFilter.setMaxSession(Constant.MaxSession);
        //被踢出后重定向到的地址；
        kickoutSessionFilter.setKickoutUrl(Constant.KickoutUrl);
        return kickoutSessionFilter;
    }

    /**
     * 注入shiro过滤器
     * ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。
     * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        log.info("###################################################################################### Init shiroFilter ##################################################################################");
        //安全事务管理器工厂类
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 配置shiro安全管理器 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //添加kickout认证
        LinkedHashMap<String, Filter> hashMap=new LinkedHashMap<String, Filter>();
        hashMap.put("kickout",kickoutSessionFilter());
        shiroFilterFactoryBean.setFilters(hashMap);

        //配置未登录时拦截到的路径
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //访问没有授权的资源
        shiroFilterFactoryBean.setUnauthorizedUrl("/4xx");

        //拦截器，配置访问权限
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/static/**","anon");//匿名访问静态资源
        filterChainDefinitionMap.put("/WebUser/goLogin", "kickout,anon");//匿名访问登入Api
        filterChainDefinitionMap.put("/captcha", "anon");//匿名访问登入Api
        filterChainDefinitionMap.put("/captchaValidateCode", "anon");//匿名访问登入Api
        filterChainDefinitionMap.put("/druid/*", "kickout,authc");//匿名访问登入Api
        filterChainDefinitionMap.put("/*", "kickout,authc");//需要认证才可以访问
        filterChainDefinitionMap.put("/**", "kickout,authc");//需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "kickout,authc");//需要认证才可以访问

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 在线用户监听
     * @return
     */
    @Bean
    public ShiroSessionListener sessionListener(){
        return new ShiroSessionListener();
    }

    /**
     *  配置自定义的权限登录器
     *  身份认证realm; (账号密码校验；权限等)
     *  @return
     * **/
    @Bean
    public UserRealm myShiroRealm(){
        UserRealm userRealm = new UserRealm();
        //使用自定义的CredentialsMatcher进行密码校验和输错次数限制
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }


    /**
     *  配置核心安全事务管理器 shiro 安全管理器设置 realm 认证和 ehcache 缓存管理
     *   @return
     * **/
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm（推荐放到最后，不然某些情况会不生效）
        securityManager.setRealm(myShiroRealm());
        log.info("###################################################################################### Init ShriroRealm #####################################################################################");
        //注入session管理器;
        securityManager.setSessionManager(sessionManager());
        log.info("###################################################################################### Init SessionManager ##################################################################################");
        return securityManager;
    }

    /**
     * 配置shiro redisManager
     * 不配置的话，默认redisManager访问的是127.0.0.1:6379的redis
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisConfig.getHost());
        redisManager.setPort(redisConfig.getPort());
        redisManager.setTimeout(redisConfig.getTimeout());
        redisManager.setPassword(redisConfig.getPassword());
        try{
            redisManager.setJedisPool(redisConfig.redisPoolFactory());
        }catch (Exception e){
            e.printStackTrace();
        }
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setExpire(Constant.RedisShiroKeyExpire);
        //更改redis用于缓存的前缀 重写后使用默认的
//        redisCacheManager.setKeyPrefix(RedisSessionKey.shiroRedisCachePrefix.getPrefix());
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        //更改redis用于session管理的前缀  重写后使用默认的
//        redisSessionDAO.setKeyPrefix(RedisSessionKey.shiroRedisSessionPrefix.getPrefix());
        redisSessionDAO.setRedisManager(redisManager());
        //设置 session 过期时间 redis session key 过期时间
        redisSessionDAO.setExpire(Constant.RedisShiroKeyExpire);
        Map map = new HashMap();
        map.put("KeyPrefix",redisSessionDAO.getKeyPrefix());
        map.put("Expire",redisSessionDAO.getExpire());
        log.warn("RedisSessionDAO params is "+map.toString());
        return redisSessionDAO;
    }

    /**
     * 自定义sessionManager
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        // 去掉 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);

        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        // 配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);


        //redis缓存
        sessionManager.setCacheManager(redisCacheManager());
        //先从缓存中获取session
        sessionManager.setSessionDAO(redisSessionDAO());
        //超时时间，默认30分钟不操作，当前sessionid就会过期；单位毫秒 设置全局session过期时间(毫秒)
        sessionManager.setGlobalSessionTimeout(Constant.sessionTimeout);// 1000 = 1 S    ， 一个小时没有操作 则session失效 ，继续操作 则顺延一小时
        //是否删除过期 session
        sessionManager.setDeleteInvalidSessions(Constant.DeleteInvalidSessions);
        //自定义cookie // true 启用自定义的SessionIdCookie
        sessionManager.setSessionIdCookieEnabled(Constant.SessionIdCookieEnabled);
        //是否定时检查session
        sessionManager.setSessionValidationSchedulerEnabled(Constant.SessionValidationSchedulerEnabled);
        Map map = new HashMap();
        map.put("GlobalSessionTimeout",sessionManager.getGlobalSessionTimeout());
        map.put("DeleteInvalidSessions",sessionManager.isDeleteInvalidSessions());
        map.put("SessionIdCookieEnabled",sessionManager.isSessionIdCookieEnabled());
        map.put("SessionValidationSchedulerEnabled",sessionManager.isSessionValidationSchedulerEnabled());
        log.warn("sessionManager params is "+map.toString());
        return sessionManager;
    }









    /**
     * 密码匹配规则
     * 密码验证器，凭证匹配器,由于我们的密码校验交给 Shiro 的 SimpleAuthenticationInfo 进行处理了
     * @return
     * **/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     *
     *  LifecycleBeanPostProcessor 用于在实现了Initializable 接口的 Shiro bean 初始化时调用 Initializable 接口回调，在实现了 Destroyable 接口的
     *
     *   Shiro bean销毁时调用 Destroyable接口回调。
     *   大概意思就是 使用@Configuration配置，会会在上下文初始化的时候强制的注入一些依赖。导致一下不可知的初始化。尤其是创建BeanPostProcessor 和
     *    BeanFactoryPostProcessor的时候（LifecycleBeanPostProcessor正是BeanPostProcessor 的子类）。应该讲这些创建Bean的方法前面加上static。让使用configuration的类在没有实例化的时候不会去过早的要求@Autowired和@Value
     *    进行注入。
     *    在创建LifecycleBeanPostProcessor的方法变为静态static方法,方可 获取 配置项
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

}
