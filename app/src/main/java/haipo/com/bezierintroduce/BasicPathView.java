package haipo.com.bezierintroduce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/20 17:35
 */
public class BasicPathView extends LinearLayout {

    private Paint mPaint;
    private Path mPath;
    private float startX, startY, endX, endY, touchX, touchY;

    private boolean isFill;

    public BasicPathView(Context context) {
        super(context);
        initView();
    }

    public BasicPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BasicPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPath = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        startX = getMeasuredWidth() / 5;
        startY = endY = getMeasuredHeight() / 2;
        endX = getMeasuredWidth() * 4 / 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(startX, startY, 10, mPaint);
        canvas.drawCircle(endX, endY, 10, mPaint);
        canvas.drawCircle(touchX, touchY, 10, mPaint);
        mPaint.setTextSize(20);
        canvas.drawText("这是一阶贝塞尔曲线,就一条直线,没什么好说的", startX, startY / 4, mPaint);

        mPaint.setColor(Color.BLUE);
        if (isFill) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        //绘制一阶
        mPath.moveTo(startX, startY / 3);
        mPath.lineTo(endX, endY / 3);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();


        mPath.moveTo(startX, startY);
        mPath.quadTo(touchX, touchY, endX, endY);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        postInvalidate();
        return true;
    }

    private int index;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final Button button = new Button(getContext());
        button.setText("填充");

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index % 2 == 0) {
                    isFill = true;
                    button.setText("不填充");
                } else {
                    isFill = false;
                    button.setText("填充");

                }
                index++;
            }
        });

        addView(button);
    }
}
