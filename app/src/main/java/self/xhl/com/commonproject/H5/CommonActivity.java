package self.xhl.com.commonproject.H5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import self.xhl.com.common.H5.Fragment.AgentWebFragment;
import self.xhl.com.common.H5.common.FragmentKeyDown;
import self.xhl.com.commonproject.H5.JsWebFragment.JsAgentWebFragment;
import self.xhl.com.commonproject.R;


/**
 * Created by cenxiaozhong on 2017/5/23.
 * source code  https://github.com/Justson/AgentWeb
 */

public class CommonActivity extends AppCompatActivity {


	private FrameLayout mFrameLayout;
	public static final String TYPE_KEY = "type_key";
	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_common);

		mFrameLayout = (FrameLayout) this.findViewById(R.id.container_framelayout);
		int key = getIntent().getIntExtra(TYPE_KEY, -1);
		mFragmentManager = this.getSupportFragmentManager();
		openFragment(key);
	}


	private AgentWebFragment mAgentWebFragment;

	private void openFragment(int key) {

		FragmentTransaction ft = mFragmentManager.beginTransaction();
		Bundle mBundle = null;


		switch (key) {

            /*Fragment 使用AgenWeb*/
			case 0: //项目中请使用常量代替0 ， 代码可读性更高
				ft.add(R.id.container_framelayout, mAgentWebFragment = AgentWebFragment.getInstance(mBundle = new Bundle()), AgentWebFragment.class.getName());
				mBundle.putString(AgentWebFragment.URL_KEY, "https://m.vip.com/?source=www&jump_https=1");
				break;
			/*下载文件*/
			case 1:
				ft.add(R.id.container_framelayout, mAgentWebFragment = AgentWebFragment.getInstance(mBundle = new Bundle()), AgentWebFragment.class.getName());
				mBundle.putString(AgentWebFragment.URL_KEY, "http://android.myapp.com/");
				break;
            /*input标签上传文件*/
			case 2:
				ft.add(R.id.container_framelayout, mAgentWebFragment = AgentWebFragment.getInstance(mBundle = new Bundle()), AgentWebFragment.class.getName());
				mBundle.putString(AgentWebFragment.URL_KEY, "file:///android_asset/upload_file/uploadfile.html");
				break;
            /*Js上传文件*/
			case 3:
				ft.add(R.id.container_framelayout, mAgentWebFragment = AgentWebFragment.getInstance(mBundle = new Bundle()), AgentWebFragment.class.getName());
				mBundle.putString(AgentWebFragment.URL_KEY, "file:///android_asset/upload_file/jsuploadfile.html");
				break;
            /*Js*/
			case 4:
				ft.add(R.id.container_framelayout, mAgentWebFragment = JsAgentWebFragment.getInstance(mBundle = new Bundle()), JsAgentWebFragment.class.getName());
				mBundle.putString(AgentWebFragment.URL_KEY, "file:///android_asset/js_interaction/hello.html");
				break;

            /*优酷*/
			case 5:
				ft.add(R.id.container_framelayout, mAgentWebFragment = AgentWebFragment.getInstance(mBundle = new Bundle()), AgentWebFragment.class.getName());
				mBundle.putString(AgentWebFragment.URL_KEY, "http://m.youku.com/video/id_XODEzMjU1MTI4.html");
//                mBundle.putString(AgentWebFragment.URL_KEY, "https://v.qq.com/x/page/i0530nu6z1a.html");
				break;


            /*短信*/
			case 8:
				ft.add(R.id.container_framelayout, mAgentWebFragment = AgentWebFragment.getInstance(mBundle = new Bundle()), AgentWebFragment.class.getName());
				mBundle.putString(AgentWebFragment.URL_KEY, "file:///android_asset/sms/sms.html");
				break;



			/*地图*/
			case 13:
				ft.add(R.id.container_framelayout, mAgentWebFragment = AgentWebFragment.getInstance(mBundle = new Bundle()), AgentWebFragment.class.getName());
				mBundle.putString(AgentWebFragment.URL_KEY, "https://map.baidu.com/mobile/webapp/index/index/#index/index/foo=bar/vt=map");
				break;

			default:
				break;

		}
		ft.commit();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//一定要保证 mAentWebFragemnt 回调
//		mAgentWebFragment.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		AgentWebFragment mAgentWebFragment = this.mAgentWebFragment;
		if (mAgentWebFragment != null) {
			FragmentKeyDown mFragmentKeyDown = mAgentWebFragment;
			if (mFragmentKeyDown.onFragmentKeyDown(keyCode, event)) {
				return true;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}

		return super.onKeyDown(keyCode, event);
	}




	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}
