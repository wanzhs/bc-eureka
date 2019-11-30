package mapper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mapper.entity.BaseRole;
import mapper.entity.BaseUserRole;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanzhs
 * @since 2019-06-21
 */
public interface BaseUserRoleMapper extends BaseMapper<BaseUserRole> {
    List<BaseRole> getRoleByUserId(String userId);
}
