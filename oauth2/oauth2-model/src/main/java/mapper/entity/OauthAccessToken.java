package mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("oauth_access_token")
public class OauthAccessToken extends Model<OauthAccessToken> {

    private static final long serialVersionUID = 1L;

    @TableField("token_id")
    private String tokenId;
    @TableField("token")
    private byte[] token;
    @TableId(value = "authentication_id", type = IdType.NONE)
    private String authenticationId;
    @TableField("user_name")
    private String userName;
    @TableField("client_id")
    private String clientId;
    @TableField("authentication")
    private byte[] authentication;
    @TableField("refresh_token")
    private String refreshToken;


    @Override
    protected Serializable pkVal() {
        return this.authenticationId;
    }

}
