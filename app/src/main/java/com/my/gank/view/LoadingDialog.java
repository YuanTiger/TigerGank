package com.my.gank.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.my.gank.R;
import com.my.gank.utils.ScreenUtil;


/**
 * 带有loading状态和文本内容的dialog。
 *
 */
public class LoadingDialog extends Dialog {

    /**
     * 使用的上下文，通常是你的{@link android.app.Application}或者是{@link android.app.Activity}。
     */
    Context context;
    /**
     * Dialog的布局文件
     */
    View mainView;

    public LoadingDialog(Context context) {
        super(context, R.style.progress_dialog);
//        super(context, R.style.animation_progress_dialog);
        this.context = context;
        init();
    }

    public LoadingDialog(Context context, int theme) {
        super(context, R.style.progress_dialog);
//        super(context, R.style.animation_progress_dialog);
        this.context = context;
        init();
    }

    /**
     * 初始化默认的布局样式。
     */
    protected void init() {
        mainView = LayoutInflater.from(context).inflate(R.layout.layout_progress_dialog, null);
        setContentView(mainView);

        // 设置为background
//        ImageView imageView = (ImageView) mainView.findViewById(R.id.pb_imageView);
//        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
//        animationDrawable.start();


        Window dialogWindow = getWindow();
//        dialogWindow.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        int width = ScreenUtil.dp2Px(160);
        int height =  ScreenUtil.dp2Px(90);

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = width;
        lp.height = height;
        dialogWindow.setAttributes(lp);

        setCanceledOnTouchOutside(false);
        setOnKeyListener(keylistener);
    }

    /**
     * 设置对话框的提示消息。
     *
     * @param message
     * @return
     */
    public LoadingDialog setMessage(String message) {
        TextView messageTv = (TextView) mainView.findViewById(R.id.message);
        if (messageTv != null) {
            messageTv.setText(message);
        }
        return this;
    }

    OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };
}