package com.justefun.ordering.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * @author Justefun
 * @projectname: SmartButler
 * @class name：com.justefun.smartbutler.utils
 * @class describe
 * @time 2018/12/7 17:51
 * @describe 工具统一类
 */
public class UtilTools {

    //设置字体
    public static void setFont(Context mContext, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }
}
