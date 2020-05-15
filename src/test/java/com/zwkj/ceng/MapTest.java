package com.zwkj.ceng;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zzc on 2020/5/11
 */
public class MapTest {

    public static void main(String[] args) {


        List<String> headList = Lists.newArrayList("11","22","33");
        List<List<String>> lis=  headList.stream().map(name -> Lists.newArrayList(name)).collect(Collectors.toList());
        System.out.println();
    }
}
