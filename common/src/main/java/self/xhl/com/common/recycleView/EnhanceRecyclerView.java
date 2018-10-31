package self.xhl.com.common.recycleView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import self.xhl.com.common.R;


/**
 * 增强版recyclerview
 *
 * @author bingo
 * @version 1.0.0
 */

public class EnhanceRecyclerView extends SuperRecyclerView {
    public EnhanceRecyclerView(Context context) {
        super(context);
    }

    public EnhanceRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRefreshingColorResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light,
                android.R.color.holo_green_light);
    }

    public void setEmptyView(String emptyStr, int drawableResId) {
        View emptyView = getEmptyView();
        TextView emptyTxt = (TextView) emptyView.findViewById(R.id.tv_empty);
        ImageView emptyImg = (ImageView) emptyView.findViewById(R.id.iv_empty);
        emptyTxt.setText(emptyStr);
        emptyImg.setImageResource(drawableResId);
    }

    public EnhanceRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void hideAllLoading() {
        this.hideProgress();
        this.hideMoreProgress();
        this.mPtrLayout.setRefreshing(false);
    }

    public void showErrorView(String errorMsg) {
        this.mProgress.setVisibility(View.GONE);
        this.isLoadingMore = false;
        this.mPtrLayout.setRefreshing(false);
        this.mEmpty.setVisibility(View.GONE);
        this.mError.setVisibility(View.VISIBLE);
        ((TextView)this.mErrorView.findViewById(R.id.txt_msg)).setText(errorMsg);
    }
}
