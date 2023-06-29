package com.example.remote;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.remote.adapter.RecyclerKeyAdapter;
import com.example.remote.api.ConsumerIrManagerApi;
import com.example.remote.api.IOnClickItemCallback;
import com.example.remote.databinding.ActivityMainBinding;
import com.example.remote.protocol.EProtocol;
import com.example.remote.protocol.NecPattern;
import com.example.remote.protocol.SANSUNGPattern;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IOnClickItemCallback {

    ActivityMainBinding mainBinding;
    ConsumerIrManagerApi consumerIrManagerApi;
    EProtocol protocol = EProtocol.NEC;//默认使用NEC协议

    RecyclerKeyAdapter adapter;

    List<String> keyList = new ArrayList<>();

    int userCodeH = 0x00;

    int userCodeL = 0xBF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        setSupportActionBar(mainBinding.toolbar);
        init();
        if (!consumerIrManagerApi.hasIrEmitter())
        {
            Toast.makeText(this, "手机不支持红外遥控", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nec:
                protocol = EProtocol.NEC;
                mainBinding.toolbar.setTitle(R.string.nec);
                break;
            case R.id.samsung:
                protocol = EProtocol.SAMSUNG;
                mainBinding.toolbar.setTitle(R.string.samsung);
                break;
            case R.id.sony:
                protocol = EProtocol.SONY;
                mainBinding.toolbar.setTitle(R.string.sony);
                break;
            default:
                protocol = EProtocol.NEC;
                mainBinding.toolbar.setTitle(R.string.nec);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void init(){
        for (int i = 0; i < 255; i++) {
            keyList.add("0x"+Integer.toHexString(i));
        }
        consumerIrManagerApi = ConsumerIrManagerApi.getConsumerIrManager(this);
        adapter = new RecyclerKeyAdapter(keyList);
        adapter.setOnClickItemCallback(this);
        mainBinding.recyclerView.setAdapter(adapter);


        mainBinding.editUser.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (mainBinding.editUser.getText().length() == 4)
                {
                    //4位头码处理
                    String text = mainBinding.editUser.getText().toString();
                    String codeH = "0x"+text.substring(0,2);
                    String codeL = "0x"+text.substring(2);
                    userCodeH = Integer.decode(codeH);
                    userCodeL = Integer.decode(codeL);
                }
                return false;
            }
        });

        mainBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mainBinding.editInput.getText()))
                {
                    switch (protocol)
                    {
                        case NEC:
                            consumerIrManagerApi.transmit(38000, NecPattern.buildPattern(userCodeH,userCodeL,Integer.decode(mainBinding.editInput.getText().toString())));
                            break;
                        case SAMSUNG:
                            consumerIrManagerApi.transmit(38000, SANSUNGPattern.buildPattern(userCodeH,userCodeL,Integer.decode(mainBinding.editInput.getText().toString())));
                            break;
                        case SONY:
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        switch (protocol)
        {
            case NEC:
                consumerIrManagerApi.transmit(38000, NecPattern.buildPattern(userCodeH,userCodeL,Integer.decode(keyList.get(position))));
                break;
            case SAMSUNG:
                consumerIrManagerApi.transmit(38000, SANSUNGPattern.buildPattern(userCodeH,userCodeL,Integer.decode(keyList.get(position))));
                break;
            case SONY:
                break;
            default:
                break;
        }
    }
}