package com.justefun.ordering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.justefun.ordering.R;
import com.justefun.ordering.entity.CommentData;
import com.justefun.ordering.entity.DishData;

import java.util.List;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.adapter
 * @time 2018/12/16 21:22
 * @describe 评论适配器
 */
public class CommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<CommentData> mList;
    //布局加载器
    private LayoutInflater inflater;
    private CommentData data;

    public CommentAdapter(Context mContext, List<CommentData> mList){
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
            convertView = inflater.inflate(R.layout.comment_item,null);
//            viewHolder.iv_menu = (ImageView) convertView.findViewById(R.id.iv_menu);
            viewHolder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
            viewHolder.comment_time = (TextView) convertView.findViewById(R.id.comment_time);
            //设置缓存
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        data = mList.get(position);
//        viewHolder.iv_menu.setImageBitmap();
        viewHolder.comment_content.setText(data.getContent());
        viewHolder.comment_time.setText(data.getTime());

        return convertView;
    }

    class ViewHolder{

        private TextView comment_content;
        private TextView comment_time;

    }
}
