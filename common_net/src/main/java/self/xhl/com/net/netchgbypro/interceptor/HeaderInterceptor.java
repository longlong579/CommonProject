package self.xhl.com.net.netchgbypro.interceptor;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import self.xhl.com.net.netParams.NetworkIniter;

/**
 *
 * add Header for request
 *
 * @author xhl
 * @version 1.0.0
 */
//无需修改 网络请求 公共的头信息 每次请求都会拦截
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Map<String, String> headers = NetworkIniter.get().headers();
        Request.Builder builder = request.newBuilder();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            builder.header(header.getKey(), header.getValue());//.header()如果后面有相同的会覆盖
        }
        builder.header(NetworkIniter.get().externalParams().getTokenName(),NetworkIniter.get().externalParams().getToken());
        return chain.proceed(builder.build());
    }
}
