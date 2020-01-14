//package com.ftwj.servicesecurity.authencation;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
//import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
//import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
//import org.springframework.security.web.util.ThrowableAnalyzer;
//import org.springframework.stereotype.Component;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//
//import javax.naming.AuthenticationException;
//import java.io.IOException;
//
//@Component
//public class MyAuthencationFailureHandler implements WebResponseExceptionTranslator {
//    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
//    @Override
//    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
//    }
//
//}
