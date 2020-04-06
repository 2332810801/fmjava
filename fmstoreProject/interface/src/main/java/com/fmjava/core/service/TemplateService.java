package com.fmjava.core.service;

import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.template.TypeTemplate;

public interface TemplateService {
    /**
     *
     * @param template 查询的模板 实体
     * @param page 当前页码
     * @param pageSize 行数
     * @return 返回模板列表
     */
    PageResult findPage(TypeTemplate template, Integer page, Integer pageSize);

    /**
     * 添加模块的方法
     * @param template 添加的实体
     */
    void add(TypeTemplate template);

    /**
     * 批量删除模块
     * @param idx
     */
    void delete(Long[] idx);

    /**
     * 查询修改
     * @param id
     * @return
     */
    TypeTemplate findById(Long id);

    /**
     * 更新模块
     * @param template
     */
    void update(TypeTemplate template);

    /**
     * 根据id查询模板
     * @param id
     * @return
     */
    TypeTemplate findOne(Long id);
}
