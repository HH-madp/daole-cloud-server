package com.daole.cloud.assistant.util;

import com.daole.cloud.assistant.entity.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {
    public static List<Map<String, Object>> menuChild(Long id, List<Level> listmap) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        if (listmap.size() > 0) {
            listmap.forEach(level -> {
                Map<String, Object> childArray = new HashMap<String, Object>(16);
                if (id.equals(level.getParentId())) {
                    childArray.put("id", level.getId());
                    childArray.put("label", level.getName());
                    childArray.put("children", menuChild(level.getId(), listmap));
                    lists.add(childArray);
                }
            });
        }
        return lists;
    }
}
