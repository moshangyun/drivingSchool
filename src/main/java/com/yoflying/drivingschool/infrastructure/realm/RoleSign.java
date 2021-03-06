package com.yoflying.drivingschool.infrastructure.realm;

/**
 * 角色标识配置类, <br>
 * 与 role_info 角色表中的 role_sign 字段 相对应 <br>
 * 使用:
 * 
 * <pre>
 * &#064;RequiresRoles(value = RoleSign.ADMIN)
 * public String admin() {
 *     return &quot;拥有admin角色,能访问&quot;;
 * }
 * </pre>
 * 
 * @author StarZou
 * @since 2014年6月17日 下午3:58:51
 **/
public class RoleSign {

    /**
     * 普通后台管理员 标识
     */
    public static final String ADMIN = "admin";

    /**
     * 教练 标识
     */
    public static final String COACH = "coach";

    /**
     * 学员 标识
     */
    public static final String STUDENT = "student";

    /**
     * 商家 标识
     */
    public static final String MERCHANT = "merchant";

    /**
     * 添加更多...
     */

}
