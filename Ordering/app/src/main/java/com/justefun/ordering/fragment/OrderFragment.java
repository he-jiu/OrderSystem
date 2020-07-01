package com.justefun.ordering.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.justefun.ordering.R;
import com.justefun.ordering.adapter.OrderAdapter;
import com.justefun.ordering.dataBase.DataOperation;
import com.justefun.ordering.entity.DishData;
import com.justefun.ordering.entity.OrderData;
import com.justefun.ordering.ui.CommentActivity;
import com.justefun.ordering.ui.CompleteActivity;
import com.justefun.ordering.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.fragment
 * @time 2018/12/11 11:20
 * @describe 订单页面
 */
public class OrderFragment extends Fragment implements View.OnClickListener {

    private boolean isPrepared = false;

    private ListView order_list;

    private Double sum;
    private TextView tv_sum;
    //按钮
    private Button btn_clear,btn_submit;


    //适配器
    OrderAdapter adapter;
    private List<OrderData> mList = new ArrayList<>();

    //数据库操作对象
    private DataOperation op;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_order,null);
        op = new DataOperation(getActivity());

        isPrepared = true;
        initData();
        findView(view);
        setUserVisibleHint(getUserVisibleHint());
        return view;
    }

    private void initData() {
        mList = op.queryMany();
        sum = op.getSum();
    }

    private void findView(View view) {

        btn_clear=view.findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        btn_submit = view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        order_list = view.findViewById(R.id.order_list);
        adapter = new OrderAdapter(getActivity(),mList);
        order_list.setAdapter(adapter);
        tv_sum = view.findViewById(R.id.tv_sum);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isPrepared && isVisibleToUser) {
            //加载数据
            initData();
            adapter = new OrderAdapter(getActivity(),mList);
            order_list.setAdapter(adapter);
            tv_sum.setText(sum.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_clear:
                clearList();
                adapter = new OrderAdapter(getActivity(),mList);
                order_list.setAdapter(adapter);
                tv_sum.setText(sum.toString());
                break;
            case R.id.btn_submit:
                if(!mList.isEmpty()){
                    showInputDialog();
                }else {
                    Toast.makeText(getActivity(),"订单为空，无法提交！",Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    private void showInputDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(getActivity());
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(getActivity());
        inputDialog.setTitle("请您输入当前桌号：").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!TextUtils.isEmpty(editText.getText())) {
//                            clearList();
//                            adapter = new OrderAdapter(getActivity(), mList);
//                            order_list.setAdapter(adapter);
//                            tv_sum.setText(sum.toString());
                            String  tableNum = editText.getText().toString();
                            submitList(tableNum);
                            Toast.makeText(getActivity(),
                                    "提交成功，桌号为："+tableNum+",祝您用餐愉快！",
                                    Toast.LENGTH_SHORT).show();


                        }else {
                            Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        inputDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        inputDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearList();
    }
    //清空订单
    public void clearList(){
        op.clear();
        mList.clear();
        sum = 0.0;
    }
    //提交订单
    private void submitList(String tableNum) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        //post请求简洁版实现
        final HttpParams params = new HttpParams();
        params.put("orderprice", sum.toString());
        params.put("ordertime",dateString);
        params.put("tablenumber","0"+tableNum);



        RxVolley.post("http://47.107.79.142:6618/Myorder/insert", params, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.i("成功返回：" + t);
                new RxVolley.Builder()
                        .url("http://47.107.79.142:6618/Myorder/Maxid")
                        .httpMethod(RxVolley.Method.GET) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
                        .cacheTime(0) //default: get 5min, post 0min
                        .contentType(RxVolley.ContentType.FORM)//default FORM or JSON
                        .shouldCache(false) //default: get true, post false
                        .callback(new HttpCallback() {
                            @Override
                            public void onSuccess(String t1) {
                                L.i("成功返回：" + t1);
                                for (int i = 0; i < mList.size(); i++) {
                                    OrderData data = mList.get(i);
                                    L.i("成功返回：hhhhhhhh" + data.getDishName() + data.getDishID() + "int:" + Integer.parseInt(data.getDishID()));
                                    HttpParams param = new HttpParams();
                                    param.put("orderid", t1);
                                    param.put("dishid", Integer.parseInt(data.getDishID()));
                                    param.put("dishprice", String.valueOf(data.getPrice()));
                                    param.put("count", String.valueOf(data.getNumber()));
                                    param.put("ps", "1");
                                    RxVolley.post("http://47.107.79.142:6618/Orderdetail/insert", param, new HttpCallback() {
                                        @Override
                                        public void onSuccess(String t2) {
                                            L.i("成功返回：" + t2);
                                        }

                                        @Override
                                        public void onFailure(VolleyError error) {
                                            L.i("成功返回：才怪" + error);
                                        }
                                    });
                                }
                                Intent intent = new Intent(getActivity(), CompleteActivity.class);
                                intent.putExtra("orderID", t1);
                                startActivity(intent);
                            }
                        })
                        .encoding("UTF-8") //default
                        .doTask();
            }
            });

    }
}
