package com.example.xiamin.musicplayer.CircleImage;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.xiamin.musicplayer.R;

/**
 * Created by Xiamin on 2016/8/27.
 */
public class CircleImageView extends ImageView {

    private int mWidth;
    //圆形的半径
    private int mRadius;
    //位图着色器
    private BitmapShader mBitmapShader;
    //圆形图像画笔
    private Paint mBitmapPaint;
    //边界画笔
    private Paint mBorderPaint;
    //边界宽度 --来自于设置
    private float mBorderWidth;
    //边界颜色 --来自于设置
    private int mBorderColor;
    //矩阵 -- 用于缩放图片以适应view控件的大小
    private Matrix mMatrix;



    public CircleImageView(Context context) {
        super(context);
        ImageViewInit(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ImageViewInit(context,attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ImageViewInit(context,attrs);
    }

    /**
     * 初始化资源 获取自定义属性
     * @param context
     * @param attrs
     */
    private void ImageViewInit(Context context, AttributeSet attrs)
    {
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBorderPaint = new Paint();
        //反锯齿
        mBitmapPaint.setAntiAlias(true);
        mBorderPaint.setAntiAlias(true);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        mBorderColor = ta.getColor(R.styleable.CircleImageView_BorderColor, Color.GRAY);
        mBorderWidth = ta.getDimension(R.styleable.CircleImageView_BorderWidth, 1);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 宽高一致
         */
        mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = mWidth / 2;
        setMeasuredDimension(mWidth, mWidth);

        //测量的半径最终应该减去边缘宽度
        mRadius = (int) (mRadius - mBorderWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(getDrawable() == null)
        {
            super.onDraw(canvas);
            return;
        }
        setUpShader();
        canvas.drawCircle(mWidth/2,mWidth/2,mRadius,mBitmapPaint);

        //set border paint
        mBorderPaint.setStyle(Paint.Style.STROKE);//设置笔刷样式：原区域掏空，只画边界
        mBorderPaint.setColor(mBorderColor);//给笔刷上色
        mBorderPaint.setStrokeCap(Paint.Cap.ROUND);//边界类型为圆形
        mBorderPaint.setStrokeWidth(mBorderWidth);//设置边界宽度
        //设置阴影
        this.setLayerType(LAYER_TYPE_SOFTWARE, mBorderPaint);

   //     mBorderPaint.setShadowLayer(12.0f, 3.0f, 3.0f, Color.w);
        //end 设置阴影

        canvas.drawCircle(mWidth / 2, mWidth / 2, mRadius, mBorderPaint);

    }

    /**
     * 初始化BitmapShader
     */
    private void setUpShader()
    {
        //首先获得drawable对象，也就是控件属性的src，也就是我们的图片
        Drawable drawable = getDrawable();
        if (drawable == null)
        {
            return;
        }

        Bitmap bitmap = drawableToBitmap(drawable);
        /**
         * public   BitmapShader(Bitmap bitmap,Shader.TileMode tileX,Shader.TileMode tileY)
         调用这个方法来产生一个画有一个位图的渲染器（Shader）。
         bitmap   在渲染器内使用的位图
         tileX      The tiling mode for x to draw the bitmap in.   在位图上X方向花砖模式
         tileY     The tiling mode for y to draw the bitmap in.    在位图上Y方向花砖模式
         TileMode：（一共有三种）
         CLAMP  ：如果渲染器超出原始边界范围，会复制范围内边缘染色。
         REPEAT ：横向和纵向的重复渲染器图片，平铺。
         MIRROR ：横向和纵向的重复渲染器图片，这个和REPEAT 重复方式不一样，他是以镜像方式平铺。
         */
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        /**
         * 获取缩放系数
         */
        float scale = 1.0f;
        int bitmapSize = Math.min(bitmap.getHeight(),bitmap.getWidth());
        scale = mWidth * 1.0f / bitmapSize;
        mMatrix.setScale(scale,scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        /**
         * 着色
         */
        mBitmapPaint.setShader(mBitmapShader);

    }

    static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }

    /**
     增加旋转动画
     */
    ObjectAnimator animtorAlpha;
    public void StartRotation()
    {
        animtorAlpha = ObjectAnimator.ofFloat(this,"rotation",0f,720f);
        animtorAlpha.setInterpolator(new LinearInterpolator());
        animtorAlpha.setRepeatCount(100);
        animtorAlpha.setDuration(36000);
        animtorAlpha.start();
        Log.i("iii","reloate");
        }

    /**
     * 停止旋转
     */
    public void StopRotation()
    {
        animtorAlpha.end();
    }
}
