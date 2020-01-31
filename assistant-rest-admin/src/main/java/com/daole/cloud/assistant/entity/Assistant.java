package com.daole.cloud.assistant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("assistant")
@Data
public class Assistant {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    //节点Id
    @TableField("leId")
    private Long leId;
    @TableField("leName")
    private String leName;
}
