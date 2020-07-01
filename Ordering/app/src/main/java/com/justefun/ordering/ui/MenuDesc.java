package com.justefun.ordering.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.justefun.ordering.MainActivity;
import com.justefun.ordering.R;
import com.justefun.ordering.adapter.CommentAdapter;
import com.justefun.ordering.adapter.DishAdapter;
import com.justefun.ordering.dataBase.DataOperation;
import com.justefun.ordering.entity.CommentData;
import com.justefun.ordering.entity.DishData;
import com.justefun.ordering.entity.OrderData;
import com.justefun.ordering.utils.DisplayUtils;
import com.justefun.ordering.utils.L;
import com.justefun.ordering.utils.PicassoUtils;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.ui
 * @time 2018/12/17 21:10
 * @describe 菜品具体类
 */
public class MenuDesc extends BaseActivity implements View.OnClickListener {

    private int width, height;
    private WindowManager wm;
    private ImageView menu_img;
    private TextView tv_menu_name;
    private TextView tv_menu_desc;
    private TextView tv_price;
    private TextView tv_num;
    private Button btn_add, btn_sub;

    private ListView lv_comment;

    private List<CommentData> mList = new ArrayList<>();
    //数据库操作对象
    private DataOperation op;
    //订单数据
    private OrderData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_desc);
        op = new DataOperation(this);
        Intent intent = getIntent();
//       initData();
        initView(intent);

    }

    private void initData() {
//        for (int i = 0; i < 8; i++) {
//            CommentData data = new CommentData();
//            data.setContent("顾客菜品评论" + i);
//            data.setTime("2018-12-" + (i * 2 + 2));
//            mList.add(data);
//        }
    }

    private void initView(Intent intent) {
        wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        menu_img = findViewById(R.id.menu_img);
        tv_menu_name = findViewById(R.id.tv_menu_name);
        tv_menu_desc = findViewById(R.id.tv_menu_desc);
        tv_price = findViewById(R.id.tv_price);
        lv_comment = findViewById(R.id.lv_comment);
        tv_num = findViewById(R.id.tv_num);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_sub = findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(this);


        //获取并设置数据
        Bundle bundle = intent.getBundleExtra("data");
        String DishName = bundle.getString("name");

        if (op.CheckIsDataAlreadyIn(DishName)) {
            data = op.getItem(DishName);
        } else {
            data = new OrderData();
            data.setDishID(bundle.getString("dishId"));
            data.setDishName(DishName);
            data.setDishImg(bundle.getString("img"));
            data.setPrice(Double.parseDouble(bundle.getString("price")));
            data.setNumber(0);
        }

        //初始化控件
        PicassoUtils.loadImageViewSize(data.getDishImg(), width, DisplayUtils.dip2px(this, 180), menu_img);
        tv_menu_name.setText(DishName);
        tv_menu_desc.setText(bundle.getString("desc"));
        tv_price.setText(bundle.getString("price"));
        tv_num.setText(data.getNumber() + "");


        //设置标题
        getSupportActionBar().setTitle(data.getDishName());

        //解析接口
        String url = "http://47.107.79.142:6618/Comment/ListCommentByDishid?dishid=" + data.getDishID();
        L.i("JSON:"+url);
        L.i("JSON:"+data.getDishID());
        HttpCallback callback = new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        };
        new RxVolley.Builder()
                .url(url)
                .httpMethod(RxVolley.Method.GET) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
                .cacheTime(0) //default: get 5min, post 0min
                .contentType(RxVolley.ContentType.FORM)//default FORM or JSON
                .shouldCache(false) //default: get true, post false
                .callback(callback)
                .encoding("UTF-8") //default
                .doTask();

    }

    @Override
    public void onClick(View v) {
        int num = data.getNumber();
        switch (v.getId()) {
            case R.id.btn_add:
                num++;
                data.setNumber(num);
                tv_num.setText(num + "");
                break;
            case R.id.btn_sub:
                if (num > 0) {
                    num--;
                    data.setNumber(num);
                    tv_num.setText(num + "");
                }
        }
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONArray jsonList = new JSONArray(t);
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject json = (JSONObject) jsonList.get(i);
                CommentData data = new CommentData();
                data.setContent(json.getString("content"));
                data.setTime(json.getString("time"));
                mList.add(data);
            }
            CommentAdapter adapter = new CommentAdapter(this, mList);
            lv_comment.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (data.getNumber() > 0) {
            if (op.CheckIsDataAlreadyIn(data.getDishName())) {
                op.update(data);
            } else {
                op.add(data);
            }
        } else if (data.getNumber() == 0) {
            if (op.CheckIsDataAlreadyIn(data.getDishName())) {
                op.delete(data);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
