package cc.example.com.myzingproject.entity;

/**
 * 客户类
 * Created by CC on 2017/4/23.
 */

public class PersonInfo {
    public String phone;//手机号
    public String name;//昵称
    public String time;//时间
    public Boolean isSign = false;//是否签收

    public PersonInfo(String phone,String time){
        this.phone = phone;
        this.time = time;
    }

}
