package com.example.remote.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * only support 12/15 bit
 */
public class SIRCPattern {


    /**
     * 12 bit
     */
    int[] test = new int[]{
            //start
            2400, 600,
            //command
            1200,600,//1
            600,600,//0
            1200,600,//1
            600,600,//0
            1200,600,//1
            600,600,//0

            //address
            600,600,//0
            1200,600,//1
            600,600,600,600,//00
            600,600,600,600//00
    };


    private static final int start[] = new int[]{2400,600};
    private static final int bit0[] = new int[] {600,600};
    private static final int bit1[] = new int[] {1200,600};
    private static int[] pattern;
    private static List<Integer> list = new ArrayList<>();


    public static int[] buildPattern(int address, int command) {
        list.clear();
        for (int level: start) {
            list.add(level);
        }
        String addressStr;
        String commandStr;
        commandStr = constructBinaryCode(command,7);
        if(address > 0x0F)
        {
            addressStr = constructBinaryCode(address,8);
        }else {
            addressStr = constructBinaryCode(address,5);

        }
        changeAdd(commandStr);
        changeAdd(addressStr);
        pattern = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            pattern[i] = list.get(i);
        }

        return pattern;
    }


    /**
     * 数字转换为长度为length位的二进制字符串
     * @param code
     * @param length
     * @return
     */
    private static String convertToBinaryBit(int code,int length) {
        String binary = Integer.toBinaryString(code);
        StringBuffer sb = new StringBuffer();
        //每个元素长度为8位，不够前面补充0
        if (binary.length() < length) {
            for (int i = 0; i < length - binary.length(); i++) {
                sb.append("0");
            }
            return sb.append(binary).toString();
        }else{
            return binary.substring(binary.length() - length);
        }
    }


    /**
     * 十六进制键值转化为二进制串，并逆转编码字符串
     * @param keyCode
     * @return
     */
    private static String constructBinaryCode(int keyCode, int length) {
        String binaryStr = convertToBinaryBit(keyCode,length);
        StringBuilder builder = new StringBuilder(binaryStr);
        return builder.reverse().toString();
    }

    /**
     * 二进制字符串转换成电平
     *
     * @param code
     */
    public static void changeAdd(String code) {
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '0')
            {
                for( int level : bit0)
                {
                    list.add(level);
                }
            }else if (code.charAt(i) == '1'){
                for( int level : bit1)
                {
                    list.add(level);
                }
            }
        }
    }

}
