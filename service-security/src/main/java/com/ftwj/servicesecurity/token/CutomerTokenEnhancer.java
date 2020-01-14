package com.ftwj.servicesecurity.token;

import com.ftwj.servicesecurity.vo.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用oauth/token返回值中带用户信息
 */
public class CutomerTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        DefaultOAuth2AccessToken token = ((DefaultOAuth2AccessToken) oAuth2AccessToken);
        final Map<String,Object> additionInfo = new HashMap<>();
        Object principale = oAuth2Authentication.getPrincipal();
        if (principale instanceof User){
            User user = (User)principale;
            additionInfo.put("username", user.getUsername());
            additionInfo.put("authorities", user.getAuthorities());
        }
        // 将用户信息添加到token额外信息中
        additionInfo.put("code", 0);
        additionInfo.put("msg", "用户鉴权成功");
        token.setAdditionalInformation(additionInfo);
        return token;
    }

}
