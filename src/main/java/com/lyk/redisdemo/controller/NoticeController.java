package com.lyk.redisdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    @Qualifier("redisTemplate1")
    private RedisTemplate redisTemplate;


    @RequestMapping("/jinhuo")
    @ResponseBody
    public String jinhuo() {
        redisTemplate.opsForValue().set("P1", 1000);
        System.out.println(redisTemplate.opsForValue().get("P1").toString());
        return "进货成功";
    }

    @RequestMapping("/getCount")
    @ResponseBody
    public String getCount() {
        Object p1 = redisTemplate.opsForValue().get("P1");
        System.out.println("库存剩余:" + (int) p1);
        return "库存剩余:" + (int) p1;
    }

    /**
     * @param :
     * @return :
     * @throws :
     * @Description : 用户购买商品
     * @author : lyk
     * @date : 2021-06-22 14:49
     */
    @RequestMapping("/buyProduct")
    @ResponseBody
    public String buyProduct() {
        String name = Thread.currentThread().getName();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", name);
        if (lock){
            Object p1 = redisTemplate.opsForValue().get("P1");
            int p = (int) p1;
            if (p >= 0) {
                p = --p;
                redisTemplate.opsForValue().set("P1", p);
                System.out.println(name + "购买成功,剩余库存" + p);
                redisTemplate.delete("lock");
                return name + "购买成功,剩余库存" + p;
            } else {
                System.out.println("库存不足请等待进货");
                redisTemplate.delete("lock");
                return "库存不足请等待进货";

            }
        }else {
            System.out.println("当前人数过多请稍后再试");
            return "当前人数过多请稍后再试";
        }

    }
}
