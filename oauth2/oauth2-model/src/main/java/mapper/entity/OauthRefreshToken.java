package mapper.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@TableName("oauth_refresh_token")
public class OauthRefreshToken extends Model<OauthRefreshToken> {

    private static final long serialVersionUID = 1L;

    @TableField("token_id")
    private String tokenId;
    @TableField("token")
    private byte[] token;
    @TableField("authentication")
    private byte[] authentication;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
