package self.xhl.com.net.netchgbypro.interceptor;

import android.text.TextUtils;


import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import self.xhl.com.net.netParams.ParameterList;
import self.xhl.com.net.netParams.NetworkIniter;
import self.xhl.com.net.netutil.Base64Util;


/**
 * @author bingo hl
 * @version 1.0.0
 */
//其他项目 注意修改校验规则(SHA) 51，138行 在App中添加 无则不添加 只适用application/x-www-form-urlencoded 表单提交
public class ClipherFormEncodeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if(request.method().equals("POST")) {
            FormBody formBody = (FormBody) request.body();
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            ParameterList params = new ParameterList();
            for (int i = 0; i < formBody.size(); i++) {
                if (!TextUtils.isEmpty(formBody.value(i))) {
                    bodyBuilder.addEncoded(formBody.name(i), formBody.value(i).replace("+", "%2B"));
                    // 转存到 ParameterList（用于sign的计算）
                    params.put(formBody.name(i), formBody.value(i));
                }
            }

            // 追加通用参数
            appendCommonParams(bodyBuilder, params);

            bodyBuilder
                    .add("_sig", genSign(params));
//                .addEncoded("token", "123456");
//        request = request.newBuilder()
//                .post(bodyBuilder.build()).build();

//        String postBodyString = bodyToString(request.body());
//        postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  bodyToString(formBody);
            String postBodyString = bodyToString(bodyBuilder.build());
            request = request.newBuilder()
                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString))
                    .build();
        }
        return chain.proceed(request);
    }

    public static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    /**
     * 增加请求体通用参数 Application中添加 {"kt","valule"}
     * @param bodyBuilder
     * @param params
     */
    private void appendCommonParams(FormBody.Builder bodyBuilder, ParameterList params) {
        Map<String, String> bodys = NetworkIniter.get().commonBodys();//自己添加 且里面的值是运行就固定的值
        for (Map.Entry<String, String> body : bodys.entrySet()) {
            bodyBuilder.add(body.getKey(), body.getValue());//待疑问 addEncode()区别
            params.put(body.getKey(),body.getValue());
        }

    }


    private String genSign(ParameterList parameterList) {
        // 在原有的parameterList上加入sign
        return signRequest(parameterList, 0);
    }

    private String signRequest(ParameterList params, int securityType) {
        if (!params.containsKey("_sig")) {
            try {
                StringBuilder sb = new StringBuilder(params.size() * 5);
                List<String> paramNames = new ArrayList(params.keySet());
                Collections.sort(paramNames);
                Iterator i$ = paramNames.iterator();

                String sig;
                while (i$.hasNext()) {
                    sig = (String) i$.next();
                    sb.append(sig);
                    sb.append('=');
                    sb.append(params.get(sig));
                }

                if (securityType == 0) {
                    //sb.append("0FM1Zi0YgE2x1xzKfa7GS2SMzJwjMPUs");加盐
                    sb.append(NetworkIniter.get().externalParams().sign());
                    MessageDigest sha = MessageDigest.getInstance("SHA1");
                    return new String(Base64Util.encode(sha.digest(sb.toString().getBytes("utf-8")), 2), "utf-8");
                }

            } catch (Exception var11) {
                throw new RuntimeException("sign url failed.", var11);
            }
        }
        return "";
    }

}
