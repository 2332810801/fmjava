package com.fmjava.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.good.Brand;
import com.fmjava.core.pojo.seller.Seller;
import com.fmjava.core.service.SellerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    /**
     * 分页查询 && 分页条件查询
     *
     * @param page     当前页
     * @param pageSize 每页展示数据条数
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(Integer page, Integer pageSize, @RequestBody Seller seller) {
        PageResult result = sellerService.findPage(page, pageSize, seller);//调用查询的方法
        return result;
    }

    /**
     * 查询商家详情的方法
     * @param id 传入的商家ID
     * @return 返回商家值
     */
    @RequestMapping("/findOne")
    public Seller findOne(String id) {
        return sellerService.findOne(id);
    }

    /**
     * 商家审核
     * @param sellerId 传入要审核的商家id
     * @param status 更改的状态 1：通过 2：未通过 3：关闭商家
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(String sellerId, String status) {
        try {
            sellerService.updateStatus(sellerId, status);
            return new Result(true, "状态修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "状态修改失败!");
        }
    }

}
