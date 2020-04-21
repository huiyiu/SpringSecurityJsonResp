package com.cothink.demo.config;

import com.cothink.demo.service.UserDetailService;
import com.cothink.demo.vo.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * EnableWebSecurity注解使得SpringMVC集成了Spring Security的web安全支持
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /***注入我们自己的登录逻辑验证器AuthenticationProvider*/
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    FuryAuthSuccessHandler furyAuthSuccessHandler;
    @Autowired
    FuryAuthFailureHandler furyAuthFailureHandler;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里可启用我们自己的登陆验证逻辑
        //auth.authenticationProvider(authenticationProvider);
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
    /**
     * 权限配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置拦截规则
        http.addFilterAt(customAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                //登录拦截，用户注册，不拦截
                .antMatchers("/","/user/register").permitAll()
                .anyRequest().authenticated()
                //这里必须要写formLogin()，不然原有的UsernamePasswordAuthenticationFilter不会出现，也就无法配置我们重新的UsernamePasswordAuthenticationFilter
                .and().formLogin().loginPage("/").usernameParameter("cardId").passwordParameter("password")
                //登录成功
                .successHandler(furyAuthSuccessHandler)
                //登录失败处理
                .failureHandler(furyAuthFailureHandler)
                .and().csrf().disable()
                //未登录处理类
                .exceptionHandling().authenticationEntryPoint(macLoginUrlAuthenticationEntryPoint());



    }

    @Bean
    public AuthenticationEntryPoint macLoginUrlAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }


    public  class  CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException, ServletException {
            //response.setStatus(HttpStatus.UNAUTHORIZED.value());
            JsonResult jr = new JsonResult().setFlag(true).setMsg("未登录！");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(jr));
        }
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
    /**
     * 自定义认证数据源
     */

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    /**
     * 密码加密
     */
   /* @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/
    /*
     * 硬编码几个用户
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("spring").password("123456").roles("LEVEL1","LEVEL2")
                .and()
                .withUser("summer").password("123456").roles("LEVEL2","LEVEL3")
                .and()
                .withUser("autumn").password("123456").roles("LEVEL1","LEVEL3");
    }
    */
}