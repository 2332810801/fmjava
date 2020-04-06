package com.fmjava.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fmjava.core.dao.template.TypeTemplateDao;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.template.TypeTemplate;
import com.fmjava.core.pojo.template.TypeTemplateQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service //阿里的service
@Transactional //事务
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TypeTemplateDao templateDao;//注入TypeTemplateDao

    /**
     *条件分页查询
     *
     * @param template 查询的模板 实体
     * @param page 当前页码
     * @param pageSize 行数
     * @return
     */
    @Override
    public PageResult findPage(TypeTemplate template, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);//分页插件
        TypeTemplateQuery query = new TypeTemplateQuery();//创建TypeTemplateQuery 对象
        TypeTemplateQuery.Criteria criteria = query.createCriteria(); //追加sql语句
        if (template != null) {//如果传入了值 表示是条件查询
            if (template.getName() != null && !"".equals(template.getName())) {//如果查询的name不为空
                criteria.andNameLike("%"+template.getName()+"%");//追加模糊查询
            }
        }
        Page<TypeTemplate> templateList =
                (Page<TypeTemplate>)templateDao.selectByExample(query);//把重新追加的查询sql执行 交给分页插件管理
        return new PageResult(templateList.getTotal(), templateList.getResult()); //返回PageResult getTotal()：返回总记录数，getResult()：返回的数据列表
    }

    /**
     * 添加模块的方法
     * @param template 添加的实体
     */
    @Override
    public void add(TypeTemplate template) {
        templateDao.insertSelective(template);
    }

    /**
     * 批量删除模块
     * @param idx
     */
    @Override
    public void delete(Long[] idx) {
        TypeTemplateQuery tempQuery=new TypeTemplateQuery();//创建TypeTemplateQuery对象
        TypeTemplateQuery.Criteria criteria = tempQuery.createCriteria();//createCriteria： 创建追加条件
        criteria.andIdIn(Arrays.asList(idx));//根据id集合删除品牌
        templateDao.deleteByExample(tempQuery);
    }

    /**
     * 查询修改
     * @param id
     * @return
     */
    @Override
    public TypeTemplate findById(Long id) {
        return templateDao.selectByPrimaryKey(id);
    }

    /**
     * 修改模板
     * @param template
     */
    @Override
    public void update(TypeTemplate template) {
        templateDao.updateByPrimaryKeySelective(template);//updateByPrimaryKeySelective根据主键修改
    }

    /**
     * 根据id查询模板
     * @param id
     * @return
     */
    @Override
    public TypeTemplate findOne(Long id) {
        return templateDao.selectByPrimaryKey(id);
    }

}

