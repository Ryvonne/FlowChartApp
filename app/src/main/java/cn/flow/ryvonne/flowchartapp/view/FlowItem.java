package cn.flow.ryvonne.flowchartapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.flow.ryvonne.flowchartapp.R;
import cn.flow.ryvonne.flowchartapp.TypedValueAssit;

public class FlowItem extends ImageView {
    /**
     * 文字放置的位置
     */
    public static final int CENTER = 0;
    public static final int LEFT = 1;
    public static final int TOP = 2;
    public static final int RIGHT = 3;
    public static final int Bottom = 4;

    /**
     * 连线的形式
     */
    public static final int BOTH = 0;
    public static final int START = 1;
    public static final int END = 2;
    public static final int NONE = 3;

    private int textlocation = CENTER;
    private String title = "";
    private int textColor = Color.BLACK;
    private float textSize = 30;
    private float textPadding = 10;
    private int needline = 0;

    Bitmap mBitmap;
    Paint mPaint;

    public FlowItem(Context context) {
        this(context, null);
    }

    public FlowItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public FlowItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FlowItem);
        textlocation = a.getInt(R.styleable.FlowItem_text_location, RIGHT);//获取哪边为中心
        title = a.getString(R.styleable.FlowItem_textcontent);
        textColor = a.getColor(R.styleable.FlowItem_textcolor, Color.BLACK);

        textSize = a.getDimension(R.styleable.FlowItem_textsize, TypedValueAssit.dip2px(context, 14));
        textPadding = a.getDimension(R.styleable.FlowItem_textpadding, TypedValueAssit.dip2px(context, 5));
        needline = a.getInt(R.styleable.FlowItem_needline, BOTH);

        Drawable draw = a.getDrawable(R.styleable.FlowItem_imagebackgroud);
        if (draw != null) {
            mBitmap = drawable2Bitmap(draw);
        } else {

        }

        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        mPaint.setAntiAlias(true);
    }

    public int getTextlocation() {
        return textlocation;
    }

    public void setTextlocation(int textlocation) {
        this.textlocation = textlocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getTextPadding() {
        return textPadding;
    }

    public void setTextPadding(float textPadding) {
        this.textPadding = textPadding;
    }

    public int getNeedline() {
        return needline;
    }

    public void setNeedline(int needline) {
        this.needline = needline;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap == null) {
            return;
        }


        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        ViewGroup.LayoutParams layout = getLayoutParams();
        setBackgroudSize(layout, width, height);

        int bitx = (layout.width - width) / 2;
        int bity = (layout.height - height) / 2;
        canvas.drawBitmap(mBitmap, bitx, bity, mPaint);

        setText(canvas, layout.width, layout.height);
    }

    public void setBackgroudSize(ViewGroup.LayoutParams layout, int width, int height) {
        double textwidth = mPaint.measureText(getTitle());

        int twidth = (int) (width + 2 * (textwidth + textPadding));
        int theight = (int) (height + (textSize + textPadding) * 2);

        if (layout.width != twidth || layout.height != theight) {
            layout.width = twidth;
            layout.height = theight;
            setLayoutParams(layout);
        }
    }

    public void setText(Canvas canvas, int width, int height) {
        switch (getTextlocation()) {
            case CENTER:
                mPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(getTitle(), width / 2, height / 2, mPaint);
                break;
            case LEFT:
                mPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(getTitle(), 0, (height + textSize) / 2, mPaint);
                break;
            case TOP:
                mPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(getTitle(), width / 2, textSize, mPaint);
                break;
            case RIGHT:
                int textwidth = (int) mPaint.measureText(getTitle());
                mPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(getTitle(), width - textwidth, (height + textSize) / 2, mPaint);
                break;
            case Bottom:
                mPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(getTitle(), width / 2, height - textPadding, mPaint);
                break;
        }
    }

    void Log(String l) {
        Log.i("FlowItem", l);
    }

    Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
