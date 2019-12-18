package com.ga.com.oauth2.jwt.config.jwt;

import mapper.entity.BaseUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import utils.constant.BcConstant;
import utils.json.MJsonUtil;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by fp295 on 2018/4/16.
 * 自定义JwtAccessToken转换器
 */
public class JwtAccessToken extends JwtAccessTokenConverter {
    @Resource
    MJsonUtil mJsonUtil;

    /**
     * 生成token
     *
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);

        // 设置额外用户信息
        BaseUser baseUser = ((BaseUserDetail) authentication.getPrincipal()).getBaseUser();
        baseUser.setUserPwd(null);
        // 将用户信息添加到token额外信息中
        defaultOAuth2AccessToken.getAdditionalInformation().put(BcConstant.USER_INFO, baseUser);

        return super.enhance(defaultOAuth2AccessToken, authentication);
    }

    /**
     * 解析token
     *
     * @param value
     * @param map
     * @return
     */
    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
        convertData(oauth2AccessToken, oauth2AccessToken.getAdditionalInformation());
        return oauth2AccessToken;
    }

    private void convertData(OAuth2AccessToken accessToken, Map<String, ?> map) {
        accessToken.getAdditionalInformation().put(BcConstant.USER_INFO, convertUserData(map.get(BcConstant.USER_INFO)));

    }

    private BaseUser convertUserData(Object map) {
        String json = mJsonUtil.objectToJsonStr(map);
        BaseUser user = mJsonUtil.jsonStrToObject(json, BaseUser.class);
        return user;
    }
}