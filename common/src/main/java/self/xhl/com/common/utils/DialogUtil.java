package self.xhl.com.common.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import self.xhl.com.common.R;

/**
 * @作者 xhl
 * @des 默认的Dialog AlertDialog
 * @创建时间 2018/7/11 14:15
 */
public class DialogUtil {

    public static AlertDialog showCusDialog(Context context,String btnTextCancel, final View.OnClickListener ok, String content, String title,boolean shouldFuzzy) {
        return getAlertDialog(context, btnTextCancel,ok, content, title,-1,shouldFuzzy);
    }

    public static AlertDialog showCusDialog(Context context,String btnTextCancel, final View.OnClickListener ok, String content, String title,int imageResourceID,boolean shouldFuzzy)
    {
        return getAlertDialog(context, btnTextCancel,ok, content, title,imageResourceID,shouldFuzzy);
    }

    @NonNull
    private static AlertDialog getAlertDialog(Context context, String btnTextCancel,final View.OnClickListener ok, String content, String title,int imageResourceID,boolean shouldFuzzy) {
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        Window window = dlg.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
//        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.common_mask_color)));
        if(shouldFuzzy)
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BFFFFFFF")));
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setContentView(R.layout.dialog_custom);
        ((TextView) window.findViewById(R.id.txt_content)).setText(content);
        TextView textView=  window.findViewById(R.id.txt_title);
        LinearLayout imageContainer= window.findViewById(R.id.imageContainer);
        ImageView imageView= window.findViewById(R.id.image);
        if (title != null) {
            textView.setText(title);
            textView.setVisibility(View.VISIBLE);
        }
        else
            textView.setVisibility(View.GONE);

        if(imageResourceID!=-1)
        {
            imageContainer.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(context.getResources().getDrawable(imageResourceID));
        }
        else
            imageContainer.setVisibility(View.GONE);
        // 确认按钮
        TextView btnOk =  window.findViewById(R.id.btn_ok);
        TextView cancel =  window.findViewById(R.id.btn_cancel);
        View line = window.findViewById(R.id.line_vertical);
        if (ok != null) {
            if(btnTextCancel.isEmpty())
                cancel.setText("取消");
            else
                cancel.setText(btnTextCancel);
            btnOk.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dlg.cancel();
                    ok.onClick(v);
                }
            });
        } else {
            cancel.setText("确定");
            line.setVisibility(View.GONE);
            btnOk.setVisibility(View.GONE);
        }

        // 关闭alert对话框架
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
        return dlg;
    }


}
