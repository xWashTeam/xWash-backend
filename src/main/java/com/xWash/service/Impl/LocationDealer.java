package com.xWash.service.Impl;

import org.springframework.stereotype.Service;

@Service
public class LocationDealer {

    private String convert_d19(String locationKey) {
        StringBuilder s = new StringBuilder("");
        try {
            String[] parts = locationKey.split("_");
            // 东十九 格式为 d19_方向_楼层 例如 d19_E_3.5
            s.append("东十九");
            if (parts[1].equals("E")) {
                s.append("_东边");
            } else {
                s.append("_西边");
            }
            s.append("_").append(parts[2]).append("楼");
            return s.toString();
        } catch (IndexOutOfBoundsException e) {
            return locationKey;
        } catch (Exception e) {
            System.out.println("[LocationDealer d19] 出现异常");
            e.printStackTrace();
            return locationKey;
        }
    }

    public String convertIntoChinese(String locationKey) {
        String res = locationKey;
        if (locationKey.contains("d19")){
            res=convert_d19(locationKey);
        }
        return res;
    }
}
