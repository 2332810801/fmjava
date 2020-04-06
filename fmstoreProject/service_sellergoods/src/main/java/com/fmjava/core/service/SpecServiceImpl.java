package com.fmjava.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fmjava.core.dao.specification.SpecificationDao;
import com.fmjava.core.dao.specification.SpecificationOptionDao;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.entity.specEntity;
import com.fmjava.core.pojo.specification.Specification;
import com.fmjava.core.pojo.specification.SpecificationOption;
import com.fmjava.core.pojo.specification.SpecificationOptionQuery;
import com.fmjava.core.pojo.specification.SpecificationQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service //阿里的Service
@Transactional //事务注解
public class SpecServiceImpl implements specService {
    @Autowired
    private SpecificationDao specificationDao;//注入规格对象dao层

    @Autowired
    private SpecificationOptionDao specificationOptionDao;//规格选项的dao

    /**
     * 分页查询规格的方法
     *
     * @param page          当前页码
     * @param pageSize      传入的行数
     * @param specification 传入条件查询的实体
     * @return 返回 总行数，规格数据
     */
    @Override
    public PageResult search(Integer page, Integer pageSize, Specification specification) {
        PageHelper.startPage(page, pageSize);//调用分页插件
        SpecificationQuery specificationQuery = new SpecificationQuery();//specificationQuery
        if (specification != null) {//如果specification不为空值
            SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();//创建追加条件
            if (specification.getSpecName() != null && !"".equals(specification.getSpecName())) {//如果传入的名称不为空 追加名称查询
                criteria.andSpecNameLike("%" + specification.getSpecName() + "%");//追加模糊查询条件 andSpecNameLike
            }
        }
        Page<Specification> specifications = (Page<Specification>) specificationDao.selectByExample(specificationQuery);//根据更改后的specificationQuery方案 进行查询

        PageResult pageResult = new PageResult(specifications.getTotal(), specifications.getResult());//new一个分页实体 getTotal()：返回总记录数 ：getResult()：查询的数据
        return pageResult;
    }

    /**
     * 添加规格和 规格选项的方法
     *
     * @param specEntity 规格对象和规格选项
     */
    @Override
    public void save(specEntity specEntity) {
        //1.保存规格对象 保存完后，获取id
        specificationDao.insertSelective(specEntity.getSpec());

        for (SpecificationOption specificationOption : specEntity.getSpecOption()) {
            //2.设置每一个规格选项对应的规格id
            specificationOption.setSpecId(specEntity.getSpec().getId());
            //3.保存每一个规格选项
            specificationOptionDao.insertSelective(specificationOption);
        }

    }

    /**
     * 根据规格id查询规格和规格选项
     *
     * @param id
     * @return
     */
    @Override
    public specEntity findSpecWithId(Long id) {
        //1.查询出规格
        Specification specification = specificationDao.selectByPrimaryKey(id);//调用根据规格id查询规格

        //2.根据规格id查询规格的选项
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();//创建规格选项的 SpecificationOptionQuery 对象

        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();//通过SpecificationOptionQuery对象获取criteria对象来添加查询条件

        criteria.andSpecIdEqualTo(specification.getId()); //添加根据规格id查询规格选项

        List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(specificationOptionQuery);//把添加好的查询条件进行查询操作

        //3.将查询出的内容，封装成一个实体specEntity 返回
        specEntity specEntity = new specEntity();//创建一个specEntity对象进行封装 规格和规格选项

        specEntity.setSpec(specification);//设置规格

        specEntity.setSpecOption(specificationOptions);//设置规格选项
        return specEntity;//返回
    }

    /**
     * 更新规格个规格选项的方法
     *
     * @param specEntity 修改的实体
     */
    @Override
    public void update(specEntity specEntity) {
        //1.更新规格对象
        specificationDao.updateByPrimaryKeySelective(specEntity.getSpec());//先更新规格对象
        //2.先打破之前的关系 id删除对象的规格
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();//创建specificationOptionQuery
        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();//创建criteria对象追加sql代码
        criteria.andSpecIdEqualTo(specEntity.getSpec().getId());//追加根据规格对象删除数据
        specificationOptionDao.deleteByExample(specificationOptionQuery);//根据规格对象删除数据
        //3.重新建立关系
        for (SpecificationOption specificationOption : specEntity.getSpecOption()) {//遍历要修改的规格选项
            //2.设置每一个规格选项对应的规模的id
            specificationOption.setSpecId(specEntity.getSpec().getId());//设置规格选项对应的规格ID
            //3.保存每一个规格选项
            specificationOptionDao.insertSelective(specificationOption);//添加规格选项
        }
    }

    /**
     * 删除规格 和规格选项
     *
     * @param idx 根据传入的id 删除
     */
    @Override
    public void delete(Long[] idx) {
        for (Long id : idx) {
            if (idx != null) {
                //1.删除规格
                specificationDao.deleteByPrimaryKey(id);//根据主键删除
                //2.删除规格选项
                SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();//创建specificationOptionQuery
                SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();//创建criteria对象追加sql代码
                criteria.andSpecIdEqualTo(id);//根据id 条件删除
                specificationOptionDao.deleteByExample(specificationOptionQuery);//删除
            }
        }


    }

    /**
     * 加载规格下拉框
     *
     * @return
     */
    @Override
    public List<Map> selectOptionList() {
        return specificationDao.selectOptionList();
    }
}
