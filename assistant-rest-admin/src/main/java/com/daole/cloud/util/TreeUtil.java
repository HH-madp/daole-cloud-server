package com.daole.cloud.util;

import com.daole.cloud.entity.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {
    public static List<Map<String, Object>> menuChild(Long id, List<Level> listmap) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        listmap.forEach(level -> {
            Map<String, Object> childArray = new HashMap<String, Object>(16);
            if (id.equals(level.getParentId())) {
                childArray.put("label", level.getName());
                childArray.put("children", menuChild(level.getId(), listmap));
                lists.add(childArray);
            }
        });
        return lists;
    }
}
