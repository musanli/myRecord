package com.main.dao.comm.inter;

import com.main.dao.comm.entity.MenuManage;
import com.main.dao.system.EasyUIComboTreePojo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MenuManageMapper {
    int deleteByPrimaryKey(String id);

    int deleteByModel(MenuManage record);

    int insert(MenuManage record);

    int insertByModel(MenuManage record);

    MenuManage selectOneModel(String id);

    int updateByModel(MenuManage record);

    int update(MenuManage record);

    int selectCount(MenuManage record);

    List<MenuManage> selectByModel(MenuManage record);

    List<MenuManage> SelectAll();
    /** 
     * @Description:查询所有最顶层的节点
     * @author li_bin
     * @Date: 2017年12月25日
     * @throws 
     * @return
     */
    List<MenuManage>  selectTopCategory(MenuManage record);
    /** 
     * @Description:查询子菜单项
     * @author li_bin
     * @Date: 2017年12月26日
     * @throws 
     * @param record
     * @return
     */
    List<MenuManage> selectSonMenu(MenuManage record);
    
    /** 
     * @Description: 查询所有信息，并设置别名
     * @author li_bin
     * @Date: 2017年12月27日
     * @throws 
     * @return
     */
    List<EasyUIComboTreePojo> selectAllByAlias() ;
    
    /** 
     * @Description:根据pid查询数据
     * @author li_bin
     * @Date: 2017年12月28日
     * @throws 
     * @param pid
     * @return
     */
    List<MenuManage> selectByPid(@Param("pid")String pid);
}