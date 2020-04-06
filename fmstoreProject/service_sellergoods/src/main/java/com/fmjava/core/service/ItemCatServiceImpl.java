package com.fmjava.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fmjava.core.dao.item.ItemCatDao;
import com.fmjava.core.pojo.item.ItemCat;
import com.fmjava.core.pojo.item.ItemCatQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatDao catDao;

    /**
     * 根据id查询父级
     * @param parentId 父级id
     * @return
     */
    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        ItemCatQuery query = new ItemCatQuery();//创建ItemCatQuery 添加查询的内容
        ItemCatQuery.Criteria criteria = query.createCriteria();//创建criteria对象
        criteria.andParentIdEqualTo(parentId);//添加条件
        List<ItemCat> itemCats = catDao.selectByExample(query);//查询返回
        return itemCats;
    }


    /**
     * 根据id查询模板的方法
     * @param id
     * @return
     */
    @Override
    public ItemCat findOnegory(Long id) {
        return catDao.selectByPrimaryKey(id);
    }

}
