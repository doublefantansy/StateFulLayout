package hzkj.cc.stateful;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StateFulLayout extends LinearLayout {
    public final static int LOADING = 10000;
    public final static int CONTENT = 10001;
    public final static int EMPTY = 10002;
    public final static int NETERROR = 10003;
    View myView;
    View view;
    LinearLayout refresh;
    ImageView loadingImage;
    ImageView imageView;
    TextView textView;
    RefreshListenner refreshListenner;
    ValueAnimator valueAnimator;

    public StateFulLayout(Context context) {
        super(context);
    }

    public StateFulLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(RefreshListenner listenner, View view) {
        this.view = view;
        refreshListenner = listenner;
        this.setOrientation(VERTICAL);
        myView = LayoutInflater.from(getContext())
                .inflate(R.layout.empty, null);
        imageView = myView.findViewById(R.id.image);
        textView = myView.findViewById(R.id.message);
        loadingImage = myView.findViewById(R.id.loadingImage);
        refresh = myView.findViewById(R.id.refresh);
        refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshListenner.refresh();
                showState(LOADING);
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myView.setLayoutParams(layoutParams);
        addView(myView);
        valueAnimator = ValueAnimator.ofFloat(0f, 360f);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imageView.setRotation((Float) animation.getAnimatedValue());
            }
        });
//        showState(LOADING);
//        refreshListenner.refresh();
    }

    public void showState(int status) {
        imageView.setRotation(0);
        valueAnimator.cancel();
        if (status == CONTENT) {
            view.setVisibility(VISIBLE);
            myView.setVisibility(GONE);
        } else {
            view.setVisibility(GONE);
            myView.setVisibility(VISIBLE);
            if (status == LOADING) {
                Log.d("ccnb", "1");
                textView.setVisibility(GONE);
                refresh.setVisibility(GONE);
                imageView.setImageResource(R.drawable.ic_iconfontshuaxin);
                textView.setTextColor(getResources().getColor(R.color.blue));
                valueAnimator.start();
            } else {
//                imageView.setRotation(0);
//                valueAnimator.cancel();
//                revolve(false);
                textView.setVisibility(VISIBLE);
                refresh.setVisibility(VISIBLE);
                if (status == EMPTY) {
                    textView.setText("暂无数据");
                    imageView.setImageResource(R.drawable.ic_kong_3);
                    textView.setTextColor(getResources().getColor(R.color.red));
                } else if (status == NETERROR) {
                    textView.setText("网络错误");
                    imageView.setImageResource(R.drawable.ic_wangluocuowu);
                    textView.setTextColor(getResources().getColor(R.color.red));
                }
            }
        }
    }


    public interface RefreshListenner {
        void refresh();
    }
}
