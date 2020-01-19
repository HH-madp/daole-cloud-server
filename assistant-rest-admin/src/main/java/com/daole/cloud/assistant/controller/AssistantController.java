package com.daole.cloud.assistant.controller;

import com.daole.cloud.assistant.entity.Assistant;
import com.daole.cloud.assistant.service.AssistantService;
import com.daole.cloud.assistant.util.TreeUtil;
import com.daole.cloud.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assistant/assi")
public class AssistantController {
    @Autowired
    private AssistantService assistantService;

    //查询所有手册信息
    @PostMapping("query")
    public R query(@RequestParam Long leId) {
        //查询出所有的分类
        List<Assistant> assistantList = assistantService.list();
        return R.success(assistantList);
    }
    //保存手册数据
    @PostMapping("save")
    public R sqve(@Valid Assistant assistant) {
        //判断传入的assistant的id是否为null；如果为null,则为新增，否则为更新
        if (assistant.getId() != null) {
            assistantService.updateById(assistant);
        } else {
            assistantService.save(assistant);
        }
        return R.success();
    }

}
