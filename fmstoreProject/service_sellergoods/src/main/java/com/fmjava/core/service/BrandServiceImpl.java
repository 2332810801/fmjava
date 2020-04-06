package com.fmjava.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fmjava.core.dao.good.BrandDao;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.good.Brand;
import com.fmjava.core.pojo.good.BrandQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional //事务处理
public class BrandServiceImpl implements BrandService {

    /**
     * 导入dao
     **/
    @Autowired
    private BrandDao brandDao;

    /**
     * 查询品牌信息
     *
     * @return
     */
    @Override
    public List<Brand> findAllBrands() {
        return brandDao.selectByExample(null);//根据实体查询 null：查询所有
    }

    /**
     * @param page     当前页
     * @param pageSize 行数
     * @param brand    传入的查询条件 实体
     * @return
     */
    @Override
    public PageResult findPage(Integer page, Integer pageSize, Brand brand) {
        //利用分页助手实现分页, 第一个参数:当前页, 第二个参数: 每页展示数据条数
        PageHelper.startPage(page, pageSize);
        BrandQuery brandQuery = new BrandQuery();
        brandQuery.setOrderByClause("id desc");//追加查询条件倒叙
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        if (brand != null) {//如果brand不为空
            if (brand.getName() != null && !"".equals(brand.getName())) {//名称不能为空
                criteria.andNameLike("%" + brand.getName() + "%");//添加模糊查询
            }
            if (brand.getFirstChar() != null && !"".equals(brand.getFirstChar())) {//首字母不能为空
                criteria.andFirstCharLike("%" + brand.getFirstChar() + "%");//添加模糊查询
            }
        }
        Page<Brand> brandList = (Page<Brand>) brandDao.selectByExample(brandQuery);//分页查询插件
        return new PageResult(brandList.getTotal(), brandList.getResult()); //返回总记录数 和查询结果
    }

    /**
     * @param brand 添加的实体
     */
    @Override
    public void add(Brand brand) {
        brandDao.insertSelective(brand);
    }

    /**
     * @param id 根据ID查询的品牌
     * @return
     */
    @Override
    public Brand findById(long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    /**
     * @param brand 根据传入的实体 进行修改
     */
    @Override
    public void update(Brand brand) {
        brandDao.updateByPrimaryKeySelective(brand);//updateByPrimaryKeySelective：根据主键修改
    }

    /**
     * @param idx 传入要删除的ID集合数组  进行批量删除
     */
    @Override
    public void delete(Long[] idx) {
        BrandQuery brandQuery = new BrandQuery();//创建brandQuery对象
        BrandQuery.Criteria criteria = brandQuery.createCriteria();//createCriteria： 创建追加条件
        criteria.andIdIn(Arrays.asList(idx));//根据id集合删除品牌
        brandDao.deleteByExample(brandQuery);//调用根据ID删除品牌的方法
    }

    @Override
    public List<Map> selectOptionList() {
        return brandDao.selectOptionList();
    }
}
