package com.hutu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final String basePath;

    public WebSecurityConfigurer(WebEndpointProperties webEndpointProperties) {
        this.basePath = webEndpointProperties.getBasePath();
    }

    @Value("${management.access.iplist}")
    private String iplist;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //得到iplist列表
        String iprule = "";
        String[] splitAddress=iplist.split(",");
        for(String ip : splitAddress){
            if (iprule.equals("")) {
                iprule = "hasIpAddress('"+ip+"')";
            } else {
                iprule += " or hasIpAddress('"+ip+"')";
            }
        }
        String actuatorRule = "hasAnyRole('ADMIN','DEV') and ("+iprule+")";

        //login和logout
        http.formLogin()
                .defaultSuccessUrl(basePath)
                .failureUrl("/login-error.html")
                .permitAll()
                .and()
                .logout()
                .and()
                .httpBasic();
        //匹配的页面，符合限制才可访问
        http.authorizeRequests()
                .antMatchers(basePath+"/**").access(actuatorRule);
        //剩下的页面，允许访问
        http.authorizeRequests().anyRequest().permitAll();
    }

    /*@Autowired
    public  void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //添加两个账号用来做测试
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("lhdadmin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("ADMIN","USER")
                .and()
                .withUser("lhduser")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("USER");
    }*/
}