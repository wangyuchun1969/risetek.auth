package com.risetek.auth.server.shiro;

import javax.servlet.ServletContext;

import org.apache.shiro.guice.web.ShiroWebModule;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class MyShiroWebModule extends ShiroWebModule {
    public static final Key<MyAuthenticationFilter> MyAUTHC = Key.get(MyAuthenticationFilter.class);

	@Inject
    public MyShiroWebModule(ServletContext sc) {
        super(sc);
    }

    @SuppressWarnings("unchecked")
	protected void configureShiroWeb() {
    	// 使用本地方法进行用户验证和授权。
    	bindRealm().to(MyAuthorizingRealm.class).asEagerSingleton();
    	
        bindConstant().annotatedWith(Names.named("shiro.globalSessionTimeout")).to(30000L);
        
        // 静态页面信息都可以访问
        addFilterChain("/risetek/**", ANON);

        // OAuth
        addFilterChain("/oath/**", ANON);
        
        // 登录和授权
        addFilterChain("/dispatch/LogInOutAction", ANON);
        // 获取服务端认证授权信息
        addFilterChain("/dispatch/AuthorityAction", ANON);

        // 提供home页面的信息服务
        addFilterChain("/dispatch/DeviceOnlineEntityAction", ANON);
        addFilterChain("/dispatch/DropEntityAction", ANON);

        // 授权登录Action
        addFilterChain("/dispatch/OpenAuthAction", ANON);
        
        // TODO: 暂时性取消安全审核?
//      addFilterChain("/dispatch/*", ANON);
//        addFilterChain("/dispatch/*", ROLES, config(PERMS, "no"));
        addFilterChain("/dispatch/**", MyAUTHC);
    }
}
