package self.xhl.com.net.netParams;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author xhl 不改
 * @version 1.0.0
 */
//application初始化时创建，传入一些不变的参数  （待考虑：地理位置 userToken放这里合适么？没办法到token要动态获取，暂时放吧）
public interface INetExternalParams {
    // App is Release version
    boolean isRelease();

    // APP Http Host
    @NonNull
    String httpHost();

    // APP Https Host
    @Nullable
    String httpsHost();

    @Nullable
    String getToken();

    @NonNull
    String getTokenName();

    @Nullable
    String sign();//SAH校验额外的
}