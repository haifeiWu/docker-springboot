package com.whforever.dockerspringboot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    public static void main(String[] args) {
        getLastNum("asf12h23456jkl126456789cvbnm12345");
    }

    public static void getLastNum(String input) {

        // TODO 找数字
        int count = 0;
        List<String> sortStr = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (char data : input.toCharArray()) {
            if (data > '0' && data < '9') {
                count++;
                stringBuilder.append(data);
            } else {
                count = 0;
                String temp = stringBuilder.toString();
                if (null != temp && !"".equals(temp)) {
                    sortStr.add(temp);
                    stringBuilder = new StringBuilder();
                }
            }
        }

        // TODO 找升序数字
        List<String> sortNum = new ArrayList<>();
        sortStr.stream().forEach(s -> {
            boolean isSort = true;
            int countFlag = 0;
            char[] charArr = s.toCharArray();
            for (int i = 0; i < charArr.length - 1; i++) {
                countFlag = i + 1;
                if (charArr[i] > charArr[countFlag]) {
                    isSort = false;
                    break;
                }
            }

            if (isSort) {
                sortNum.add(s);
            }
        });

        // TODO 找最大的数字
        AtomicInteger maxData = new AtomicInteger();
        sortNum.stream().forEach(num -> {
            int temp = Integer.valueOf(num);
            if (temp > maxData.get()) {
                maxData.set(temp);
            }
        });

        System.out.println(maxData);
    }
}
