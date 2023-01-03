package com.zj970.tourism;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import com.zj970.tourism.base.BaseActivity;

/**
 * <p>
 * 注册
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/2
 */
public class RegisteredActivity extends BaseActivity implements View.OnClickListener {

    EditText userName;
    EditText password;
    EditText name;
    Spinner area;
    Button register_ok;
    CheckBox agreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered);
        userName = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        area = findViewById(R.id.area);
        register_ok = findViewById(R.id.registered_ok);
        agreement = findViewById(R.id.agreement);

        register_ok.setOnClickListener(this::onClick);

        //TODO:用户名输入状态监听
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //TODO:密码输入状态监听
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //TODO:昵称输入状态监听
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registered_ok && registered()) {
            LoginActivity.actionStart(this, userName.getText().toString(), password.getText().toString());
        }
    }


    private Boolean registered() {

        //TODO:注册
        if (TextUtils.isEmpty(userName.getText().toString())) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (! agreement.isChecked()) {
            Toast.makeText(this, "必须同意协议才能使用本程序", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
