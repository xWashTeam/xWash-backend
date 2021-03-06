package com.xWash.model.entity;

import java.util.Comparator;
import java.util.Map;

public class LocationComparator {
    public static final Comparator<Map.Entry<String, String>> d19Comparator = new Comparator<Map.Entry<String, String>>() {
        @Override
        public int compare(Map.Entry<String, String> e1, Map.Entry<String, String> e2) {
            /**
             * d19 json中key的格式为：d19_方向(E | W)_楼层（1.5 | 2.5 | ...）
             * 排序目的要让东边排在前
             * */
            String o1 = e1.getKey();
            String o2 = e2.getKey();
            if (o1.equals(o2))
                return 0;
            int delta = o1.charAt(6) - o2.charAt(6);// 下标6为楼层数字
            if (delta == 0) {
                if (o1.charAt(4) - o2.charAt(4) < 0) {// 下标4为E | W
                    return -1;
                }
                return 1;
            }
            return delta;
        }
    };

    public static final Comparator<Map.Entry<String, String>> xi1Comparator = new Comparator<Map.Entry<String, String>>() {
        @Override
        public int compare(Map.Entry<String, String> e1, Map.Entry<String, String> e2) {
            /**
             * xi1 json中key的格式为：xi1_方向(E | W)_楼层.号数（1.1 | 2.2 | ...）
             * */
            String o1 = e1.getKey();
            String o2 = e2.getKey();
            if (o1.equals(o2))
                return 0;
            int delta = o1.charAt(6) - o2.charAt(6);// 下标6为楼层数字
            if (delta == 0) {
                if (o1.charAt(8) - o2.charAt(8) < 0) {// 下标4为E | W
                    return -1;
                }
                return 1;
            }
            return delta;
        }
    };
}
