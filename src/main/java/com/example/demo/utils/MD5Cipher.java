package com.example.demo.utils;

import java.security.MessageDigest;

public class MD5Cipher
{
    private static final String KEY_MD5= "MD5";

    public static byte[] encrypt(byte[] data) throws Exception
    {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }
    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString().toUpperCase();

    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
    public static String dbStr(String inStr){
        String pwdStr = string2MD5(inStr)+inStr;
        return convertMD5(pwdStr);
    }
    public static String pwdStr(String dbStr){
        String pwdStr = convertMD5(dbStr);
        String pwd =pwdStr.substring(32,pwdStr.length());
        return pwd;
    }


    // 测试主函数
    public static void main(String args[]) {
        String s = new String("abcdef");
        System.out.println("原始：" + s);
        String pwdStr = string2MD5(s)+s;
        System.out.println("MD5后：" + string2MD5(s));
        System.out.println("加密的：" + convertMD5(pwdStr));
        System.out.println("解密的：" + convertMD5(convertMD5(pwdStr)));
        System.out.println("---------------------------" );
        System.out.println("加密的：" + dbStr(s));
        System.out.println("解密的：" + pwdStr(dbStr(s)));

    }


}
