package com.example.remote;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
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
import com.example.remote.protocol.SIRCPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements IOnClickItemCallback {

    ActivityMainBinding mainBinding;
    ConsumerIrManagerApi consumerIrManagerApi;
    EProtocol protocol = EProtocol.NEC;//默认使用NEC协议

    RecyclerKeyAdapter adapter;

    List<String> keyList = new ArrayList<>();

    /**
     * NEC or SANSUNG
     */
    int userCodeH = 0x00;
    int userCodeL = 0xBF;

    /**
     * SONY
     */
    int address = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        setSupportActionBar(mainBinding.toolbar);
        init();
        if (!consumerIrManagerApi.hasIrEmitter()) {
            Toast.makeText(this, "手机不支持红外遥控", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
                notifyDataSonyButton();
                break;
            default:
                protocol = EProtocol.NEC;
                mainBinding.toolbar.setTitle(R.string.nec);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void init() {
        for (int i = 0; i <= 0xFF; i++) {
            keyList.add("0x"+String.format("%02x", i));
        }
        consumerIrManagerApi = ConsumerIrManagerApi.getConsumerIrManager(this);
        adapter = new RecyclerKeyAdapter(keyList);
        adapter.setOnClickItemCallback(this);
        mainBinding.recyclerView.setAdapter(adapter);


        mainBinding.editUser.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (protocol == EProtocol.SONY && mainBinding.editUser.getText().length() > 2){
                    mainBinding.editUser.setText(String.format("%02x", address));
                }

                if (mainBinding.editUser.getText().length() == 4) {
                    //4位头码处理
                    String text = mainBinding.editUser.getText().toString();
                    String codeH = "0x" + text.substring(0, 2);
                    String codeL = "0x" + text.substring(2);
                    userCodeH = Integer.decode(codeH);
                    userCodeL = Integer.decode(codeL);
                } else if ((mainBinding.editUser.getText().length() == 2)) {
                    String text = mainBinding.editUser.getText().toString();
                    address = Integer.decode("0x" + text);
                }
                return false;
            }
        });

        mainBinding.editInput.setOnEditorActionListener((v, actionId, event) -> {
            if (protocol == EProtocol.SONY)
            {
                String text = mainBinding.editInput.getText().toString();
                int  command = Integer.decode( "0x" + text);
                if (command > 0x7F)
                {
                    mainBinding.editInput.setText("");
                }
            }
            return false;
        });

        mainBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mainBinding.editInput.getText())) {
                    switch (protocol) {
                        case NEC:
                            consumerIrManagerApi.transmit(38000, NecPattern.buildPattern(
                                    userCodeH, userCodeL, Integer.decode("0x" + mainBinding.editInput.getText().toString())));
                            break;
                        case SAMSUNG:
                            consumerIrManagerApi.transmit(38000, SANSUNGPattern.buildPattern(
                                    userCodeH, userCodeL, Integer.decode("0x" + mainBinding.editInput.getText().toString())));
                            break;
                        case SONY:
                            consumerIrManagerApi.transmit(40000, SIRCPattern.buildPattern(
                                    address, Integer.decode("0x" + mainBinding.editInput.getText().toString())));
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
        switch (protocol) {
            case NEC:
                consumerIrManagerApi.transmit(38000, NecPattern.buildPattern(
                        userCodeH, userCodeL, Integer.decode(keyList.get(position))));
                break;
            case SAMSUNG:
                consumerIrManagerApi.transmit(38000, SANSUNGPattern.buildPattern(
                        userCodeH, userCodeL, Integer.decode(keyList.get(position))));
                break;
            case SONY:
                consumerIrManagerApi.transmit(40000, SIRCPattern.buildPattern(
                        address, Integer.decode(keyList.get(position))));
                break;
            default:
                break;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void notifyDataSonyButton(){
        mainBinding.editUser.setText(String.format("%02x", address));
        mainBinding.editUser.setHint(String.format("%02x", address));
        keyList.clear();
        for (int i = 0; i <= 0x7F; i++) {
                keyList.add("0x" + String.format("%02x", i));
        }
        adapter.notifyDataSetChanged();
    }
}