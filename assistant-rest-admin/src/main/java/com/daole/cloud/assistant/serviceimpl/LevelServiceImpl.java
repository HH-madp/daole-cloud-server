package com.daole.cloud.assistant.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daole.cloud.assistant.mapper.LevelMapper;
import com.daole.cloud.assistant.entity.Level;
import com.daole.cloud.assistant.service.LevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {
    @Resource
    private LevelMapper levelMapper;
}
