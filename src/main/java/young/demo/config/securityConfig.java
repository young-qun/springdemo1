package young.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private DataSource dataSource;

    /**
     * 配置如何拦截通过的请求 --类似于xml的配置拦截器
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").authenticated().antMatchers(HttpMethod.POST,"/spit").authenticated()
        .anyRequest().permitAll();
    }

    /**
     * 配置user-detail  为Spring security 提供用户的数据支撑 即哪些用户可以登陆
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**认证的方式有多重  --基于内存
         * inMemoryUserDetailsManagerConfigurer 对象 处理基于内存的用户的认证
         */
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryUserDetailsManagerConfigurer = auth.inMemoryAuthentication();
        //允许的用户的信息 用户名，角色，密码
        inMemoryUserDetailsManagerConfigurer.withUser("aaaa").password("123").roles("aa");

        /**
         * 认证的方式有多重  --基于jdbc数据库
         * 配置数据库的连接
         * JdbcUserDetailsManagerConfigurer 操作数据库的对象
         */
        JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> jdbcUserDetailsManagerConfigurer = auth.jdbcAuthentication();
        //配置数据库 可以访问
        jdbcUserDetailsManagerConfigurer.dataSource(dataSource);
        //该方法去数据库中查询用户
        jdbcUserDetailsManagerConfigurer.usersByUsernameQuery("");
        //查询改用户的授权信息
        jdbcUserDetailsManagerConfigurer.authoritiesByUsernameQuery("");
        //对密码进行转码处理
        jdbcUserDetailsManagerConfigurer.passwordEncoder(new BCryptPasswordEncoder(11));
        /**认证的方式有多重  --基于ladp认证方式
         * 引入ladp的依赖
         * LdapAuthenticationProviderConfigurer 对象
         */
        LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> authenticationProviderConfigurer = auth.ldapAuthentication();
    }

    /**
     * 配置filter链
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
    }
}
