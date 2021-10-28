package pers.etherealss.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pers.etherealss.common.enums.ApiInfo;
import pers.etherealss.common.filter.LoginCaptchaFilter;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.utils.ResponseUtil;

/**
 * @author wtk
 * @description
 * @date 2021-09-05
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_URL = "/users/login";

    private final UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private LoginCaptchaFilter loginCaptchaFilter;

    /**
     * 将AuthenticationManager注入容器
     * @return
     * @throws Exception
     */
    @Override
    @Bean("authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * IllegalStateException:https://blog.csdn.net/yhlly_/article/details/107348728
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * anonymous           |   匿名可以访问，已登录的用户不能访问
     * denyAll             |   用户不能访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        this.configureLogin(httpSecurity);
        this.configureException(httpSecurity);
        // 基于token，所以不需要session
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        /*
         设置哪些路径可以直接访问，不需要认证
         */
        httpSecurity.authorizeRequests()
                // 对于登录login 验证码captcha 允许匿名访问
                .antMatchers("/test/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/",
                        // 放行Oauth2的API
                        "/oauth/**",
                        "/index.html",
                        "/login.html",
                        "/captcha",
                        LOGIN_URL,
                        "/users/register",
                        "/competitions/**",
                        "/lib/**",
                        "/toastr/**",
                        "/css/**",
                        "/img/**",
                        "/js/**",
                        "/config/**"
                ).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
        ;

        // 关闭CSRF防护
        httpSecurity.csrf().disable();
    }

    private void configureLogin(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin()
                // 登录页面
                .loginPage("http://localhost:8080/#/login")
                // 登录请求访问的Controller路径
                .loginProcessingUrl("/users/login")
                // 登录成功后的跳转路径
                .defaultSuccessUrl("/test.html")
                .permitAll()
                // 成功处理器
                .successHandler(successHandler)
                // 失败处理器
                .failureHandler(failureHandler)
//                .usernameParameter("username").passwordParameter("password").permitAll()
        ;
        // 添加验证码过滤器
        httpSecurity.addFilterBefore(loginCaptchaFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 配置认证失败处理类
     */
    private void configureException(HttpSecurity httpSecurity) throws Exception {
        // 认证失败处理类
        httpSecurity.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            log.debug("认证失败：" + authException);
            Msg<Object> msg = new Msg<>(ApiInfo.FORBIDDEN_REQUEST);
            msg.setMessage(authException.getMessage());
            ResponseUtil.send(response, msg);
        });
    }
}
