package com.xWash.msic;

import cn.hutool.core.io.FileUtil;

import java.io.File;

public class FileUtilTest {

    public void lsTest(){
        for (File file :
                FileUtil.ls(".")) {
            System.out.println(file.getName());
        }
    }

    public static void main(String[] args) {
        new FileUtilTest().lsTest();
    }
}
