package self.xhl.com.net.netchgbypro.converter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import self.xhl.com.net.netchgbypro.exception.YcExceptionHandler;

/**
 * @author xhl  根据实际项目做相应的修改
 * @version 1.1.0
 */

/*
{
    "success": false,
    "code": "10500",
    "message": "服务器繁忙，请稍候重试!"
    "data":   /"data":"string"
}
 */

public class YcRespTypeAdapterFactory implements TypeAdapterFactory {
    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {

            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {

                JsonElement jsonElement = elementAdapter.read(in);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if(jsonObject.has("success")) {
                        YcExceptionHandler.throwXKExceptionWhenNotSuccess(jsonObject);//解析抛异常
                    }
                }
                return delegate.fromJsonTree(jsonElement);
            }

        }.nullSafe();
    }
}



/*
{
	"stat": {
		"cid": "a:f52876|t:409|s:1530604129684",
		"code": 0,
		"notificationList": [],
		"stateList": [{
			"code": 0,
			"length": 52,
			"msg": "成功"
		}],
		"systime": 1530604129979
	},
	"content": [{
		"creditPrice": "960.39",
		"notPayPrice": "-1000288.40"
	}]
}

{
    "success": false,
    "code": "10500",
    "message": "服务器繁忙，请稍候重试!"
    "data":{}/ "data":"string"
}
 */
//public class YcRespTypeAdapterFactory implements TypeAdapterFactory {
//    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {
//
//        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
//        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
//
//        return new TypeAdapter<T>() {
//
//            public void write(JsonWriter out, T value) throws IOException {
//                delegate.write(out, value);
//            }
//
//            public T read(JsonReader in) throws IOException {
//
//                JsonElement jsonElement = elementAdapter.read(in);
//                if (jsonElement.isJsonObject()) {
//                    JsonObject jsonObject = jsonElement.getAsJsonObject();
//                    if (jsonObject.has("stat")) {
//
//                        YcExceptionHandler.throwXKExceptionWhenNotSuccess(jsonObject);
//
//                        if (jsonObject.has("content")) {
//                            jsonElement = jsonObject.get("content");
//                            // 如果content数组只有一个对象，那直接拿这个对象去解析，
//                            // 如果数组有多个对象，那需要整个content list去解析
//                            JsonArray contentArray = jsonElement.getAsJsonArray();
//                            if ( contentArray.size() == 1 ) {
//                                jsonElement = contentArray.get(0);
//                            }
//                        }
//                    }
//                }
//                return delegate.fromJsonTree(jsonElement);
//            }
//
//        }.nullSafe();
//    }
//}
