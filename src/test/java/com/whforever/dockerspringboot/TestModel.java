package com.whforever.dockerspringboot;

import org.junit.Test;

public class TestModel {
    @Test
    public void test() {
        int[] data = {3,8,1,22,9};
        getMinMaxData(data);
    }

    public void getMinMaxData(int[] data) {
        int min = data[0];
        int max = data[0];

        for (int i = 1; i < data.length; i++) {
            if (min > data[i]) {
                min = data[i];
            }

            if (max < data[i]) {
                max = data[i];
            }
        }

        System.out.println("最大值为 ：" + max);
        System.out.println("最小值为 ：" + min);
    }
}
