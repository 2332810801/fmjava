package com.fmjava.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.entity.specEntity;
import com.fmjava.core.pojo.specification.Specification;
import com.fmjava.core.service.specService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spec")
public class SpecController {

    @Reference
    private specService specService;

    /**
     * @param page          当前页码
     * @param pageSize      行数
     * @param specification 传入的实体
     * @return 返回总记录数 && 数据列表
     */
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer pageSize, @RequestBody Specification specification) {
        //调用分页条件查询的方法
        PageResult search = specService.search(page, pageSize, specification);
        return search;
    }

    /**
     * 添加的方法
     * @param specEntity 添加的规格和规格选项
     * @return
     */
    @RequestMapping("/save")
    public Result save(@RequestBody specEntity specEntity) {
        try {
            specService.save(specEntity);//调用添加的方法
            return new Result(true, "保存成功");
        } catch (Exception e) {
            return new Result(false, "保存失败");
        }
    }
    @RequestMapping("/findSpecWithId")
    public specEntity findSpecWithId(Long id){
        return specService.findSpecWithId(id);//调用根据规格id查询规格和规格选项
    }

    @RequestMapping("/update")
    public Result update(@RequestBody specEntity specEntity) {
        try {
            specService.update(specEntity);//调用修改的方法
            return new Result(true, "更新成功");
        } catch (Exception e) {
            return new Result(false, "更新失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] idx){
        try {
            specService.delete(idx);
            return new Result(true,"删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    /**
     * 加载规格下拉框
     * @return
     */
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
        return specService.selectOptionList();
    }
}
