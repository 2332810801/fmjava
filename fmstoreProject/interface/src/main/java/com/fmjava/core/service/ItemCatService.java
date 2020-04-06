package com.fmjava.core.service;

import com.fmjava.core.pojo.item.ItemCat;

import java.util.List;

public interface ItemCatService {
    /**
     * 查询分类父级的方法
     * @param parentId 父级id
     * @return
     */
    List<ItemCat> findByParentId(Long parentId);


    ItemCat findOnegory(Long id);
}
