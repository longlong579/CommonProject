package self.xhl.com.common.widget.emptyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.qiujuer.genius.ui.widget.Loading;

import self.xhl.com.common.R;


/**
 * 简单的占位控件， 任何布局都可以使用
 * 实现了显示一个空的图片显示，
 * 可以和MVP配合显示没有数据，正在加载等状态
*/
/*
     private fun initStateView() //初始化时绑定所包含的布局
     {
            placeholer?.apply {
                bind(rl_pic, cl_sku, rl_commodity_no, ll_bottom)
                triggerLoading()
            }
      }
    placeholer.triggerOk()//获取数据成功
    placeholer.triggerError(msg)//获取数据失败
 */
@SuppressWarnings("unused")
public class EmptyView extends LinearLayout implements PlaceHolderView {
    private ImageView mEmptyImg;
    private TextView mStatusText;
    private Loading mLoading;

    private int[] mDrawableIds = new int[]{0, 0};
    private int[] mTextIds = new int[]{0, 0, 0};

    private View[] mBindViews;

    public EmptyView(Context context) {
        super(context);
        init(null, 0);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.lay_empty, this);
        mEmptyImg = (ImageView) findViewById(R.id.im_empty);
        mStatusText = (TextView) findViewById(R.id.txt_empty);
        mLoading = (Loading) findViewById(R.id.loading);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.EmptyView, defStyle, 0);

        mDrawableIds[0] = a.getInt(R.styleable.EmptyView_comEmptyDrawable, R.mipmap.ic_network_error);
        mDrawableIds[1] = a.getInt(R.styleable.EmptyView_comErrorDrawable, R.mipmap.ic_network_error);
        mTextIds[0] = a.getInt(R.styleable.EmptyView_comEmptyText, R.string.prompt_empty);
        mTextIds[1] = a.getInt(R.styleable.EmptyView_comErrorText, R.string.prompt_error);
        mTextIds[2] = a.getInt(R.styleable.EmptyView_comLoadingText, R.string.prompt_loading);

        a.recycle();
    }

    /**
     * 绑定一系列数据显示的布局
     * 当前布局隐藏时（有数据时）自动显示绑定的数据布局
     * 而当数据加载时，自动显示Loading，并隐藏数据布局
     *
     * @param views 数据显示的布局
     */
    public void bind(View... views) {
        this.mBindViews = views;
    }

    /**
     * 更改绑定布局的显示状态
     *
     * @param visible 显示的状态
     */
    private void changeBindViewVisibility(int visible) {
        final View[] views = mBindViews;
        if (views == null || views.length == 0)
            return;

        for (View view : views) {
            view.setVisibility(visible);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerEmpty() {
        mLoading.setVisibility(GONE);
        mLoading.stop();
        mEmptyImg.setImageResource(mDrawableIds[0]);
        mStatusText.setText(mTextIds[0]);
        mEmptyImg.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public void triggerEmpty(String msg) {
        mLoading.setVisibility(GONE);
        mLoading.stop();
        mEmptyImg.setImageResource(mDrawableIds[0]);
        mStatusText.setText(msg);
        mEmptyImg.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerNetError() {
        mLoading.setVisibility(GONE);
        mLoading.stop();
        mEmptyImg.setImageResource(mDrawableIds[1]);
        mStatusText.setText(mTextIds[1]);
        mEmptyImg.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerError(@StringRes int strRes) {
        mLoading.setVisibility(GONE);
        mLoading.stop();
//        Application.showToast(strRes);
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }

    @Override
    public void triggerError(String str) {
        mLoading.setVisibility(GONE);
        mLoading.stop();
        mStatusText.setText(str);
        mEmptyImg.setVisibility(VISIBLE);
        mEmptyImg.setImageResource(mDrawableIds[1]);
//        Application.showToast(strRes);
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerLoading() {
        mEmptyImg.setVisibility(GONE);
        mStatusText.setText(mTextIds[2]);
        setVisibility(VISIBLE);
        mLoading.setVisibility(VISIBLE);
        mLoading.start();
        changeBindViewVisibility(GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerOk() {
        setVisibility(GONE);
        changeBindViewVisibility(VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerOkOrEmpty(boolean isOk) {
        if (isOk)
            triggerOk();
        else
            triggerEmpty();
    }

}
