package com.daole.cloud.assistant.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daole.cloud.assistant.entity.Assistant;
import com.daole.cloud.assistant.entity.Level;
import com.daole.cloud.assistant.mapper.AssistantMapper;
import com.daole.cloud.assistant.mapper.LevelMapper;
import com.daole.cloud.assistant.service.AssistantService;
import com.daole.cloud.assistant.service.LevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AssistantServiceImpl extends ServiceImpl<AssistantMapper, Assistant> implements AssistantService {
    @Resource
    private AssistantMapper assistantMapper;
}
