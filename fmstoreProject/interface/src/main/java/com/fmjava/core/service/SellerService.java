package com.fmjava.core.service;
/**
 * 商家
 */

import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.seller.Seller;

public interface SellerService {

    /**
     * 申请商家入驻的方法
     * @param seller
     */
    public void add(Seller seller);

    /**
     * 分页 &&条件查询
     * @param page 当前页
     * @param pageSize 行数
     * @param seller 传入的条件数据
     * @return 总页数 && 数据
     */
    PageResult findPage(Integer page, Integer pageSize, Seller seller);

    /**
     * 查询商家详情的方法
     * @param id 传入的商家ID
     * @return 返回商家值
     */
    Seller findOne(String id);

    /**
     * 商家审核
     * @param sellerId 传入要审核的商家id
     * @param status 更改的状态 1：通过 2：未通过 3：关闭商家
     */
    void updateStatus(String sellerId, String status);
}
