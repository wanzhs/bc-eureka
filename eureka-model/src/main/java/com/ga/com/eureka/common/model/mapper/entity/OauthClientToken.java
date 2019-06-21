package com.ga.com.eureka.common.model.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Blob;

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
@TableName("oauth_client_token")
public class OauthClientToken extends Model<OauthClientToken> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "authentication_id", type = IdType.NONE)
    private String authenticationId;
    @TableField("token_id")
    private String tokenId;
    @TableField("token")
    private Blob token;
    @TableField("user_name")
    private String userName;
    @TableField("client_id")
    private String clientId;


    @Override
    protected Serializable pkVal() {
        return this.authenticationId;
    }

}
