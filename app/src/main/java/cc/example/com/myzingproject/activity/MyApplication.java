package cc.example.com.myzingproject.activity;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;


public class MyApplication extends Application {
    public static List<String> numberList;
    @Override
    public void onCreate() {
        super.onCreate();
        numberList=new ArrayList<>();

    }
    public static List<String> getNumberList() {
        return numberList;
    }
    public static void addNumberList(String number){
        numberList.add(number);
    }
}
