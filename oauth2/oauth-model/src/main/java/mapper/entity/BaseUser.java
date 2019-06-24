package mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:wanzhongsu
 * @description: 用户表
 * @date:2019/6/21 9:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("base_user")
public class BaseUser extends Model<BaseUser> {

    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.NONE)
    private String userId;
    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;
    /**
     * 用户密码
     */
    @TableField("user_pwd")
    private String userPwd;
    /**
     * 用户电话
     */
    @TableField("user_phone")
    private String userPhone;
    /**
     * 用户性别  0 女、1 男
     */
    @TableField("user_gender")
    private UserGender userGender;
    /**
     * 用户年龄
     */
    @TableField("user_age")
    private Integer userAge;
    /**
     * 用户活跃   0 禁用、1 启用
     */
    @TableField("user_active")
    private UserActive userActive;
    /**
     * 创建时间
     */
    @TableField("create_dt")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDt;
    /**
     * 更新时间
     */
    @TableField("update_dt")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDt;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    public enum UserGender implements IEnum<Integer> {
        FEMALE(0, "女"),
        MALE(1, "男");
        private int value;
        private String desc;

        UserGender(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue() {
            return this.value;
        }

        @JsonValue
        public String getDesc() {
            return this.desc;
        }
    }

    public enum UserActive implements IEnum<Integer> {
        DISABLE(0, "禁用"),
        ENABLE(1, "启用");
        private int value;
        private String desc;

        UserActive(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue() {
            return this.value;
        }

        @JsonValue
        public String getDesc() {
            return this.desc;
        }
    }
}
