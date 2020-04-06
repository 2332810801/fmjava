package com.fmjava.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fmjava.core.dao.seller.SellerDao;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.good.Brand;
import com.fmjava.core.pojo.good.BrandQuery;
import com.fmjava.core.pojo.seller.Seller;
import com.fmjava.core.pojo.seller.SellerQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    /**
     * 注入商家的dao
     */
    @Autowired
    private SellerDao sellerDao;

    /**
     * 商家申请的方法
     *
     * @param seller
     */
    @Override
    public void add(Seller seller) {
        //创建商家的时间
        seller.setCreateTime(new Date());
        //审核状态注册的时候默认为0 ,未审核
        seller.setStatus("0");
        sellerDao.insertSelective(seller);
    }

    /**
     *
     * @param page 当前页
     * @param pageSize 行数
     * @param seller 传入的条件数据
     * @return 返回总行数和查询的数据
     */
    @Override
    public PageResult findPage(Integer page, Integer pageSize, Seller seller) {
        PageHelper.startPage(page, pageSize);//启动分页查询的插件
        SellerQuery query = new SellerQuery();//创建SellerQuery对象对sql今天更改
        SellerQuery.Criteria criteria = query.createCriteria();//创建Criteria 的对象
        if (seller != null) {//如果传入的值不为空
            if (seller.getStatus() != null && !"".equals(seller.getStatus())) {//如果传入的状态不为空
                criteria.andStatusEqualTo(seller.getStatus());//追加状态
            }
            if (seller.getName() != null && !"".equals(seller.getName())) {//如果传入的名称不为空
                criteria.andNameLike("%" + seller.getName() + "%");//追加 名称的模糊查询
            }
            if (seller.getNickName() != null && !"".equals(seller.getNickName())) {//如果传入的店铺名称不为空
                criteria.andNickNameLike("%" + seller.getNickName() + "%");//追加店铺名称的模糊查询
            }
        }
        Page<Seller> sellerList = (Page<Seller>) sellerDao.selectByExample(query);//将查询的结果保存给page插件
        return new PageResult(sellerList.getTotal(), sellerList.getResult());//返回总行数和查询的数据
    }

    /**
     * 查询商家详情的方法
     * @param id 传入的商家ID
     * @return 返回商家值
     */
    @Override
    public Seller findOne(String id) {
        return   sellerDao.selectByPrimaryKey(id);
    }

    /**
     * 商家审核
     * @param sellerId 传入要审核的商家id
     * @param status 更改的状态 1：通过 2：未通过 3：关闭商家
     */
    @Override
    public void updateStatus(String sellerId, String status) {
        Seller seller = new Seller();//创建商家pojo
        seller.setStatus(status);//修改商家审核状态
        seller.setSellerId(sellerId);//修改要修改的商家
        sellerDao.updateByPrimaryKeySelective(seller);//修改商家状态 ByPrimaryKeySelective：根据主键修改  Selective 有数据则修改 没有就不修改
    }


}