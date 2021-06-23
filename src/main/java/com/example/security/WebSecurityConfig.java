package com.example.security;

import com.example.model.Role;
import com.example.model.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //    @Autowired
//    private DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/js/**", "/img/**", "/css/**", "/tracker/error").permitAll()
                .antMatchers("/tracker/index", "/tracker/login", "/tracker/registration").anonymous()
                .antMatchers("/tracker/adminUsers", "/tracker/adminActivities", "/tracker/statUsers", "/tracker/statActivities", "/tracker/adminRequests").hasRole(RoleName.ADMIN.name())
                .antMatchers("/tracker/userRequests", "/tracker/schedule","/tracker/userProfile","/tracker/updateProfile").hasRole(RoleName.USER.name())
                .anyRequest().authenticated()
//        .and()
//        .formLogin()
                .and()
                .formLogin().loginPage("/tracker/login").defaultSuccessUrl("/tracker/default")
                .and()
                .logout().logoutUrl("/tracker/logout")
                .permitAll()
        ;
//        .loginPage("/tracker/login").permitAll()
//        .defaultSuccessUrl("/tracker/index");
//        .formLogin()
//        .loginPage("/tracker/login")
//                .failureUrl("/tracker/login?error")
//                .defaultSuccessUrl("/tracker/adminUsers")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/js/**", "/img/**", "/css/**", "/tracker/error").permitAll()
//                .antMatchers("/tracker/adminUser").hasRole("USER")
////                .antMatchers("/tracker/adminUser").hasRole("ADMIN")
//                .antMatchers("/tracker/index", "/tracker/login", "/tracker/registration").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/tracker/login")
////                .usernameParameter("userEmail")
//                .failureUrl("/tracker/login?error")
//                .defaultSuccessUrl("/tracker/adminUser")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }



/*    @Bean
    @Override
    public UserDetailsService userDetailsService() {


        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("1")
                        .password(passwordEncoder().encode("1"))
                        .roles("USER")
                        .build(),
                User.builder()
                        .username("2")
                        .password(passwordEncoder().encode("2"))
                        .roles("ADMIN")
                        .build()
        );
    }*/

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select from user user_email, user_password, role_id where user_email = ?")
                .authoritiesByUsernameQuery("select user.user_email, user_role.role_name from user left join user_role on user.role_id = user_role.role_id where user.user_email = ?");
    }*/
}