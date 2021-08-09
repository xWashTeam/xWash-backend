package com.xWash.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.ResourceUtil;
import sun.misc.Resource;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;


public class BuildingFileUtil {
    static String  path = "building_map/";
    static public String getAbsolutePath(String filename){
        return path+filename+".json";
    }

    public static String getContent(File file){
        return FileUtil.readUtf8String(file);
    }

    public static File[] getFiles(){
        return FileUtil.ls("building_map/");
    }

    static public boolean isExist(String absolutePath) {
        URL url = BuildingFileUtil.class.getClassLoader().getResource(absolutePath);
        if (url ==null)
            return false;
        try {
            File file = new File(url.toURI());
            return file.exists() && file.isFile();
        } catch (URISyntaxException e) {
            System.out.println("[util.BuildingFileUtil]  判断文件存在与否出错! ");
            e.printStackTrace();
        }
        return false;
    }
}
