package com.example.paintapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrwView extends View {
    private float xPos,yPos;
    private static final float TOUCH_TOLERANCE = 4;

    private ArrayList<Stroke> paths = new ArrayList<>();
    private int currentColor;
    private int currentStroke;
    private Path path;

    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;

    private Paint bitmapPaint = new Paint(Paint.DITHER_FLAG);


    public DrwView(Context context) {
        super(context,null);
    }

    public DrwView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // smoothen drawings
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        // 0xff=255 in decimal
        paint.setAlpha(0xff);
    }


    public void initialise(int height, int width){
        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        currentColor = Color.BLUE;
        currentStroke = 20;

    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
    }

    public void setCurrentStroke(int currentStroke) {
        this.currentStroke = currentStroke;
    }

    public void undo(){
        if(paths.size() != 0){
            paths.remove(paths.size() -1);
            invalidate();
        }
    }

    public void redo(){
        if(paths.size() != 0){
            paths.remove(paths.size() +1);
            invalidate();
        }
    }

    public Bitmap save() {
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        canvas.drawColor(Color.WHITE);

        for(Stroke s : paths){
            paint.setColor(s.getColor());
            paint.setStrokeWidth(s.getWidth());
            canvas.drawPath(s.getPath(), paint);
        }

        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        canvas.restore();
    }

    public void onTouch(float x, float y){
        path = new Path();
        Stroke stroke = new Stroke(currentColor,currentStroke,path);
        paths.add(stroke);
        path.reset();
        path.moveTo(x,y);
        xPos = x;
        yPos = y;

    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - xPos);
        float dy = Math.abs(y - yPos);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(xPos, yPos, (x + xPos) / 2, (y + yPos) / 2);
            xPos = x;
            yPos = y;
        }
    }


    private void touchUp() {
        path.lineTo(xPos, yPos);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }
}
