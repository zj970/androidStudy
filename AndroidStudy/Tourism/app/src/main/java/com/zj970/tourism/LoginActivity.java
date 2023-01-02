package com.zj970.tourism;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;
import androidx.annotation.Nullable;
import com.zj970.tourism.base.BaseActivity;

/**
 * <p>
 * 登录业余
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/1
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    TextView phone;
    Button login;
    Button registered;
    ImageView ios;
    ImageView weChat;
    ImageView qq;
    CheckBox agreement;

    boolean authentication = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        //初始化
        phone = findViewById(R.id.phone);
        login = findViewById(R.id.login);
        registered = findViewById(R.id.register);
        ios = findViewById(R.id.ios);
        weChat = findViewById(R.id.wechat);
        qq = findViewById(R.id.qq);
        agreement = findViewById(R.id.login_agreement);

        registered.setOnClickListener(this::onClick);
        login.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (!agreement.isChecked()) {
            Toast.makeText(this, "必须同意协议才能使用本程序", Toast.LENGTH_LONG).show();
            return;
        }

        switch (v.getId()) {
            case R.id.register:
                Intent intent = new Intent(this, RegisteredActivity.class);
                startActivity(intent);//注册
                break;
            case R.id.login:
                if (checkLogin() || authentication) {
                    Intent main = new Intent(this, MainActivity.class);
                    startActivity(main);
                    finish();
                }
                break;
            case R.id.ios:
                authentication = isIOS();
                break;
            case R.id.wechat:
                authentication = isWeChat();
                break;
            case R.id.qq:
                authentication = isQQ();
                break;
        }
    }


    private Boolean checkLogin() {
        //TODO:进行登录认证
        return true;
    }

    private Boolean isIOS() {
        //TODO: IOS第三方接口认证调用
        return true;
    }

    private Boolean isWeChat() {
        //TODO:微信接口认证调用
        return true;
    }

    private Boolean isQQ() {
        //TODO:QQ接口认证调用
        return true;
    }

    public static void actionStart(Context context, String userName, String password){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("userName",userName);
        intent.putExtra("password",password);
        context.startActivity(intent);
    }

}
