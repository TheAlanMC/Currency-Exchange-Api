package bo.edu.ucb.currencykt.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/v1/api/currency/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
    }

    @Bean
    fun users(): UserDetailsService{
        val user = User.builder()
            .username("user")
            .password("{noop}user")
            .roles("USER")
            .build()
        val admin = User.builder()
            .username("admin")
            .password("{noop}admin")
            .roles("USER", "ADMIN")
            .build()
        return InMemoryUserDetailsManager(user, admin)
    }

}