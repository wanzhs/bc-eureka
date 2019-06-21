package mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Clientdetails extends Model<Clientdetails> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "appId", type = IdType.NONE)
    private String appId;
    @TableField("resourceIds")
    private String resourceIds;
    @TableField("appSecret")
    private String appSecret;
    @TableField("scope")
    private String scope;
    @TableField("grantTypes")
    private String grantTypes;
    @TableField("redirectUrl")
    private String redirectUrl;
    @TableField("authorities")
    private String authorities;

    @TableField("access_token_validity")
    private Integer accessTokenValidity;
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    @TableField("additionalInformation")
    private String additionalInformation;
    @TableField("autoApproveScopes")
    private String autoApproveScopes;


    @Override
    protected Serializable pkVal() {
        return this.appId;
    }

}
