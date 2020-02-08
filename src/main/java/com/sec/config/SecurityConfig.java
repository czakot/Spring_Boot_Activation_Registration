package com.sec.config;

import com.sec.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @EnableGlobalMethodSecurity(securedEnabled = true) // for usage of @Secured("<role>") to secure a method
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        
        RoleRepository roleRepository;
        
        @Autowired
        public void setRoleRepository(RoleRepository roleRepository) {
            this.roleRepository = roleRepository;
        }

	@Bean
        @Override
	public UserDetailsService userDetailsService() {
	    return super.userDetailsService();
	}
	
	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception{
		auth
                    .userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity httpSec) throws Exception {
            httpSec
                .authorizeRequests()
                    .antMatchers("/css/*").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/reg").permitAll()
                    .antMatchers("/adminreg").permitAll()
                    .antMatchers("/restart").permitAll()
                    .antMatchers("/restart_blank").permitAll()
                    .antMatchers("/activation/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .logout()
                    .logoutSuccessUrl("/login?logout").permitAll();

            if (roleRepository.findByRole("ADMIN") != null) {
                httpSec.formLogin().loginPage("/login").permitAll();
            } else {
                httpSec.formLogin().loginPage("/prelogin").permitAll();
            }

	}	
	
}
