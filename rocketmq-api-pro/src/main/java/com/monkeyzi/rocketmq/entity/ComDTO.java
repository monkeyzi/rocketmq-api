package com.monkeyzi.rocketmq.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
public class ComDTO  implements Comparable<ComDTO> {

    private Integer id;

    private ComDTO(Integer id){
        this.id=id;
    }
    @Override
    public int compareTo(ComDTO o) {
        return this.id-o.getId();
    }


    public static void main(String[] args) {
        List<ComDTO> list=new ArrayList<>();
        for (int i=5;i>0;i--){
            list.add(new ComDTO(i));
        }
        Collections.sort(list);
        System.out.println("从小到大排序："+JSON.toJSONString(list));

        Comparator comparator= (Comparator<ComDTO>) (o1, o2) -> o2.getId()-o1.getId();
        List<ComDTO> list2=new ArrayList<>();
        for (int i=1;i<6;i++){
            list2.add(new ComDTO(i));
        }
        Collections.sort(list2,comparator);
        System.out.println("从大到小排序："+JSON.toJSONString(list2));
    }
}
