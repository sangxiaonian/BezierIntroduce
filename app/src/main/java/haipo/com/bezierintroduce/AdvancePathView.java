package haipo.com.bezierintroduce;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/21 9:59
 */
public class AdvancePathView extends RelativeLayout {

    protected Random random;
    private Path mPath;
    private Paint mPaint;
    protected PointF pointFStart, pointFEnd, pointFFirst, pointFSecond;
    protected Bitmap bitmap;

    private int[]colors ={Color.WHITE,Color.CYAN,Color.YELLOW,Color.BLACK ,Color.LTGRAY,Color.GREEN,Color.RED};

    public AdvancePathView(Context context) {
        super(context);
        initView();
    }

    public AdvancePathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AdvancePathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        setBackgroundColor(Color.WHITE);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);

        mPath = new Path();

        pointFStart = new PointF();
        pointFFirst = new PointF();
        pointFSecond = new PointF();
        pointFEnd = new PointF();

        random = new Random();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
    }


    protected void initPoint() {
        pointFStart.x = getMeasuredWidth() / 2;
        pointFStart.y = getMeasuredHeight() - 10;

        pointFEnd.y = 10;
        pointFEnd.x = getMeasuredWidth() / 2;

        pointFFirst.x = 10;
        pointFSecond.x = getMeasuredWidth() - 10;
        pointFSecond.y = getMeasuredHeight() / 4;
        pointFFirst.y = getMeasuredHeight() * 3 / 4;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPoint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(pointFFirst.x, pointFFirst.y, 10, mPaint);
        canvas.drawCircle(pointFSecond.x, pointFSecond.y, 10, mPaint);
        canvas.drawCircle(pointFEnd.x, pointFEnd.y, 10, mPaint);
        canvas.drawCircle(pointFStart.x, pointFStart.y, 10, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        mPath.moveTo(pointFStart.x, pointFStart.y);
        mPath.cubicTo(pointFFirst.x, pointFFirst.y, pointFSecond.x, pointFSecond.y, pointFEnd.x, pointFEnd.y);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
    }

    private Bitmap drawHeart(int color) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.drawColor(color, PorterDuff.Mode.SRC_ATOP);
        canvas.setBitmap(null);
        return newBitmap;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        Button button = new Button(getContext());
        button.setText("添加一个心");

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addHeart();
            }
        });

        addView(button);
    }

    public void addHeart() {
        ImageView imageView = new ImageView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_BOTTOM);
        imageView.setImageBitmap(drawHeart(colors[random.nextInt(colors.length)]));
        addView(imageView, params);
        moveHeart(imageView);
    }



    private void moveHeart(final ImageView view){
        PointF pointFFirst = this.pointFFirst;
        PointF pointFSecond = this.pointFSecond;
        PointF pointFStart = this.pointFStart;
        PointF pointFEnd = this.pointFEnd;


        ValueAnimator animator = ValueAnimator.ofObject(new TypeE(pointFFirst, pointFSecond), pointFStart, pointFEnd);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF value = (PointF) animation.getAnimatedValue();
                view.setX(value.x);
                view.setY(value.y);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                AdvancePathView.this.removeView(view);
            }
        });

        ObjectAnimator af = ObjectAnimator.ofFloat(view, "alpha", 1f, 0);
        af.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                AdvancePathView.this.removeView(view);
            }
        });

       AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.play(animator).with(af);
        set.start();

    }



    /**
     * 绘制一个增值器
     */
    class TypeE implements TypeEvaluator<PointF> {

        private PointF pointFFirst,pointFSecond;

        public TypeE(PointF start,PointF end){
            this.pointFFirst =start;
            this.pointFSecond = end;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF result = new PointF();
            float left = 1 - fraction;
            result.x = (float) (startValue.x*Math.pow(left,3)+3*pointFFirst.x*Math.pow(left,2)*fraction+3*pointFSecond.x*Math.pow(fraction, 2)*left+endValue.x*Math.pow(fraction,3));
            result.y= (float) (startValue.y*Math.pow(left,3)+3*pointFFirst.y*Math.pow(left,2)*fraction+3*pointFSecond.y*Math.pow(fraction, 2)*left+endValue.y*Math.pow(fraction,3));
            return result;
        }
    }


}
