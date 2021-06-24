package com.example.flightgearappandroid.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.flightgearappandroid.R;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class JoyStick extends View {
    private float cx;
    private float cy;
    private float radius;
    private Paint paint;
    public BiConsumer<Float,Float> onChange;



    public JoyStick(Context context) {
        super(context);
        init(null);
    }

    public JoyStick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public JoyStick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

  //  public JoyStick(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
      //  super(context, attrs, defStyleAttr, defStyleRes);
      //  init(attrs);
  //  }
    private void init(@Nullable AttributeSet set){
    this.paint = new Paint();
    this.paint.setAntiAlias(true); // for better quality
    this.cx  = 500;
    this.cy  = 160;
    this.radius = 60;
    paint.setColor(Color.GREEN);
    this.onChange = null;


    }

    @Override
    protected void onDraw(Canvas canvas) {
     //   float cx = 500;
       // float cy = 160;
        //float radius = 60;
        //Paint paint = new Paint();
        //paint.setColor(Color.GREEN);
        canvas.drawCircle(cx,cy,radius,paint); // simply draw the circle
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean value = super.onTouchEvent(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                return true;
            }
            case MotionEvent.ACTION_MOVE: { // handle the movment of the joystick
                float x = event.getX();
                float y = event.getY();
                double dx = Math.pow(x-this.cx,2);
                double dy = Math.pow(y-cy,2);
                if( (dx + dy) < Math.pow(radius,2)){ // we touch the joystick
                    if(!checkBoundX(x)) return true;
                    if(!checkBoundY(y)) return true;
                    this.cx = x;
                    this.cy = y;
                    onChange.accept(cx,cy); // do the view model function
                    postInvalidate();
                    return true;
                }
                return true;
            }
        }
        return value;
    }
    // bound checking for x and y
    public boolean checkBoundX(float x){
        if((x+60 > 1000) || (x-60 < 0) ) return false;
        return true;
    }
    public boolean checkBoundY(float y){
        if((y+60 > 320) || (y-60 < 0)) return false;
        return true;
    }
}
