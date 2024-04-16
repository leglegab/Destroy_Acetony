package com.petrolpark.destroy.test;

import java.util.Arrays;

import com.petrolpark.destroy.item.SeismographItem.Seismograph;

public class SeismographTest {
    
    public static void main(String[] args) {
        Seismograph seismograph = new Seismograph();
        seismograph.getRows()[0] = 0b1101;
        System.out.println(Arrays.toString(seismograph.getRowDisplayed(0)));
    };
};
