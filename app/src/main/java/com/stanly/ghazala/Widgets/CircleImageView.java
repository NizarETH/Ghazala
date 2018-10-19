package com.stanly.ghazala.Widgets;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
/**
 * Created by nizar on 28/06/16.
 */
public class CircleImageView extends ImageView {

    private Paint paint;
    private int color = 0x0000FF;
    private int colorCode1, colorCode2;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        //this.setBackgroundColor(color);
        this.color = color;
    }


    /**
     * @param context
     */
    public CircleImageView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.parseColor("#4275a9"));

    }

    /**
     * @param context
     */
    public CircleImageView(Context context, Paint paint) {
        super(context);
        this.paint = paint;
    }

    /**
     * @param context
     * @param attrs
     */
    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.parseColor("#4275a9"));
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* (non-Javadoc)
     * @see android.widget.ImageView#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect = canvas.getClipBounds();
        Log.i(" bounds of arrow",rect.left+" bottom "+rect.bottom+" top "+rect.top+ " right "+rect.right);
        if(paint == null){
            paint = new Paint();
            paint.setColor(Color.WHITE);
        }
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1.5f);
        canvas.drawCircle(rect.right / 2, rect.bottom / 2, rect.bottom / 2 - 1.5f, paint );
        super.onDraw(canvas);

    }

    /**
     * @return the paint
     */
    public Paint getPaint() {
        return paint;
    }

    /**
     * @param paint the paint to set
     */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

}
