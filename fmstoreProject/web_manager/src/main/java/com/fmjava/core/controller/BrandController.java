package com.fmjava.core.controller;
import	java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.good.Brand;
import com.fmjava.core.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAllBrands")
    public List<Brand> getBrands(){
        return brandService.findAllBrands();
    }

    /**
     * 分页查询 && 分页条件查询
     * @param page 当前页
     * @param pageSize 每页展示数据条数
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(Integer page, Integer pageSize,@RequestBody Brand brand) {
        PageResult result = brandService.findPage(page, pageSize,brand);
        return result;
    }

    /**添加品牌*/
    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand){
        try {
            brandService.add(brand);
            return new Result(true,"添加成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }
/**根据ID查询一个品牌*/
    @RequestMapping("/findById")
    public Brand findById(long id){
        Brand brand=brandService.findById(id);
        return brand;
    }
    /**更新品牌*/
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand){
        try {
            brandService.update(brand);
            return new Result(true,"更新成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"更新失败");
        }
    }
    /**删除品牌*/
    @RequestMapping("/delete")
    public Result delete(Long[] idx){
        try {
            brandService.delete(idx);
            return new Result(true,"删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    /**
     * 查询品牌信息
     * @return
     */
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }
}
