package org.isj.ing.annuarium.webapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//retenir la classe WebSecurityConfigurerAdapter car ca vient a l'examen
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
		jdbcAuthentication()
		.usersByUsernameQuery(usersQuery)
		.authoritiesByUsernameQuery(rolesQuery)
		.dataSource(dataSource)
		.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.
		authorizeRequests()
		.antMatchers("/login").permitAll()
				.antMatchers("/registration").permitAll()
				.antMatchers("/","/listactes","/searchacteform","/detail").permitAll()
		.antMatchers("/api/**").permitAll() //permet qu'on puisse tester l'application
		.antMatchers("/admin/**").hasAuthority("ADMIN")//pout toutes les routes qui commencent par admin ils doivent avoir le role
		.anyRequest().authenticated().and().csrf().disable().formLogin()
		.loginPage("/login").failureUrl("/login?error=true")//login par defaut et permet et permet d'afficher l'erreur en cas d'erreu
		.defaultSuccessUrl("/listactes")//permet de renvoyer l'user a une autre page quand il s'authentifie
		.usernameParameter("email")
		.passwordParameter("password")
		.and().logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login").and().exceptionHandling()
		.accessDeniedPage("/access-denied");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		.ignoring()//packages qui sont dans le package static
		.antMatchers("/assets/**","/forms/**","/resources/**", "/static/**");
	}

}
