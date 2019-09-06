package com.monkeyzi.rocketmq.mq.demo.other;

public class StringDemo {

    public static void main(String[] args) {
        String a="hello";
        String c="hello";
        String b=new String("hello");


        System.out.println("a==b:"+(a==b)); //false
        System.out.println("a==c:"+(a==c)); //true
        System.out.println("b==c:"+(b==c)); //false
        System.out.println("a-eq-b:"+a.equals(b)); //true
        System.out.println("a-eq-c:"+a.equals(c)); //true
        System.out.println("b-eq-c:"+b.equals(c)); //true

        /*String str1="abc";
        String str2="abc";
        char[] chars={'a','b','c'};
        String str3=new String(chars);
        System.out.println(str1==str2);
        System.out.println(str1==str3);
        System.out.println(str2==str3);*/
    }
}
