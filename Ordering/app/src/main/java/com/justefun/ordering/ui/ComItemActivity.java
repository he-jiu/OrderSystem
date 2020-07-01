package com.justefun.ordering.ui;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.justefun.ordering.R;
import com.justefun.ordering.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.ui
 * @time 2018/12/23 13:03
 * @describe TODO
 */
public class ComItemActivity extends BaseActivity implements View.OnClickListener {

    private EditText comment_item;
    private String dish_name;
    private String dish_id;
    private String orderID;

    private Button btn_item_comment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_item);
        Intent intent = getIntent();

        initView(intent);
    }

    private void initView(Intent intent) {
        comment_item = findViewById(R.id.comment_item);
        btn_item_comment = findViewById(R.id.btn_item_comment);
        btn_item_comment.setOnClickListener(this);

        orderID = intent.getStringExtra("orderID");
        dish_name = intent.getStringExtra("DishName");
        dish_id = intent.getStringExtra("DishId");

        //设置标题
        getSupportActionBar().setTitle(dish_name+"的评论");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_item_comment:
                if(!TextUtils.isEmpty(comment_item.getText())){
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(date);
                    //post请求简洁版实现
                    HttpParams params = new HttpParams();
                    params.put("dishid",dish_id);
                    params.put("content",comment_item.getText().toString());
                    params.put("star",5);
                    params.put("keyword","0");
                    params.put("orderid",orderID);
                    params.put("time",dateString);
                    RxVolley.post("http://47.107.79.142:6618/Comment/insert", params, new HttpCallback(){
                        @Override
                        public void onSuccess(String t) {
                            L.i("成功返回："+t);
                        }
                    });

                    finish();
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
