package yeell.com.customviewproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yeell on 2017/9/28.
 */

public class ButtonCustomView extends View {

    private Paint mPaint;
    private Paint mPaintTwo;
    private Path mPath;

    private int mWidth = 0;
    private int mHeight = 0;

    private int mRadius = 0;

    private int okWidthProgress = 0;

    boolean startDrawPath = false;

    private PathMeasure mPathMeasure;
    private Path mDesPath;

    private Paint mTextPaint;

    private String content = "开始动画";

    private Rect mTextRect;

    private float textAlpha = 1;

    public float getTextAlpha() {
        return textAlpha;
    }

    public void setTextAlpha(float textAlpha) {
        this.textAlpha = textAlpha;
        invalidate();
    }

    public int getOkWidthProgress() {
        return okWidthProgress;
    }

    public void setOkWidthProgress(int okWidthProgress) {
        this.okWidthProgress = okWidthProgress;
//        Log.e("okWidthProgress",okWidthProgress+"");
        invalidate();
    }

    public Rect getTextRect() {
        return mTextRect;
    }

    public void setTextRect(Rect mTextRect) {
        this.mTextRect = mTextRect;
    }

    public boolean isStartDrawPath() {
        return startDrawPath;
    }

    public void setStartDrawPath(boolean startDrawPath) {
        this.startDrawPath = startDrawPath;
        invalidate();
    }

    public int getRadius() {
        return mRadius;
    }

    /**
     * 代表的百分比
     *
     * @param mRadius
     */
    public void setRadius(int mRadius) {
        mRadius = (mWidth - mHeight) / 2 * mRadius / 100;
        this.mRadius = mRadius;
        invalidate();
    }

    public ButtonCustomView(Context context) {
        super(context);
        init();
    }

    public ButtonCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = width;
        } else {
            mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            //什么都不做
            mHeight = height;
        } else {
            mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        }

        if (mHeight * 2 >= mWidth) {
            mHeight = mWidth / 2;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        initOk();
    }

    private void initOk() {
        mPath.moveTo(mHeight / 16 * 3, mHeight / 2);
        mPath.rLineTo(mHeight / 4, mHeight / 4);
        mPath.rLineTo(mHeight / 8 * 3, -mHeight / 8 * 4);
        mDesPath.moveTo(mHeight / 16 * 3, mHeight / 2);
    }

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);


        mPaintTwo = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTwo.setColor(Color.WHITE);
        mPaintTwo.setStyle(Paint.Style.STROKE);
        mPaintTwo.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

        mPath = new Path();
        mPathMeasure = new PathMeasure();
        mDesPath = new Path();

        mTextPaint = new Paint();
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        mTextPaint.setColor(Color.WHITE);
        mTextRect = new Rect();
        mTextPaint.getTextBounds(content, 0, content.length(), mTextRect);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mRadius, 0, mWidth - mRadius, mHeight, mRadius, mRadius, mPaint);

        canvas.drawText(content, 0, content.length(),
                mWidth / 2 - (mTextRect.right - mTextRect.left) / 2,
                mHeight / 2 + Math.abs(Math.abs(mTextRect.bottom) - Math.abs(mTextRect.top)) / 2, mTextPaint);
        mTextPaint.setAlpha((int) (textAlpha * 255));
        if (startDrawPath) {
            drawOk(canvas);
        }
    }

    private void drawOk(Canvas canvas) {
        canvas.save();
        mPathMeasure.setPath(mPath, false);
        float length = mPathMeasure.getLength();
        Log.e("length", length + "");
        mPathMeasure.getSegment(0, length / 100 * okWidthProgress, mDesPath, false);
        canvas.translate((mWidth - mHeight) / 2, 0);
        canvas.drawPath(mDesPath, mPaintTwo);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {

        }
        return super.onTouchEvent(event);
    }

}
