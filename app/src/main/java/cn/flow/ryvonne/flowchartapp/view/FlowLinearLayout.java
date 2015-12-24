package cn.flow.ryvonne.flowchartapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class FlowLinearLayout extends LinearLayout {
    private Paint mPaintText;
    Paint mPaint;

    private List<LinearLayout> mLinearLayouts;

    public FlowLinearLayout(Context context) {
        this(context, null);
    }

    public FlowLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(20);

        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.parseColor("#A9A5A0"));
            mPaint.setStrokeWidth(10);
        }
    }

    /**
     * 计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int count = getChildCount();
        for (int i = count - 1; i >= 1; i--) {
            View bottomView = getChildAt(i);
            View topView = getChildAt(i - 1);

            if (!isVisibility(bottomView) || !isVisibility(topView)) {
                continue;
            }

            ArrayList<Dot> startDots = new ArrayList<Dot>();
            ArrayList<Dot> endDots = new ArrayList<Dot>();

            if (topView instanceof ViewGroup) {
                float parentwith = topView.getX();
                float parentheight = topView.getY();
                if (!isVisibility(topView)) {
                    continue;
                }

                ViewGroup group = (ViewGroup) topView;

                int viewcount = group.getChildCount();
                for (int j = 0; j < viewcount; j++) {
                    View sv = group.getChildAt(j);
                    if (!isVisibility(sv)) {
                        continue;
                    }
                    if (setFlowItemTitle(canvas, sv, group)) {
                        int flag = ((FlowItem) sv).getNeedline();
                        if (flag == FlowItem.START || flag == FlowItem.NONE) {

                            continue;
                        }
                    }
                    Dot dot = new Dot();
                    dot.x = parentwith + sv.getX() + sv.getWidth() / 2;
                    dot.y = topView.getY() + topView.getHeight() / 2;
                    startDots.add(dot);

                }
            } else {
                if (!isVisibility(topView)) {
                    continue;
                }

                if (topView instanceof FlowItem) {
                    int flag = ((FlowItem) topView).getNeedline();
                    if (flag == FlowItem.START || flag == FlowItem.NONE) {
                    } else {
                        Dot dot = new Dot();
                        dot.x = topView.getX() + topView.getWidth() / 2;
                        dot.y = topView.getY() + topView.getHeight() / 2;
                        startDots.add(dot);
                        setFlowItemTitle(canvas, topView, null);
                    }
                } else {
                    Dot dot = new Dot();
                    dot.x = topView.getX() + topView.getWidth() / 2;
                    dot.y = topView.getY() + topView.getHeight() / 2;
                    startDots.add(dot);
                    setFlowItemTitle(canvas, topView, null);
                }
            }

            if (bottomView instanceof ViewGroup) {
                if (!isVisibility(bottomView)) {
                    continue;
                }

                if (bottomView instanceof FlowGroupView) {
                    FlowGroupView groupView = (FlowGroupView) bottomView;

                    int scount = groupView.getChildCount();
                    for (int k = 0; k < scount; k++) {
                        FlowLinearLayout group = (FlowLinearLayout) groupView.getChildAt(k);

                        if (!isVisibility(group)) {
                            continue;
                        }

                        if (!(group instanceof FlowLinearLayout)) {
                            continue;
                        }

                        if (group.getChildCount() == 0) {
                            continue;
                        }

                        float parentwith = groupView.getX() + group.getX();
                        float parentheight = groupView.getY() + group.getY();

                        View sv = group.getChildAt(0);

                        Dot dot = new Dot();
                        dot.x = parentwith + sv.getX() + sv.getWidth() / 2;
                        dot.y = parentheight + sv.getY() + sv.getHeight() / 2;
                        endDots.add(dot);
                    }
                } else {
                    float parentwith = bottomView.getX();
                    float parentheight = bottomView.getY();

                    ViewGroup group = (ViewGroup) bottomView;
                    int viewcount = group.getChildCount();
                    for (int j = 0; j < viewcount; j++) {
                        View sv = group.getChildAt(j);
                        if (!isVisibility(sv)) {
                            continue;
                        }
                        if (setFlowItemTitle(canvas, sv, group)) {
                            int flag = ((FlowItem) sv).getNeedline();
                            if (flag == FlowItem.END || flag == FlowItem.NONE) {
                                continue;
                            }
                        }

                        Dot dot = new Dot();
                        dot.x = parentwith + sv.getX() + sv.getWidth() / 2;
                        dot.y = parentheight + sv.getY() + sv.getHeight() / 2;
                        endDots.add(dot);
                    }
                }
            } else {
                if (!isVisibility(bottomView)) {
                    continue;
                }
                if (bottomView instanceof FlowItem) {
                    int flag = ((FlowItem) bottomView).getNeedline();
                    if (flag == FlowItem.START || flag == FlowItem.NONE) {
                    } else {
                        Dot dot = new Dot();
                        dot.x = bottomView.getX() + bottomView.getWidth() / 2;
                        dot.y = bottomView.getY() + bottomView.getHeight() / 2;
                        endDots.add(dot);
                        setFlowItemTitle(canvas, bottomView, null);
                    }
                } else {
                    Dot dot = new Dot();
                    dot.x = bottomView.getX() + bottomView.getWidth() / 2;
                    dot.y = bottomView.getY() + bottomView.getHeight() / 2;
                    endDots.add(dot);
                    setFlowItemTitle(canvas, bottomView, null);

                }


            }


            for (int j = 0; j < startDots.size(); j++) {
                for (int k = 0; k < endDots.size(); k++) {
                    canvas.drawLine(startDots.get(j).x, startDots.get(j).y, endDots.get(k).x, endDots.get(k).y, mPaint);
                }
            }

        }

        super.onDraw(canvas);
    }


    class Dot {
        float x;
        float y;
    }

    /**
     * 在FlowItem旁边绘制文字的方法
     *
     * @param canvas
     * @param view
     * @param parentView
     * @return
     */
    boolean setFlowItemTitle(Canvas canvas, View view, ViewGroup parentView) {
//        return setTitile(canvas, view, parentView);
        return true;
    }

    boolean setTitile(Canvas canvas, View view, ViewGroup parentView) {
        if (!isVisibility(view)) {
            return false;
        }

        if (!(view instanceof FlowItem)) {
            return false;
        }
        FlowItem item = (FlowItem) view;

        float textSize = item.getTextSize();
        float textPadding = item.getTextPadding();
        mPaintText.setColor(item.getTextColor());
        mPaintText.setTextSize(textSize);
        String title = item.getTitle();

        if (title == null) {
            return false;
        }
        int location = item.getTextlocation();
        float x = item.getX();
        float y = item.getY();

        if (parentView != null) {
            x += parentView.getX();
            y += parentView.getY();
        }

        int with = item.getWidth();
        int height = item.getHeight();

        float tx = 0;
        float ty = 0;

        switch (location) {
            case FlowItem.CENTER:
//                mPaintText.setTextAlign(Paint.Align.CENTER);
//                tx = x;
//                ty = y;
                break;
            case FlowItem.LEFT:
                tx = x - textPadding;
                ty = y + (height + textSize) / 2;
                mPaintText.setTextAlign(Paint.Align.RIGHT);
                break;
            case FlowItem.TOP:
                mPaintText.setTextAlign(Paint.Align.CENTER);
                break;
            case FlowItem.RIGHT:
                tx = x + with + textPadding;
                ty = y + (height + textSize) / 2;
                mPaintText.setTextAlign(Paint.Align.LEFT);
                break;
            case FlowItem.Bottom:
                mPaintText.setTextAlign(Paint.Align.CENTER);
                break;

        }
        canvas.drawText(title, tx, ty, mPaintText);
        int id = view.getId();
        return true;
    }

    boolean isVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

}
