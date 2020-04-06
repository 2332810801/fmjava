package com.fmjava.core.service;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.good.Brand;

import	java.util.List;
import java.util.Map;

public interface BrandService {

    public List<Brand> findAllBrands();

    /**
     * 分页查询
     * @param page 当前页
     * @param rows 一次查询多少条记录
     * @return 封装的分页数据信息
     */
    public PageResult findPage(Integer page, Integer pageSize,Brand brand);

    /**
     * 添加品牌
     * @param brand
     */
    void add(Brand brand);

    /**
     * 根据ID查询一个品牌
     * @param id
     * @return
     */
    Brand findById(long id);

    /**
     * 更新品牌
     * @param brand
     */
    void update(Brand brand);

    /**
     * 根据ID删除品牌
     * @param idx
     */
    void delete(Long[] idx);

    /**
     * 查询品牌选项
     * @return
     */
    List<Map> selectOptionList();
}
