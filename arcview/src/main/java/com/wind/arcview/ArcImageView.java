package com.wind.arcview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by zhangcong on 2017/11/7.
 */

public class ArcImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint paint;
    private PointF startPoint;
    private PointF endPoint;
    private PointF controlPoint;
    private int width;
    private int height;
    private int ArcHeight = 50;
    private Path path;
    private Bitmap bitmap;
    private float mScale=1.0f;
    public ArcImageView(Context context) {
        super(context);
        this.init();
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 设置弧形高度
     */
    public void setArcHeight(int height)
    {
        this.ArcHeight=height;
    }
    /**
     * 设置图片缩放大小
     * @param scale
     */
    public void setScale(float scale)
    {
        this.mScale=scale;
    }
    private void init() {
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.startPoint = new PointF(0.0F, 0.0F);
        this.endPoint = new PointF(0.0F, 0.0F);
        this.controlPoint = new PointF(0.0F, 0.0F);
        this.path = new Path();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.path.reset();
        this.path.moveTo(0.0F, 0.0F);
        this.path.addRect(0.0F, 0.0F, (float)this.width, (float)(this.height - this.ArcHeight), Path.Direction.CCW);
        this.startPoint.x = 0.0F;
        this.startPoint.y = (float)(this.height - this.ArcHeight);
        this.endPoint.x = (float)this.width;
        this.endPoint.y = (float)(this.height - this.ArcHeight);
        this.controlPoint.x = (float)(this.width / 2);
        this.controlPoint.y = (float)(this.height + this.ArcHeight);
        this.invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap!=null)
        {
            //计算缩放比例

            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);

            Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            shader.setLocalMatrix(matrix);

            paint.setShader(shader);
            this.path.moveTo(this.startPoint.x, this.startPoint.y);
            this.path.quadTo(this.controlPoint.x, this.controlPoint.y, this.endPoint.x, this.endPoint.y);
            canvas.drawPath(this.path,this.paint);
        }

    }
    @Override
    public void setImageResource(@DrawableRes int resId) {
        bitmap = BitmapFactory.decodeResource(getResources(),resId);
        Log.i(">>>>>",">>>>");
    }
}
