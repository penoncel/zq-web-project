//package com.mer.framework.Config.Shiro;
//
//
//import lombok.extern.slf4j.Slf4j;
//import net.sf.ehcache.CacheException;
//import net.sf.ehcache.CacheManager;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.cache.ehcache.EhCacheManager;
//import org.apache.shiro.io.ResourceUtils;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.session.mgt.SessionManager;
//import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//import java.io.IOException;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//
////https://blog.csdn.net/u011818862/article/details/106619710?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.channel_param#%E6%8E%A5%E5%8F%A3%E5%AE%89%E5%85%A8%E9%98%B2%E4%BB%80%E4%B9%88
//
//
////https://www.oschina.net/question/2556911_2268835
//
///**
// * @author zhoaqi 15701556037
// */
//@SuppressWarnings("all")
//@Configuration
//@Slf4j
//public class ShiroConfigBF {
//
//    /**
//     *
//     * @描述：kickoutSessionFilter同一个用户多设备登录限制
//     * @创建人：wyait
//     * @创建时间：2018年4月24日 下午8:14:28
//     * @return
//     */
//    public KickoutSessionFilter kickoutSessionFilter(){
//        KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
//        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
//        //这里我们还是用之前shiro使用的ehcache实现的cacheManager()缓存管理
//        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
//        kickoutSessionFilter.setCacheManager(ehCacheManager());
//        //用于根据会话ID，获取会话进行踢出操作的；
//        kickoutSessionFilter.setSessionManager(sessionManager());
//        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
//        kickoutSessionFilter.setKickoutAfter(false);
//        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
//        kickoutSessionFilter.setMaxSession(1);
//        //被踢出后重定向到的地址；
//        kickoutSessionFilter.setKickoutUrl("/login?kickout=1");
//        return kickoutSessionFilter;
//    }
//
//    /**
//     * 注入shiro过滤器
//     * ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。
//     * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
//     * @return
//     */
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
//        log.info("########################################################################################注入shiro过滤器################################################################################");
//        //安全事务管理器工厂类
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        // 配置shiro安全管理器 SecurityManager
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//
//        //添加kickout认证
//        LinkedHashMap<String, Filter> hashMap=new LinkedHashMap<String, Filter>();
//        hashMap.put("kickout",kickoutSessionFilter());
//        shiroFilterFactoryBean.setFilters(hashMap);
//
//        //配置未登录时拦截到的路径
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        //访问没有授权的资源
//        shiroFilterFactoryBean.setUnauthorizedUrl("/4xx");
//
//        //拦截器，配置访问权限
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/static/**","anon");//匿名访问静态资源
//        filterChainDefinitionMap.put("/WebUser/goLogin", "kickout,anon");//匿名访问登入Api
//        filterChainDefinitionMap.put("/captcha", "anon");//匿名访问登入Api
//        filterChainDefinitionMap.put("/captchaValidateCode", "anon");//匿名访问登入Api
//        filterChainDefinitionMap.put("/druid/*", "anon");//匿名访问登入Api
//        filterChainDefinitionMap.put("/*", "kickout,authc");//需要认证才可以访问
//        filterChainDefinitionMap.put("/**", "kickout,authc");//需要认证才可以访问
//        filterChainDefinitionMap.put("/*.*", "kickout,authc");//需要认证才可以访问
//
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//    }
//
//    /**
//     *  配置核心安全事务管理器
//     *   @return
//     * **/
//    @Bean
//    public SecurityManager securityManager(){
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        log.info("######################################################################################加载安全控件#####################################################################################");
//        // 设置记住我
////        securityManager.setRememberMeManager(rememberMeManager());
//        //注入session管理器;
//        securityManager.setSessionManager(sessionManager());
//        //注入ehcache缓存管理器;
//        securityManager.setCacheManager(ehCacheManager());
//        //设置realm（推荐放到最后，不然某些情况会不生效）
//        securityManager.setRealm(myShiroRealm());
//        return securityManager;
//    }
//
//    /**
//     *  配置自定义的权限登录器
//     *  身份认证realm; (账号密码校验；权限等)
//     *  @return
//     * **/
//    @Bean
//    public UserRealm myShiroRealm(){
//        UserRealm userRealm = new UserRealm();
//        //使用自定义的CredentialsMatcher进行密码校验和输错次数限制
//        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        return userRealm;
//    }
//
//    /**
//     * ehcache缓存管理器；shiro整合ehcache：
//     * 通过安全管理器：securityManager
//     * 单例的cache防止热部署重启失败
//     * @return EhCacheManager
//     */
//    @Bean(name = "ehCacheManager")
//    public EhCacheManager ehCacheManager(){
//        log.info("######################################################################################ehcache缓存#####################################################################################");
//        EhCacheManager ehcache = new EhCacheManager();
//        CacheManager cacheManager = CacheManager.getCacheManager("shiro");
//        if(cacheManager == null){
//            try {
//                cacheManager = CacheManager.create(ResourceUtils.getInputStreamForPath("classpath:cfg/ehcache.xml"));
//            } catch (CacheException | IOException e) {
//                e.printStackTrace();
//            }
//        }
//        ehcache.setCacheManager(cacheManager);
//        return ehcache;
//    }
//
//
//    /**
//     * cookie对象，通过登录界面的记住我
//     * @return
//     */
////    @Bean
////    public SimpleCookie rememberMeCookie(){
////        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
////        SimpleCookie cookie = new SimpleCookie("rememberMe");
////        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
////        //setcookie()的第七个参数
////        //设为true后，只能通过http访问，javascript无法访问
////        //防止xss读取cookie
////        cookie.setHttpOnly(true);
////        //记住我有效期长达30天
////        cookie.setMaxAge(2592000);
////        return cookie;
////    }
//
//    /**
//     * rememberMeManager 管理器
//     * @return
//     */
////    @Bean
////    public CookieRememberMeManager rememberMeManager(){
////        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
////        rememberMeManager.setCookie(rememberMeCookie());
////        rememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
////        return rememberMeManager;
////    }
//
//    /**
//     * EnterpriseCacheSessionDAO shiro sessionDao层的实现；
//     * 提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
//     */
//    @Bean
//    public EnterpriseCacheSessionDAO enterCacheSessionDAO() {
//        EnterpriseCacheSessionDAO enterCacheSessionDAO = new EnterpriseCacheSessionDAO();
//        //添加缓存管理器
//        enterCacheSessionDAO.setCacheManager(ehCacheManager());
//        //添加ehcache活跃缓存名称（必须和ehcache缓存名称一致）
//        enterCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
//        return enterCacheSessionDAO;
//    }
//
//    /**
//     * FormAuthenticationFilter 过滤器 过滤记住我
//     * @return
//     */
////    @Bean
////    public FormAuthenticationFilter formAuthenticationFilter(){
////        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
////        //对应前端的checkbox的name = rememberMe
////        formAuthenticationFilter.setRememberMeParam("rememberMe");
////        return formAuthenticationFilter;
////    }
//
//    /**
//     * 密码匹配规则
//     * 密码验证器，凭证匹配器,由于我们的密码校验交给 Shiro 的 SimpleAuthenticationInfo 进行处理了
//     * @return
//     * **/
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher(){
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
//        return hashedCredentialsMatcher;
//    }
//
//    /**
//     * 自定义sessionManager
//     * @return
//     * **/
//    @Bean
//    public SessionManager sessionManager(){
//        CustomSessionManager customSessionManager = new CustomSessionManager();
//        //超时时间，默认30分钟不操作，当前sessionid就会过期；单位毫秒
//        customSessionManager.setGlobalSessionTimeout(  4*60 * 60 * 1000);//session过期时间
//        customSessionManager.setDeleteInvalidSessions(true);//是否删除过期session
//        customSessionManager.setSessionDAO(enterCacheSessionDAO());
//        return customSessionManager;
//    }
//
//
//
//    /**
//     *  开启shiro aop注解支持.
//     *  使用代理方式;所以需要开启代码支持;
//     * @param securityManager
//     * @return
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//
//    /**
//     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
//     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
//     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
//     * @return
//     */
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }
//
//
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
//        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(securityManager());
//        return advisor;
//    }
//
//
//    //  缓存配置
//    //shiro自带的 MemoryConstrainedCacheManager 作缓存
//    // 但是只能用于本机，在集群时就无法使用，需要使用ehcache
////    @Bean(name = "cacheManager")
////    public CacheManager cacheManager() {
////        MemoryConstrainedCacheManager cacheManager=new MemoryConstrainedCacheManager();//使用内存缓存
////        return cacheManager;
////    }
//
//
//    //开启cglib代理 自动代理所有的advisor
////    @Bean
////    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
////        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
////        advisorAutoProxyCreator.setProxyTargetClass(true);
////        return advisorAutoProxyCreator;
////    }
//
//
//}
