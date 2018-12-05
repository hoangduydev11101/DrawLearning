package com.draw.leduy.ezdraw.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {

    private TypePath mTypePath = TypePath.BRUSH;

    public void setTypePath() {

    }
    private static final float TOLERANCE = 5;
    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Context mContext;
    private Paint mPaint;
    private float mX, mY;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        mContext = c;

        // we set a new Path
        mPath = new Path();

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
    }

    // set color path
    public void setPathColor(int color) {
        if (mPaint == null)
            return;

        mPaint.setColor(ActivityCompat.getColor(mContext, color));
        invalidate();
    }

    // set size path
    public void setPathSize(float size) {
        if (mPaint == null)
            return;

        mPaint.setStrokeWidth(size);
        invalidate();
    }

    // set type path
    public void setPathStyle(Style type) {
        if (mPaint == null)
            return;
        switch (type) {
            case FILL:
                mPaint.setStyle(Paint.Style.FILL);
                break;

            case STROKE:
                mPaint.setStyle(Paint.Style.STROKE);
                break;

            case FILL_STROKE:
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
        }
        invalidate();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    public void setPathStrokeColor(int color) {
        if (mPaint == null)
            return;

//        mPaint.
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the mPath with the mPaint on the canvas when onDraw
        canvas.drawPath(mPath, mPaint);
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public enum TypePath {PENCIL, CYRAN, BRUSH, SMOOTH}

    public void earsePath() {
        if (mPaint == null)
            return;

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        invalidate();
    }

    public void clearCanvas() {
        mPath.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }

    public enum Style {FILL, STROKE, FILL_STROKE}
}
