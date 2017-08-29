//package com.demo.project.config;
//
//import com.demo.project.Book;
//import com.demo.project.ReadingListRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Example;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//@Configuration
//@EnableWebSecurity
//public class SecuryConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private ReadingListRepository readingListRepository;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.userDetailsService(new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                Book book = new Book();
//                book.setReader(username);
//                Example<Book> example = Example.of(book);
//
//                return readingListRepository.findOne(example);
//            }
//        });
//        super.configure(auth);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests().antMatchers("/").hasRole("READER")
//                .antMatchers("/**").permitAll()
//                .and()
//                .formLogin().loginPage("/login")
//                .failureUrl("/login?error=true");
//    }
//
//}
