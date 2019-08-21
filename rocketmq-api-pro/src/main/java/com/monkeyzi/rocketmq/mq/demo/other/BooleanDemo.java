package com.monkeyzi.rocketmq.mq.demo.other;

import java.util.stream.IntStream;

public class BooleanDemo {

    public static boolean valid=true;

    public static void main(String[] args) {

        IntStream.range(0,100).forEach(a->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bbb();
                }
            }).start();
        });
    }

    public  static  void  bbb(){
        Boolean[] error={false, true, true, true};

        int count=0;
        for (Boolean err:error){
            if (!err){
                valid=false;
            }
            if(++count==error.length){
                System.out.println(valid);
            }
        }
    }
}
