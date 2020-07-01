package com.justefun.ordering.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.justefun.ordering.R;
import com.justefun.ordering.adapter.OrderAdapter;
import com.justefun.ordering.dataBase.DataOperation;
import com.justefun.ordering.entity.DishData;
import com.justefun.ordering.entity.OrderData;
import com.justefun.ordering.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.ui
 * @time 2018/12/23 12:05
 * @describe TODO
 */
public class CommentActivity extends BaseActivity implements View.OnClickListener {

    private ListView order_list;
    //适配器
    OrderAdapter adapter;
    private List<OrderData> mList = new ArrayList<>();

    //数据库操作对象
    private DataOperation op;

    private EditText comment_0;
    private Button btn_all_comment;

    private String orderID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        op = new DataOperation(this);
        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");
        initData();
        initView();
    }

    private void initData() {
        mList = op.queryMany();

    }

    private void initView() {
        btn_all_comment = findViewById(R.id.btn_all_comment);
        btn_all_comment.setOnClickListener(this);
        comment_0 = findViewById(R.id.comment_0);
        order_list = findViewById(R.id.order_list);
        adapter = new OrderAdapter(this,mList);
        order_list.setAdapter(adapter);

        //点击listView
        order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderData data = mList.get(position);
                Intent intent = new Intent(CommentActivity.this,ComItemActivity.class);
                intent.putExtra("DishId",data.getDishID());
                intent.putExtra("DishName",data.getDishName());
                intent.putExtra("orderID",orderID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_all_comment:
                if(!TextUtils.isEmpty(comment_0.getText())){
                    //TODO
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(date);
                    //post请求简洁版实现
                    HttpParams params = new HttpParams();
                    params.put("dishid",0);
                    params.put("content",comment_0.getText().toString());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        op.clear();
    }
}
