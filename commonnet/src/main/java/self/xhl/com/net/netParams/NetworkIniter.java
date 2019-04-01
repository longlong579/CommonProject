package self.xhl.com.net.netParams;

import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;

/**
 * @author bingo xhl  不改
 * @version 1.1.0
 * OKHttpClient的相关参数设置
 */
//保存一些网络需要的信息,其他项目不需修改headers()，只要提供mExtraHeaders 复写headers()中的key
public class NetworkIniter {
    private static NetworkIniter sNetwork;
    private final INetExternalParams mExternalParams;//额外固定的 Post请求参数
    private final Map<String, String> mExtraHeaders;//额外固定的 Headers
    private Map<String, String> mExtraCommonBody;//公共请求体信息 不变的那些
    private final List<Interceptor> networkInterceptors;//网络拦截器
    private final List<Interceptor> interceptors;//拦截器
    public final Context mApplicationContext;

    private NetworkIniter(Context context,INetExternalParams params,
                          Map<String, String> extraHeaders,Map<String, String> extraBodys,
                          List<Interceptor> networkInterceptors, List<Interceptor> interceptors) {
        this.mExternalParams = params;
        this.mApplicationContext = context;
        this.mExtraHeaders = extraHeaders;
        this.networkInterceptors = networkInterceptors;
        this.interceptors = interceptors;
    }


    public static final class Builder {
        private Context context;
        private Map<String, String> extraHeaders;//公共请求头
        private Map<String, String> extraCommonBody;//公共请求体信息
        private INetExternalParams networkParams;//网络请求固定参数 比如网络地址等 供其他调用
        private List<Interceptor> networkInterceptors;
        private List<Interceptor> interceptors;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        //retrofit的公共请求头信息 在application中配置
        public Builder extraHeaders(Map<String, String> extraHeaders) {
            this.extraHeaders = extraHeaders;
            return this;
        }

        //retrofit的公共请求体信息POST 表单上传有用JSON没用 在application中配置
        public Builder extraCommonBody(Map<String, String> extraCommonBody) {
            this.extraCommonBody = extraCommonBody;
            return this;
        }

        public Builder externalParams(INetExternalParams params) {
            this.networkParams = params;
            return this;
        }

        public Builder networkInterceptors(List<Interceptor> networkInterceptors) {
            this.networkInterceptors = networkInterceptors;
            return this;
        }

        public Builder interceptors(List<Interceptor> interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public NetworkIniter build() {
            checkNotNull(networkParams, "INetExternalParams");
            checkNotNull(context, "context");

            if (interceptors == null) {
                interceptors = new ArrayList<>();
            }

            if (networkInterceptors == null) {
                networkInterceptors = new ArrayList<>();
            }

            return new NetworkIniter(context, networkParams,extraHeaders, extraCommonBody,networkInterceptors, interceptors);
        }

    }

    public static void init(NetworkIniter xkNetwork) {
        if (xkNetwork == null) {
            throw new RuntimeException("Please using XKNetwork.Builder(context).build() to init XKNetwork");
        }
        sNetwork = xkNetwork;
    }

    public static NetworkIniter get() {
        if (sNetwork == null) {
            throw new RuntimeException("Please using XKNetwork.init() in Application first");
        }
        return sNetwork;
    }

    public INetExternalParams externalParams() {
        return mExternalParams;
    }

    //可以在这里添加 也可以在mExtraHeaders中添加 为了解耦 建议放mExtraHeaders map<String,Sting>->{"User-Agent","(" + Build.MODEL + ";Djkj;Android " + Build.VERSION.RELEASE + ";)"}
    public Map<String, String> headers() {
        Map<String, String> map = new HashMap<>();

        map.put("User-Agent", "(" + Build.MODEL + ";Djkj;Android " + Build.VERSION.RELEASE + ";)");

        if (mExtraHeaders != null) {
            map.putAll(mExtraHeaders);
        }
        return map;
    }

    public Map<String, String> commonBodys() //okhttps 拦截器中设置公共请求体 Post 表单
    {
        Map<String, String> map = new HashMap<>();
        if (mExtraCommonBody != null) {
            map.putAll(mExtraCommonBody);
        }
        return map;
    }


    public List<Interceptor> networkInterceptors() {
        return networkInterceptors;
    }

    public List<Interceptor> interceptors() {
        return interceptors;
    }

    private static void checkNotNull(Object object, String err) {
        if (object == null) {
            throw new IllegalArgumentException(err + " can not be null !");
        }
    }
}