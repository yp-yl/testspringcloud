package com.ftwj.servicesecurity.token;

import com.ftwj.servicesecurity.vo.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *    自定义token转换器：生成token：DefaultAccessTokenConverter 默认只加入user_name,
 *
 */
public class CustomerTokenConverter extends DefaultAccessTokenConverter {

    public CustomerTokenConverter(){
        super.setUserTokenConverter(new CustomerUserAuthenticationConverter());
    }
    private class CustomerUserAuthenticationConverter extends DefaultUserAuthenticationConverter{
        @Override
        public Map<String, ?> convertUserAuthentication(Authentication authentication){
            Object principale = authentication.getPrincipal();
            LinkedHashMap<String,Object> response = new LinkedHashMap<>();
            if(principale instanceof User){
                User user = (User) principale;
                response.put("username",user.getUsername());
                // 判断权限是否有
                if (user.getAuthorities()!=null && !user.getAuthorities().isEmpty()){
                    response.put("authorities", AuthorityUtils.authorityListToSet(user.getAuthorities()));
                }
            }
            return response;
        }

        @Override
        public Authentication extractAuthentication(Map<String, ?> map) {
            return null;
        }
    }
}
