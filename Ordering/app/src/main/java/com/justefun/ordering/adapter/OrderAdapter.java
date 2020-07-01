package com.justefun.ordering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.justefun.ordering.R;
import com.justefun.ordering.entity.DishData;
import com.justefun.ordering.entity.OrderData;
import com.justefun.ordering.utils.PicassoUtils;

import java.util.List;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.adapter
 * @time 2018/12/16 21:22
 * @describe 订单适配器
 */
public class OrderAdapter extends BaseAdapter {

    private Context mContext;
    private List<OrderData> mList;
    //布局加载器
    private LayoutInflater inflater;
    private OrderData data;

    private int width, height;
    private WindowManager wm;

    public OrderAdapter(Context mContext, List<OrderData> mList){
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
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
            convertView = inflater.inflate(R.layout.order_item,null);
            viewHolder.iv_menu = (ImageView) convertView.findViewById(R.id.iv_menu);
            viewHolder.tv_menu_name = (TextView) convertView.findViewById(R.id.tv_menu_name);
            viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);

            //设置缓存
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //设置数据
        data = mList.get(position);

        PicassoUtils.loadImageViewSize(data.getDishImg(), width/3, 250, viewHolder.iv_menu);
        viewHolder.tv_menu_name.setText(data.getDishName());
        viewHolder.tv_num.setText(String.format("%s", data.getNumber()));
        viewHolder.tv_price.setText(String.format("%s", data.getPrice()));
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_menu;
        private TextView tv_menu_name;
        private TextView tv_price;
        private TextView tv_num;

    }
}
