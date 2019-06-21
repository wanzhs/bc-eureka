package mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@TableName("oauth_code")
public class OauthCode extends Model<OauthCode> {

    private static final long serialVersionUID = 1L;
    @TableField("code")
    private String code;
    @TableField("authentication")
    private byte[] authentication;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
