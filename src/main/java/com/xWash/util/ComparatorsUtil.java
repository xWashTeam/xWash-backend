package com.xWash.util;

import com.xWash.entity.QueryResult;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;

public class ComparatorsUtil {

    public static Comparator<Map.Entry<String, QueryResult>> getComparator(String name) {
        switch (name) {
            case "d19":
                return d19Comparator;
            case "xi1":
            case "xi2":
                return xi1Comparator;
        }
        return null;
    }

    private final static Comparator<Map.Entry<String, QueryResult>> d19Comparator = new Comparator<Map.Entry<String, QueryResult>>() {
        @Override
        public int compare(Map.Entry<String, QueryResult> e1, Map.Entry<String, QueryResult> e2) {
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

    private final static Comparator<Map.Entry<String, QueryResult>> xi1Comparator = new Comparator<Map.Entry<String, QueryResult>>() {
        @Override
        public int compare(Map.Entry<String, QueryResult> e1, Map.Entry<String, QueryResult> e2) {
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
