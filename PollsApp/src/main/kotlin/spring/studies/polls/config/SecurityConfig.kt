package spring.studies.polls.config

import com.example.polls.security.JwtAuthenticationEntryPoint
import com.example.polls.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import spring.studies.polls.service.CustomUserDetailsService


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        jsr250Enabled = true,
        securedEnabled = true
)//This is used to enable method level security based on annotations.
data class SecurityConfig(
        val customUserDetailsService : CustomUserDetailsService, //To authenticate a User or perform various role-based checks, Spring security needs to load users details
        val unauthoziredHandler : JwtAuthenticationEntryPoint // This class is used to return a 401 unauthorized error to clients that try to access a protected resource without proper authentication
): WebSecurityConfigurerAdapter() {
                // Enable the Spring Security Filter Chain
                // This chain allow us filter certain requests so that user doesn't have to be authenticated (Ex: To access CSS)


    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter()

        /*
        * reads JWT authentication token from the Authorization header of all the requests
        * validates the token
        * loads the user details associated with that token.
        * Sets the user details in Spring Security’s SecurityContext. Spring Security uses the user details to perform authorization checks.
        * */
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth.userDetailsService(customUserDetailsService)
                .pass

    /*
    *
    * AuthenticationManagerBuilder is used to create an AuthenticationManager
    * */
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()

        /*
        *
        * AuthenticationManager is the main Spring Security interface for authenticating a user.
        *
        * */
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // Here was added the CUSTOM implementation of JWTAuthenticationEntryPoint and the custom JWTAuthenticationFilter
        http
                .cors()
                    .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                        .permitAll()
                    .antMatchers("/api/auth/**")
                        .permitAll()
                    .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
                        .permitAll()
                    .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**")
                        .permitAll()
                    .anyRequest()
                        .authenticated()

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

    }
}