package com.daole.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("level")
@Data
public class Level{
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    //名称
    private String name;
    //父节点Id
    @TableField("parentId")
    private Long parentId;
    //排序
    private int sort;
}
