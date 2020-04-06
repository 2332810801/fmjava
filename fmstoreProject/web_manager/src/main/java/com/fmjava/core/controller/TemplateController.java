package com.fmjava.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.template.TypeTemplate;
import com.fmjava.core.service.TemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
public class TemplateController {
    @Reference
    private TemplateService templateService;

    /**
     * 模板高级分页查询
     * @param template 条件查询的实体
     * @param page 页码
     * @param pageSize 行数
     * @return 返回总记录数&&数据信息
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TypeTemplate template, Integer page, Integer pageSize) {
        PageResult result = templateService.findPage(template, page, pageSize);
        return result;
    }

    /**
     * 添加模块的方法
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TypeTemplate template){
        try{
            templateService.add(template);
            return new Result(true,"添加成功");
        }catch (Exception e){
            return new Result(false,"添加失败");
        }
    }
    @RequestMapping("/delete")
    public Result delete(Long[] idx){
        try {
            templateService.delete(idx);
            return new Result(true,"删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }

    }

    /**
     * 根据id查找模块信息
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public TypeTemplate findById(Long id){
        TypeTemplate byId = templateService.findById(id);
        return byId;
    }
    @RequestMapping("/update")
    public Result update(@RequestBody TypeTemplate template){
        try{
            templateService.update(template);
            return new Result(true,"更新成功");
        }catch (Exception e){
            return new Result(false,"更新失败");
        }
    }
}