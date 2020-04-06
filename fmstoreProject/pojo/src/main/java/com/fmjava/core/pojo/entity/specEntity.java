package com.fmjava.core.pojo.entity;

import com.fmjava.core.pojo.specification.Specification;
import com.fmjava.core.pojo.specification.SpecificationOption;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class specEntity implements Serializable {
    private Specification spec;//规格对象
    private List<SpecificationOption> specOption;//规格选项集合
}
