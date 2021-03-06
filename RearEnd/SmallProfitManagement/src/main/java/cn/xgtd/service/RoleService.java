package cn.xgtd.service;

import cn.xgtd.dao.RoleDao;
import cn.xgtd.domain.user.Role;
import cn.xgtd.domain.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 角色管理业务层
 * @author Kite
 * @date 2020/6/3
 */
public interface RoleService {


    /**
     * 添加角色
     * @param role 角色对象
     * @return 当前创建的角色
     */
    public Role addRole(Role role);


    /**
     * 查询所有当前角色下子用户
     * @param rId 角色id
     * @return 角色集合
     */
    public List<Role> findRoleList(Integer rId);

    /**
     * 删除角色提示
     * @param rId 角色id
     * @return  返回管理用户
     */
    public  List<String> deleteRole(Integer rId);

    /**
     * 删除角色
     * @param rId
     * @return
     */
    public Integer deleteRoleUser(Integer rId);

    /**
     * 修改角色
     * @param role 角色对象
     * @return
     */
    public Role updateRole(Role role);

    /**
     * 查询无权限创建角色角色可赋予用户的角色
     * @param uId 角色id
     * @return
     */
    public List<Role> findBasicsRole(Integer uId);


    /**
     * 查询时间
     * @param content 查询内容
     * @param laterTime 之后
     * @param beforeTime 之前
     * @return
     */
    public List<Role> findRoleSearch( String content, String laterTime ,String beforeTime);



}
