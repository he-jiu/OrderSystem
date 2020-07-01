package com.justefun.ordering.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.justefun.ordering.R;
import com.justefun.ordering.adapter.DishAdapter;
import com.justefun.ordering.entity.DishData;
import com.justefun.ordering.ui.MenuDesc;
import com.justefun.ordering.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.fragment
 * @time 2018/12/11 11:19
 * @describe 菜品展示页面
 */
public class MenuFragment extends Fragment {

    private ListView menu_list;

    private List<DishData> mList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_menu,null);
        initData();
        //L.i("JSON:"+mList.get(1).getDishName());
        findView(view);

        return view;
    }

    private void initData() {
//        for (int i = 0; i < 5; i++) {
//            DishData data = new DishData();
//            data.setDishID(String.valueOf(i));
//            data.setDishImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545372909652&di=08d9764fc61f8f349465d559229a77aa&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fdc54564e9258d1096ac75d9bda58ccbf6c814df7.jpg");
//            data.setDishName("样例菜品"+i);
//            data.setIntroduce("菜品"+i+"的描述信息");
//            data.setDishPrice(2.5*i+1);
//            mList.add(data);
//        }

    }

    private void findView(View view) {

        menu_list = view.findViewById(R.id.menu_list);


        //解析接口
        String url = "http://47.107.79.142:6618/Dish/ListDish";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.i("JSON:"+t);
                parsingJson(t);
            }
        });


        //设置ListView的点击事件
        menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DishData data = mList.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("dishId",data.getDishID());
                bundle.putString("img",data.getDishImg());
                bundle.putString("name",data.getDishName());
                bundle.putString("desc",data.getIntroduce());
                bundle.putString("price",data.getDishPrice()+"");
                Intent intent = new Intent(getActivity(),MenuDesc.class);
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONArray jsonList = new JSONArray(t);
            for(int i = 1;i<jsonList.length();i++){
                JSONObject json = (JSONObject) jsonList.get(i);
                DishData data = new DishData();
                data.setDishID(json.getString("id"));
                data.setDishName(json.getString("name"));
                data.setIntroduce(json.getString("type"));
                data.setDishImg(json.getString("url"));
                data.setDishPrice(json.getDouble("dishprice"));
                mList.add(data);
            }
            DishAdapter adapter = new DishAdapter(getActivity(),mList);
            menu_list.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
