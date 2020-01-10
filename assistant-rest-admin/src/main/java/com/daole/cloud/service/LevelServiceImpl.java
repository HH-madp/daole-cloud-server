package com.daole.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daole.cloud.entity.Level;
import com.daole.cloud.mapper.LevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {
    @Resource
    private LevelMapper levelMapper;
}
