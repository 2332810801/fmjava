package com.fmjava.core.service;

import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.specEntity;
import com.fmjava.core.pojo.specification.Specification;

import java.util.List;
import java.util.Map;

public interface specService {
    /**
     * 分页条件查询规格
     * @param page 当前页码
     * @param pageSize 行数
     * @param specification 条件查询实体
     * @return 返回总行数&&规格数据
     */
    PageResult search(Integer page, Integer pageSize, Specification specification);

    /**
     *添加的方法
     * @param specEntity 规格对象和规格选项
     */
    void save(specEntity specEntity);

    /**
     * 根据规格id查询规格和规格选项
     * @param id 规格id
     * @return 返回specEntity对象
     */
    specEntity findSpecWithId(Long id);

    /**
     * 更新规格个规格选项的方法
     * @param specEntity 修改的实体
     */
    void update(specEntity specEntity);

    /**
     * 批量 && 删除规格和规格选项
     * @param idx 根据传入的id 删除
     */
    void delete(Long[] idx);

    /**
     * 加载规格下拉框
     * @return
     */
    List<Map> selectOptionList();
}
