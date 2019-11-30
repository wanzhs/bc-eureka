package mapper.entity;

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
@TableName("base_user_role")
public class BaseUserRole extends Model<BaseUserRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_role_id", type = IdType.NONE)
    private String userRoleId;
    @TableField("user_id")
    private String userId;
    @TableField("role_id")
    private String roleId;


    @Override
    protected Serializable pkVal() {
        return this.userRoleId;
    }

}
