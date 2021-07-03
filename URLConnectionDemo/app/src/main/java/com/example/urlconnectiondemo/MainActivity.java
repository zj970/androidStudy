package com.example.urlconnectiondemo;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


/** 从荆楚理工学院网下载图片，展示出来 **/
public class MainActivity extends Activity {

    public final static String IMAGES_PATH = "https://www.freeimages.com/cn/search/cat";
    public final static String JCUT_ADDRESS = "http://www.jcut.edu.cn";
    private ImageListAdapter adapter =null;
    private List<Bitmap> list = new ArrayList<Bitmap>();
    GridView imageGridView;
    /**
     * 用handler接受来自网络线程获取的Bitmap对象，然后更新主线程UI
     **/
    private final Handler handler = new Handler() {
        @Override
        public void publish(LogRecord record) {

        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }

        public void handleMessage(Message msg) {
            list.add((Bitmap) msg.obj);
            adapter.notifyDataSetChanged();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageGridView = (GridView) findViewById(R.id.imageGridView);
        this.getAllBitmap();
        adapter = new ImageListAdapter(this, R.layout.item_image, list);
        imageGridView.setAdapter(adapter);
        imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView iv = (ImageView) view.findViewById(R.id.bigImage);
                byte[] bytes = getBytesOfVitmap(adapter.getItem(position));
                Intent it = new Intent(MainActivity.this,BigImageActivity.class);
                it.putExtra("bigImageBytes", bytes);
                startActivity(it);
            }
        });
    }

    //获取所有的风景图片地址
    private void getAllBitmap(){
        new Thread(){
            public void run() {
                try {
                    Document document = Jsoup.connect(IMAGES_PATH).get();
                    Elements elements = document.selectFirst("div[class='v_news_content']").select("p");
                    for (int i = 0; i < elements.size(); i++){
                        String path = JCUT_ADDRESS + elements.get(i).select("img").attr("src");
                        getBitmap(path);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //根据图片路径获取图片对象
    public void getBitmap(final String path){
        new Thread(){
            public void run(){
                try {
                    URL url = new URL(path);
                    URLConnection coon = url.openConnection();
                    coon.setConnectTimeout(3 * 3000);
                    InputStream is = coon.getInputStream();
                    //获取图片
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Message msg = new Message();
                    //msg = Message.obtain(handler, 0, bitmap);
                    msg.sendToTarget();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //bitmap序列化
    public byte[] getBytesOfVitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }

}