package com.xhl.widget.drawableCenterText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import com.xhl.myviews.R;


/**
 * @author xhl
 * @desc 图片居中TextView gravity:center_vertical
 * 2019/1/19 10:49
 */
public class DrawableCenterTextView extends AppCompatTextView {
    private int leftDrawableWidth, leftDrawableHeight, rightDrawableWidth, rightDrawableHeight,
            topDrawableWidth, topDrawableHeight, bottomDrawableWidth, bottomDrawableHeight;
    private int leftWidth, rightWidth;//左右图片宽度

    private final int DRAWABLE_LEFT = 0;
    private final int DRAWABLE_TOP = 1;
    private final int DRAWABLE_RIGHT = 2;
    private final int DRAWABLE_BOTTOM = 3;
    private boolean addTail = false;//是否对换行符的长字符串进行...替换

    private DrawableListener.DrawableRightListener drawableRightListener;

    private DrawableListener.DrawableLeftListener drawableLeftListener;

    private DrawableListener.DrawableTopListener drawableTopListener;

    private DrawableListener.DrawableBottomListener drawableBottomListener;


    public DrawableCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView, defStyleAttr, 0);

        leftDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_leftDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        leftDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_leftDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));

        rightDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_rightDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        rightDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_rightDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));

        topDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_topDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        topDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_topDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));

        bottomDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_bottomDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        bottomDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_bottomDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));

        addTail = typedArray.getBoolean(R.styleable.DrawableTextView_addTail, false);

        typedArray.recycle();

        Drawable[] drawables = getCompoundDrawables();

        for (int i = 0; i < drawables.length; i++) {

            setDrawableSize(drawables[i], i);

        }

        //放置图片
        setCompoundDrawables(drawables[DRAWABLE_LEFT], drawables[DRAWABLE_TOP], drawables[DRAWABLE_RIGHT], drawables[DRAWABLE_BOTTOM]);
    }

    //设置图片的高度和宽度
    private void setDrawableSize(Drawable drawable, int index) {

        if (drawable == null) {

            return;
        }
        //左上右下


        int width = 0, height = 0;

        switch (index) {

            case DRAWABLE_LEFT:

                width = leftDrawableWidth;
                height = leftDrawableHeight;

                break;

            case DRAWABLE_TOP:

                width = topDrawableWidth;
                height = topDrawableHeight;
                break;

            case DRAWABLE_RIGHT:

                width = rightDrawableWidth;
                height = rightDrawableHeight;

                break;

            case DRAWABLE_BOTTOM:

                width = bottomDrawableWidth;
                height = bottomDrawableHeight;

                break;


        }

        //如果没有设置图片的高度和宽度具使用默认的图片高度和宽度
        if (width < 0) {

            width = drawable.getIntrinsicWidth();

        }

        if (height < 0) {

            height = drawable.getIntrinsicHeight();
        }

        if (index == 0) {

            leftWidth = width;

        } else if (index == 2) {

            rightWidth = width;
        }

        drawable.setBounds(0, 0, width, height);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //是否对包含有换行符的文字，以换行符分割的句子，超过textView最大宽度的时候以“...”结尾
        if (addTail) {
            String text = getText().toString();

            float textWidth = getWidth() - getPaddingRight() - getPaddingLeft();

            if (leftWidth != 0) {

                textWidth = textWidth - leftWidth - getCompoundDrawablePadding();

            }

            if (rightWidth != 0) {

                textWidth = textWidth - rightWidth - getCompoundDrawablePadding();
            }
            setText(changeText(text, textWidth));
        }

    }


    /**
     * 以换行符\n来分离文本，每行超过最大长度的文本以...来替换 可以少写很多的textView
     *
     * @param text      文本内容
     * @param textWidth 文本最大长度
     * @return
     */
    private String changeText(String text, float textWidth) {

        String[] contents = text.split("\\n");
        float contentWidth;
        String content;
        for (int j = 0; j < contents.length; j++) {

            content = contents[j];
            contentWidth = this.getPaint().measureText(content);

            if (contentWidth > textWidth) {


                String newContent;
                float newContentWidth;
                for (int i = content.length(); i >= 0; i--) {

                    newContent = content.substring(0, i);

                    newContentWidth = this.getPaint().measureText(newContent + "...");


                    if (newContentWidth <= textWidth) {

                        contents[j] = newContent.concat("...");

                        break;

                    }

                }

            }

        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int k = 0; k < contents.length; k++) {


            if (k < contents.length - 1) {

                stringBuilder.append(contents[k] + "\n");

            } else {

                stringBuilder.append(contents[k]);
            }


        }

        return stringBuilder.toString();

    }


    //设置drawableRight 图片的点击监听
    public void setDrawableRightListener(DrawableListener.DrawableRightListener drawableRightListener) {
        this.drawableRightListener = drawableRightListener;
    }


    public void setDrawableLeftListener(DrawableListener.DrawableLeftListener drawableLeftListener) {
        this.drawableLeftListener = drawableLeftListener;
    }

    public void setDrawableTopListener(DrawableListener.DrawableTopListener drawableTopListener) {
        this.drawableTopListener = drawableTopListener;
    }

    public void setDrawableBottomListener(DrawableListener.DrawableBottomListener drawableBottomListener) {
        this.drawableBottomListener = drawableBottomListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:

                if (drawableRightListener != null) {

                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())
                            && event.getRawX() < getRight()) {

                        drawableRightListener.drawableRightListener(this);

                        return true;

                    }
                }


                if (drawableLeftListener != null) {

                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())
                            && event.getRawX() > getLeft()) {

                        drawableLeftListener.drawableLeftListener(this);

                        return true;
                    }
                }


                if (drawableTopListener != null) {

                    Drawable drawableTop = getCompoundDrawables()[DRAWABLE_TOP];
                    if (drawableTop != null && event.getRawY() <= (getTop() + drawableTop.getBounds().height())
                            && event.getRawY() > getTop()) {

                        drawableTopListener.drawableTopListener(this);

                        return true;

                    }
                }

                if (drawableBottomListener != null) {

                    Drawable drawableBottom = getCompoundDrawables()[DRAWABLE_BOTTOM];
                    if (drawableBottom != null && event.getRawY() >= (getBottom() - drawableBottom.getBounds().height())
                            && event.getRawY() < getBottom()) {

                        drawableBottomListener.drawableBottomListener(this);

                        return true;

                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取TextView的Drawable对象，返回的数组长度应该是4，对应左上右下
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawable = drawables[0];
            if (drawable != null) {
                // 当左边Drawable的不为空时，测量要绘制文本的宽度
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = drawable.getIntrinsicWidth();
                // 计算总宽度（文本宽度 + drawablePadding + drawableWidth）
                float bodyWidth = textWidth + drawablePadding + drawableWidth;
                // 移动画布开始绘制的X轴
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            } else if ((drawable = drawables[1]) != null) {
                // 否则如果上边的Drawable不为空时，获取文本的高度
                Rect rect = new Rect();
                getPaint().getTextBounds(getText().toString(), 0, getText().toString().length(), rect);
                float textHeight = rect.height();
                int drawablePadding = getCompoundDrawablePadding();
                int drawableHeight = drawable.getIntrinsicHeight();
                // 计算总高度（文本高度 + drawablePadding + drawableHeight）
                float bodyHeight = textHeight + drawablePadding + drawableHeight;
                // 移动画布开始绘制的Y轴
                canvas.translate(0, (getHeight() - bodyHeight) / 2);
            }
        }
        super.onDraw(canvas);
    }
}
