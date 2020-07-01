package com.justefun.ordercustomer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DishAdapter extends BaseAdapter {
    private Context mContext;
    private List<DishData> mList;
    //布局加载器
    private LayoutInflater inflater;
    private DishData data;

    public DishAdapter(Context mContext,List<DishData> mList){
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //第一次加载
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.dish,null);
//            viewHolder.iv_menu = (ImageView) convertView.findViewById(R.id.iv_menu);
            viewHolder.tv_dish_name = (TextView) convertView.findViewById(R.id.dish_name);
            viewHolder.tv_dish_num = (TextView) convertView.findViewById(R.id.dish_num);
            viewHolder.tv_orderid = (TextView) convertView.findViewById(R.id.orderid);
            //设置缓存
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        data = mList.get(position);
//        viewHolder.iv_menu.setImageBitmap();
        viewHolder.tv_dish_name.setText(data.getDishName());
        viewHolder.tv_dish_num.setText(String.valueOf(data.getDishNum()));
        viewHolder.tv_orderid.setText(String.valueOf(data.getOrderID()));
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_menu;
        private TextView tv_dish_name;
        private TextView tv_dish_num;
        private TextView tv_orderid;
    }
}
