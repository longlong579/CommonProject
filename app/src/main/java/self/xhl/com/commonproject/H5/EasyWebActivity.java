package self.xhl.com.commonproject.H5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IWebLayout;
import com.xhl.statusbarcompatutil.StatusBarCompat;

import self.xhl.com.common.H5.base.BaseAgentWebActivity;
import self.xhl.com.common.H5.widget.WebLayout;
import self.xhl.com.commonproject.R;


/**
 * Created by cenxiaozhong on 2017/7/22.
 * 不和Js交互的网页 可以使用这个 比如隐私政策啥的
 * ToolBar自己设置 或用默认的
 * <p>
 */
public class EasyWebActivity extends BaseAgentWebActivity {

	private TextView mTitleTextView;
	private String Url;
	private String title;

	public static void launchActivity(Context context, String Url,String toolBarTitle)
	{
		Intent intent =new Intent(context, EasyWebActivity.class);
		intent.putExtra("URL",Url);
		intent.putExtra("ToolBarTitle",toolBarTitle);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Url = getIntent().getStringExtra("URL");
		title=getIntent().getStringExtra("ToolBarTitle");
		if(StringUtils.isEmpty(Url)) {
			Url = "https://www.so.com/?src=tab_www";
		}
		if(StringUtils.isEmpty(title)) {
			title = "EasyWebActivity";
		}

		setContentView(R.layout.activity_web);
		addBGChild((FrameLayout) mAgentWeb.getWebCreator().getWebParentLayout()); // 得到 AgentWeb 最底层的控件 配合getWebLayout()使用
		LinearLayout mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
		initToolBar();
	}

	private void initToolBar()
	{
		Toolbar mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
		mToolbar.setTitleTextColor(Color.WHITE);
		mToolbar.setTitle("");
		mTitleTextView = (TextView) this.findViewById(R.id.toolbar_title);
		mTitleTextView.setText(title);
		this.setSupportActionBar(mToolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EasyWebActivity.this.finish();
			}
		});
		StatusBarCompat.setStatusBarColor(this,Color.GREEN);
	}
	@Nullable
	@Override
	protected IWebLayout getWebLayout() {
		return new WebLayout(this);
	}

	//当使用WebLayout时
	protected void addBGChild(FrameLayout frameLayout) {

		TextView mTextView = new TextView(frameLayout.getContext());
		mTextView.setText("Developed by LongGe");
		mTextView.setTextSize(16);
		mTextView.setTextColor(Color.parseColor("#727779"));
		frameLayout.setBackgroundColor(Color.parseColor("#272b2d"));
		FrameLayout.LayoutParams mFlp = new FrameLayout.LayoutParams(-2, -2);
		mFlp.gravity = Gravity.CENTER_HORIZONTAL;
		final float scale = frameLayout.getContext().getResources().getDisplayMetrics().density;
		mFlp.topMargin = (int) (5 * scale + 0.5f);
		frameLayout.addView(mTextView, 0, mFlp);
	}

	@NonNull
	@Override
	protected ViewGroup getAgentWebParent() {
		return (ViewGroup) this.findViewById(R.id.container);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}



	@Override
	protected int getIndicatorColor() {
		return Color.parseColor("#ff0000");
	}

	@Override
	protected int getIndicatorHeight() {
		return 3;
	}

	@Nullable
	@Override
	protected String getUrl() {
		return "http://www.baidu.com";
	}

	@Nullable
	@Override
	public DefaultWebClient.OpenOtherPageWays getOpenOtherAppWay() {
		return DefaultWebClient.OpenOtherPageWays.ASK;//打开其他应用时，弹窗咨询用户是否前往其他应用
	}

	private AlertDialog mAlertDialog;
	private void showDialog() {

		if (mAlertDialog == null) {
			mAlertDialog = new AlertDialog.Builder(this)
					.setMessage("您确定要关闭该页面吗?")
					.setNegativeButton("再逛逛", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (mAlertDialog != null) {
								mAlertDialog.dismiss();
							}
						}
					})//
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							if (mAlertDialog != null) {
								mAlertDialog.dismiss();
							}
							EasyWebActivity.this.finish();
						}
					}).create();
		}
		mAlertDialog.show();

	}

}
