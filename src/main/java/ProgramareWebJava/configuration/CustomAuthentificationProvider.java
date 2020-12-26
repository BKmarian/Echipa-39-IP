//package ProgramareWebJava.configuration;
//
//import ProgramareWebJava.entities.User;
//import ProgramareWebJava.services.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//
//@Component
//class CustomAuthentificationProvider
//        implements AuthenticationProvider {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication)
//            throws AuthenticationException {
//
//        String name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        if (shouldAuthenticateAgainstThirdPartySystem(name, password)) {
//
//            return new UsernamePasswordAuthenticationToken(
//                    name, password, new ArrayList<>());
//        } else {
//            return null;
//        }
//    }
//    public boolean shouldAuthenticateAgainstThirdPartySystem(String username,String password) {
//
//        if("admin".equals(username) && "admin".equals(password))
//            return true;
//
//        User user = userService.getUserByUsername(username);
//
//        return user.getPassword().equals(password);
//
//    }
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(
//                UsernamePasswordAuthenticationToken.class);
//    }
//}