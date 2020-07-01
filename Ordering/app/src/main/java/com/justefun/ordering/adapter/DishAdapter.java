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
import com.justefun.ordering.entity.CommentData;
import com.justefun.ordering.entity.DishData;
import com.justefun.ordering.utils.PicassoUtils;

import java.util.List;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.adapter
 * @time 2018/12/16 21:22
 * @describe 菜品适配器
 */
public class DishAdapter extends BaseAdapter{

    private Context mContext;
    private List<DishData> mList;
    //布局加载器
    private LayoutInflater inflater;
    private DishData data;
    private int width, height;
    private WindowManager wm;

    public DishAdapter(Context mContext,List<DishData> mList){
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
            convertView = inflater.inflate(R.layout.menu_item,null);

           viewHolder.iv_menu = convertView.findViewById(R.id.iv_menu);
            viewHolder.tv_menu_name = convertView.findViewById(R.id.tv_menu_name);
            viewHolder.tv_menu_desc = convertView.findViewById(R.id.tv_menu_desc);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_price);
//            viewHolder.tv_num = convertView.findViewById(R.id.tv_num);
//            viewHolder.btn_add = convertView.findViewById(R.id.btn_add);
//            viewHolder.btn_sub = convertView.findViewById(R.id.btn_sub);
            //设置缓存
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置标记
//        viewHolder.tv_num.setTag(R.id.tv_num,position);
//        viewHolder.btn_add.setTag(R.id.btn_add,position);
//        final ViewHolder finalViewHolder = viewHolder;
//        viewHolder.btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int num = Integer.parseInt(finalViewHolder.tv_num.getText().toString());
//                num++;
//                finalViewHolder.tv_num.setText(num);
//            }
//        });
//        viewHolder.btn_sub.setTag(R.id.btn_sub,position);
//        viewHolder.btn_sub.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int num = Integer.parseInt(finalViewHolder.tv_num.getText().toString());
//                if(num>0){
//                    num--;
//                    finalViewHolder.tv_num.setText(num);
//                }
//            }
//        });

        //设置数据
        data = mList.get(position);


        PicassoUtils.loadImageViewSize(data.getDishImg(), width/3, 250, viewHolder.iv_menu);
        viewHolder.tv_menu_name.setText(data.getDishName());
        viewHolder.tv_menu_desc.setText(data.getIntroduce());
        viewHolder.tv_price.setText(String.format("%s", data.getDishPrice()));
        return convertView;
    }


    class ViewHolder{
        private ImageView iv_menu;
        private TextView tv_menu_name;
        private TextView tv_menu_desc;
        private TextView tv_price;
//        private TextView tv_num;
//        private Button btn_add;
//        private Button btn_sub;
    }
}
