package com.justefun.ordercustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView dish_list;
    private List<DishData> mList = new ArrayList<>();

    private List<Integer> orderidlist = new ArrayList<>();

    private List<String> orderpricelist = new ArrayList<>();
    private List<String> ordertimelist = new ArrayList<>();
    private List<String> ordertablenumberlist = new ArrayList<>();
    private DishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉bar上方的阴影
//        getSupportActionBar().setElevation(0);
        setContentView(R.layout.dishprint);
        // initdishfagment();

        initdata();
        findView();
    }

    private void initdata() {
        String url = "http://47.107.79.142:6618/Myorder/ListMyorder";
        new RxVolley.Builder()
                .url(url)
                .httpMethod(RxVolley.Method.GET) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
                .cacheTime(0) //default: get 5min, post 0min
                .contentType(RxVolley.ContentType.FORM)//default FORM or JSON
                .shouldCache(false) //default: get true, post false
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        try {
                            L.i("yyyyxx");
                            JSONArray jsonList = new JSONArray(t);
                            for (int i = 0; i < jsonList.length(); i++) {
                                JSONObject json = (JSONObject) jsonList.get(i);
                                int orderid;

                                String orderprice;
                                String tablenum;
                                String ordertime;

                                tablenum = json.getString("tablenumber");

                                if (tablenum.charAt(0) == '0') {//厨师未做订单
                                    orderid = Integer.parseInt(json.getString("orderid"));
                                    orderprice = json.getString("orderprice");
                                    ordertime = json.getString("ordertime");
                                    orderidlist.add(orderid);

                                    orderpricelist.add(orderprice);
                                    ordertimelist.add(ordertime);
                                    ordertablenumberlist.add(tablenum);

                                    L.i("yyyy"+orderid);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        int ii;
                        for (int i = 0; i < orderidlist.size(); i++) {
                            ii = orderidlist.get(i);
                            L.i("yyyy000"+ii);
                            //解析接口
                            String url = "http://47.107.79.142:6618/Orderdetail/ListOrderdetailByid?orderid=" + ii;
                            final int finalIi = ii;
                            new RxVolley.Builder()
                                    .url(url)
                                    .httpMethod(RxVolley.Method.GET) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
                                    .cacheTime(0) //default: get 5min, post 0min
                                    .contentType(RxVolley.ContentType.FORM)//default FORM or JSON
                                    .shouldCache(false) //default: get true, post false
                                    .callback(new HttpCallback() {
                                        @Override
                                        public void onSuccess(String t) {
                                            L.i("yyyy1111"+t);
                                            try {

                                                JSONArray jsonList = new JSONArray(t);
                                                for (int i = 0; i < jsonList.length(); i++) {
                                                    JSONObject json = (JSONObject) jsonList.get(i);

                                                    //L.i("yyyy4445" + json.getString("name"));

                                                    DishData data = new DishData();
                                                    data.setDishName(json.getString("name"));
                                                    data.setDishNum(Integer.parseInt(json.getString("count")));
                                                  //  data.setOrderID(1);
                                                    data.setOrderID(finalIi);

                                                  //  L.i("yyyy4444" + data.getDishName());
                                                    //Log.i("yyyy","yyyy"+data.getDishName());
                                                    mList.add(data);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            adapter = new DishAdapter(MainActivity.this, mList);
                                            dish_list.setAdapter(adapter);
                                        }
                                    }).encoding("UTF-8") //default
                                    .doTask();
                        }
                    }

                    @Override
                    public void onFailure(VolleyError error) {
                        super.onFailure(error);
                        L.i("yyyyx0"+error);
                    }
                }).encoding("UTF-8") //default
                .doTask();
    }

        /*for (int i = 0; i < 5; i++) {
            DishData data = new DishData();
            data.setDishName("样例菜品" + i);
            data.setDishNum(0);
            data.setOrderID(1);
            mList.add(data);
        }*/

    private void findView() {
        dish_list = findViewById(R.id.dishprint_list);

        //DishAdapter adapter = new DishAdapter(this, mList);
        //dish_list.setAdapter(adapter);
        dish_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //post请求简洁版实现
                HttpParams params = new HttpParams();

              //  L.i("yyyy+++"+orderidlist.get(position));

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(date);
                //L.i("yyyy+++"+dateString);
                params.put("orderid",orderidlist.get(position));
                params.put("orderprice",orderpricelist.get(position));
                params.put("ordertime",dateString);
                String stringls=ordertablenumberlist.get(position).substring(1);

                L.i("yyyy+++&&&"+stringls);
                params.put("tablenumber",stringls);

                RxVolley.post("http://47.107.79.142:6618/Myorder/update", params, new HttpCallback(){
                    @Override
                    public void onSuccess(String t) {
                        L.i("yyyyy成功返回："+t);
                    }
                    @Override
                    public void onFailure(VolleyError error) {
                        super.onFailure(error);
                        L.i("yyyyyshibai返回："+error);
                    }
                });
                orderidlist.remove(position);
                orderpricelist.remove(position);
                ordertimelist.remove(position);
                ordertablenumberlist.remove(position);

                adapter.notifyDataSetChanged();

            }
        });
    }



}
