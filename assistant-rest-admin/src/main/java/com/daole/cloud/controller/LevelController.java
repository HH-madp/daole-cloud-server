package com.daole.cloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daole.cloud.entity.Level;
import com.daole.cloud.service.LevelService;
import com.daole.cloud.util.TreeUtil;
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
@RequestMapping("/assistant/level")
public class LevelController {
    @Autowired
    private LevelService levelService;

    //获取所有分类，并将分类按分级返回
    @PostMapping("levels")
    public Map<String, Object> getAllLevel() {
        Map<String, Object> resultMap = new HashMap<>();
        //查询出所有的分类
        List<Level> levelList = levelService.list();
        List<Map<String, Object>> resultList = new ArrayList<>();
        //遍历分类，筛选出所有一级分类
        levelList.forEach(level -> {
            Map<String, Object> levelMap = new HashMap<>();
            if (0 == level.getParentId()) {
                levelMap.put("label", level.getName());
                //调用递归函数，查询出所有下级
                levelMap.put("children", TreeUtil.menuChild(level.getId(), levelList));
                resultList.add(levelMap);
            }
        });
        resultMap.put("data", resultList);
        resultMap.put("success", true);
        return resultMap;
    }

    //保存分类数据
    @PostMapping("save")
    public Map<String,Object> sqve(@Valid Level level) {
        levelService.save(level);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        return resultMap;
    }
}
