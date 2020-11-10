package com.hutu.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * WebSecurityConfigurer
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	private final String adminContextPath;

	public WebSecurityConfigurer(AdminServerProperties adminServerProperties) {
		this.adminContextPath = adminServerProperties.getContextPath();
	}

	@Value("${spring.boot.admin.access.iplist}")
	private String iplist;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//得到iplist列表
		String iprule = "";
		//hasIpAddress('10.0.0.0/16') or hasIpAddress('127.0.0.1/32')
		String[] splitAddress=iplist.split(",");
		for(String ip : splitAddress){
			if (iprule.equals("")) {
				iprule = "hasIpAddress('"+ip+"')";
			} else {
				iprule += " or hasIpAddress('"+ip+"')";
			}
		}
		String adminRule = "hasAnyRole('ADMIN','DEV') and ("+iprule+")";
		//login和logout
		http.formLogin()
				.loginPage(adminContextPath+"/login")
				.defaultSuccessUrl(adminContextPath+"/wallboard")
				.failureUrl("/login-error.html")
				.permitAll()
				.and()
				.logout().logoutUrl(adminContextPath+"/logout").permitAll()
				.and()
				.httpBasic();
		http.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.ignoringAntMatchers(
						adminContextPath+"/**",
						"/actuator/**"
				);
		//匹配的页面，符合限制才可访问
		http.authorizeRequests()
				.antMatchers(adminContextPath+"/login/**",adminContextPath+"/assets/**").access(iprule)
				.antMatchers(adminContextPath+"/**").access(adminRule);
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