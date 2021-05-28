package com.hutu.cloud.common.test;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class TestStudent {
    String name = "hhah";
    int age = 18;
    HashMap map = new HashMap(){{
        put("key1","value1");
        put("key2","value2");
    }};
    List list = new ArrayList<Object>(){{
        add("hhhh");
        add(13);
        add(22L);
        add(new ArrayList<String>(){{
            add("第二层");
            add("222");
        }});
    }};
}
