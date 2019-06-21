package com.ga.com.eureka.common.model.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanzhs
 * @since 2019-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oauth_client_details")
public class OauthClientDetails extends Model<OauthClientDetails> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "client_id", type = IdType.NONE)
    private String clientId;
    @TableField("resource_ids")
    private String resourceIds;
    @TableField("client_secret")
    private String clientSecret;
    @TableField("scope")
    private String scope;
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;
    @TableField("authorities")
    private String authorities;
    @TableField("access_token_validity")
    private Integer accessTokenValidity;
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;
    @TableField("additional_information")
    private String additionalInformation;
    @TableField("autoapprove")
    private String autoapprove;


    @Override
    protected Serializable pkVal() {
        return this.clientId;
    }

}
