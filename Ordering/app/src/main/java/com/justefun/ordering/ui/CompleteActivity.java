package com.justefun.ordering.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.justefun.ordering.R;
import com.justefun.ordering.dataBase.DataOperation;
import com.justefun.ordering.utils.PicassoUtils;
import com.justefun.ordering.view.CustomDialog;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.ui
 * @time 2018/12/23 14:46
 * @describe TODO
 */
public class CompleteActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_pay,btn_comment,btn_over;

    //提示框
    private CustomDialog dialog;
    //图片
    private ImageView iv_pay;

    private String orderID;

    //数据库操作对象
    private DataOperation op;
    private Boolean isPay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        op = new DataOperation(this);
        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");
        initView();
    }

    private void initView() {
        btn_pay = findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(this);
        btn_comment = findViewById(R.id.btn_comment);
        btn_comment.setOnClickListener(this);
        btn_over = findViewById(R.id.btn_over);
        btn_over.setOnClickListener(this);
        isPay = false;
        //初始化提示框
        dialog = new CustomDialog(this, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.pay_item,
                R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        iv_pay = dialog.findViewById(R.id.iv_pay);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pay:
                isPay =true;
                dialog.show();
                break;
            case R.id.btn_comment:
                if (isPay) {
                    Intent intent = new Intent(this, CommentActivity.class);
                    intent.putExtra("orderID",orderID);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this,"请先付款！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_over:
                if (isPay) {
                    op.clear();
                    finish();
                }else {
                    Toast.makeText(this,"请先付款！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
