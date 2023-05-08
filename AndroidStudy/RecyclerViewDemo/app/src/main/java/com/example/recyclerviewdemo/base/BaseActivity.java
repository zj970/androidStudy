package com.example.recyclerviewdemo.base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recyclerviewdemo.bean.BasicBean;
import com.example.recyclerviewdemo.bean.Group;
import com.example.recyclerviewdemo.bean.Message;
import com.example.recyclerviewdemo.bean.SelectBean;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther zj970
 * @create 2023-05-05 下午4:03
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 导航返回
     * @param toolbar
     */
    protected void back(MaterialToolbar toolbar){
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    /**
     * 跳转活动
     * @param clazz
     */
    protected void jumpActivity(final Class<?> clazz){
        startActivity(new Intent(this,clazz));
    }

    /**
     * 显示弹窗消息
     * @param msg
     */
    protected void showMsg(CharSequence msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 随机生产列表数据
     * @return
     */
    protected List<String> getStrings(){
        List<String> list = new ArrayList<String>();
        int num = (int) (1 + Math.random() * 41);
        for (int i = 0; i < num; i++) {
            list.add("第 " + i + " 条数据");
        }
        return list;
    }

    protected List<BasicBean> getBasicBean(){
        List<BasicBean> list = new ArrayList<BasicBean>();
        int num = (int) (1 + Math.random() * 41);
        for (int i = 0; i < num; i++) {
            list.add(new BasicBean("第 " + i + " 条标题","第 " + i + " 条内容"));
        }
        return list;
    }

    protected List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        int num = (int) (1 + Math.random() * (20 - 10 + 1));
        for (int i = 0; i < num; i++) {
            int type = i % 2 == 0 ? 0 : 1;
            String content = type == 0 ? "今天你搞钱了吗？" : "摸鱼的时候就专心摸鱼！";
            messages.add(new Message(type, content));
        }
        return messages;
    }

    protected List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();
        int groupNum = (int) (1 + Math.random() * (20 - 10 + 1));
        for (int i = 0; i < groupNum; i++) {
            List<Group.Contacts> contacts = new ArrayList<>();
            int contactsNum = (int) (1 + Math.random() * (20 - 10 + 1));
            for (int j = 0; j < contactsNum; j++) {
                contacts.add(new Group.Contacts("搞钱" + (j + 1) + "号"));
            }
            groups.add(new Group("搞钱" + (i + 1) + "组", contacts));
        }
        return groups;
    }


    protected List<SelectBean> getSelects() {
        List<SelectBean> selectBeans = new ArrayList<>();
        int num = (int) (1 + Math.random() * (50 - 10 + 1));
        for (int i = 0; i < num; i++) {
            selectBeans.add(new SelectBean(false, "第 " + i + " 条数据"));
        }
        return selectBeans;
    }

}
