package com.daole.cloud.assistant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daole.cloud.assistant.service.LevelService;
import com.daole.cloud.assistant.entity.Level;
import com.daole.cloud.common.vo.R;
import com.daole.cloud.assistant.util.TreeUtil;
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
    public R getAllLevel() {
        //查询出所有的分类
        List<Level> levelList = levelService.list();
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (levelList.size() > 0) {
            //遍历分类，筛选出所有一级分类
            levelList.forEach(level -> {
                Map<String, Object> levelMap = new HashMap<>();
                if (0 == level.getParentId()) {
                    levelMap.put("id", level.getId());
                    levelMap.put("label", level.getName());
                    //调用递归函数，查询出所有下级
                    levelMap.put("children", TreeUtil.menuChild(level.getId(), levelList));
                    resultList.add(levelMap);
                }
            });
        }
        return R.success(resultList);
    }

    //保存分类数据
    @PostMapping("save")
    public R sqve(@Valid Level level) {
        //判断传入的level的id是否为null；如果为null,则为新增，否则为更新
        if (level.getId() != null) {
            levelService.updateById(level);
        } else {
            levelService.save(level);
        }
        return R.success(level);
    }

    /**
     * 根据父级id查询该父级id下的所有子级数据
     *
     * @param parentId
     * @return
     */
    @PostMapping("getByParentId")
    public R<Page<Level>> getgetByParentId(@RequestParam(required = false, value = "pageNum", defaultValue = "0") int pageNum,
                                           @RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam Long parentId) {
        //分页查询数据
        Page<Level> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Level> queryWrapper = new QueryWrapper<>();
        //按id倒叙排序
        queryWrapper.orderByDesc("id");
        //按条件查询数据
        queryWrapper.lambda().eq(Level::getParentId, parentId);
        IPage<Level> pageLevel = levelService.page(page, queryWrapper);
        return R.success(pageLevel);
    }

    /**
     * 根据id获取数据
     *
     * @param id
     * @return
     */
    @PostMapping("get")
    public R get(@RequestParam Long id) {
        QueryWrapper<Level> queryWrapper = new QueryWrapper<>();
        //设置插叙条件
        queryWrapper.lambda().eq(Level::getId, id);
        List<Level> levelList = levelService.list(queryWrapper);
        return R.success(levelList);
    }

    /**
     * 根据id删除当前数据
     *
     * @param id
     * @return
     */
    @PostMapping("del")
    public R delete(@RequestParam Long id) {
        //删除菜单时，先查询当前菜单下是否有下级菜单，如果有下级菜单，则不进行删除
        QueryWrapper<Level> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Level::getParentId, id);
        List<Level> levelList = levelService.list(queryWrapper);
        if (levelList.size() > 0) {
            return R.fail("该菜单存有下级菜单，请删除下级菜单再进行操作!");
        } else {
            QueryWrapper<Level> delWrapper = new QueryWrapper<>();
            delWrapper.lambda().eq(Level::getId, id);
            levelService.remove(delWrapper);
            return R.success();
        }
    }
}
