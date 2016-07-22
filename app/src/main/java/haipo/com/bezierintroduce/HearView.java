package haipo.com.bezierintroduce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/21 12:00
 */
public class HearView extends AdvancePathView {

    public HearView(Context context) {
        super(context);
    }

    public HearView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onDraw(Canvas canvas) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointFStart = new PointF();
        pointFFirst = new PointF();
        pointFSecond = new PointF();
        pointFEnd = new PointF();

        pointFStart.x = getMeasuredWidth() / 2-bitmap.getWidth()/2;
        pointFStart.y = getMeasuredHeight() - bitmap.getHeight();

        pointFEnd.y = 0;
        pointFEnd.x = random.nextFloat()*getMeasuredWidth();

        pointFFirst.x = random.nextFloat()*getMeasuredWidth();
        pointFSecond.x = getMeasuredWidth() - pointFFirst.x;
        pointFSecond.y = random.nextFloat()*getMeasuredHeight() / 2+getMeasuredHeight()/2;
        pointFFirst.y = random.nextFloat()*getMeasuredHeight()  / 2;
        Log.i("TAG","出发了");
        addHeart();
        return true;
    }

    @Override
    protected void initPoint() {

    }


}
