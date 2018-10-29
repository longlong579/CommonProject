package self.xhl.com.net.netchgbypro.exception;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import self.xhl.com.net.exception.RequestStatusEntity;
import self.xhl.com.net.exception.BizException;
import self.xhl.com.net.exception.TickOutException;
import self.xhl.com.net.exception.TokenFailException;

import static self.xhl.com.net.netchgbypro.exception.RespCode2Msg.LOGIN_VERIFY;

/**
 * @author bingo xhl 根据实际项目修改
 * @version 1.0.0
 */

/*
{
    "success": false,
    "code": "10500",
    "message": "服务器繁忙，请稍候重试!"
    "data":   / "data":"string"
}
 */
public class YcExceptionHandler {
//    private static final int ERROR_CODE_TICK_OUT = -60001;//到时候修改

    private static final int KEY_Defualt = 1;

    private static boolean isTickOut(int code) {
        return RespCode2Msg.LOGIN_VERIFY == code || RespCode2Msg.LOGIN_TICKOUT == code;//根据实际项目修改
    }

    private static boolean isTookenFail(int code)//Token失效 根据实际项目修改
    {
        return code==RespCode2Msg.TOKEN_ERROR ||code==RespCode2Msg.TOKEN_EXPIRE;
    }

    public static void throwXKExceptionWhenNotSuccess(JsonObject jsonObject) {

        if (jsonObject.has("success")) {
            boolean success = jsonObject.get("success").getAsBoolean();
            if (!success)//失败才有code
            {
                if (jsonObject.has("code")) {
                    int code = jsonObject.get("code").getAsInt();
                    try {
                        if (isTickOut(code)) {
                            throw new TickOutException("账号被踢下线");//抛出的异常会被OnError接收
                        }
                        else if(isTookenFail(code))
                        {
                            throw new TokenFailException(code,"Token失效");
                        }
                        else {
                            // 根据不同的code提示 养车51公司gson规定
                            //throw new BizException(getErrorMsgByCode(code), code);
                            throw new BizException(jsonObject.get("message").getAsString(), code);
                        }
                    } catch (NumberFormatException e) {
                        throw new BizException("code", KEY_Defualt);
                    }
                }

            }
        }
        else {
            throw new BizException("服务器非法错误", KEY_Defualt);
        }
    }



    //  养车51返回数据格式解析
//    /*
//{
//	"stat": {
//		"cid": "a:f52876|t:409|s:1530604129684",
//		"code": 0,
//		"notificationList": [],
//		"stateList": [{
//			"code": 0,
//			"length": 52,
//			"msg": "成功"
//		}],
//		"systime": 1530604129979
//	},
//	"content": [{
//		"creditPrice": "960.39",
//		"notPayPrice": "-1000288.40"
//	}]
//}
//
//{
//    "success": false,
//    "code": "10500",
//    "message": "服务器繁忙，请稍候重试!"
//    "data":{}/ "data":"string"
//}
// */
//    public static void throwXKExceptionWhenNotSuccess(JsonObject jsonObject) {
//        JsonObject statObj = jsonObject.get("stat").getAsJsonObject();
//        if (statObj.has("code")) {
//
//            int code = statObj.get("code").getAsInt();
//            if (code != 0) {
//                // 对不同的code做 不同的异常处理
//                try {
//                    if (isTickOut(code)) {
//                        throw new TickOutException("踢下线");//抛出的异常会被OnError接收
//                    } else {
//                        // 根据不同的code提示 养车51公司gson规定
//                        throw new BizException(getErrorMsgByCode(code), code);
//                    }
//                } catch (NumberFormatException e) {
//                    throw new BizException("code", KEY_Defualt);
//                }
//            } else {
//                // code 为0的情况
//                if (statObj.has("stateList")) {
//                    JsonArray stateList = statObj.getAsJsonArray("stateList");
//                    int stateSize = stateList.size();
//                    for (JsonElement jsonElement : stateList) {
//                        JsonObject state = jsonElement.getAsJsonObject();
//                        int currentCode;
//                        if (state.has("code") && (currentCode = state.get("code").getAsInt()) != 0) {
//                            if (stateSize == 1) {
//                                throw new BizException(state.get("msg").getAsString(), currentCode);
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            throw new BizException("服务器非法错误", KEY_Defualt);
//        }
//    }
//
//    public static String getErrorMsgByCode(int status) {
//        switch (status) {
//            case RespCode2Msg.TOKEN_ERROR:
//                return RespCode2Msg.MSG_TOKEN_ERROR;
//            case RespCode2Msg.TOKEN_EXPIRE:
//                return RespCode2Msg.MSG_TOKEN_EXPIRE;
//            case RespCode2Msg.APPID_NOT_EXIST:
//                return RespCode2Msg.MSG_APPID_NOT_EXIST;
//            case RespCode2Msg.INTERNAL_SERVER_ERROR:
//                return RespCode2Msg.MSG_INTERNAL_SERVER_ERROR;
//            case RespCode2Msg.UNKNOWN_ERROR:
//                return RespCode2Msg.MSG_UNKNOWN_ERROR;
//            case RespCode2Msg.UNKNOWN_METHOD:
//                return RespCode2Msg.MSG_UNKNOWN_METHOD;
//            case RespCode2Msg.PARAMETER_ERROR:
//                return RespCode2Msg.MSG_PARAMETER_ERROR;
//            case RespCode2Msg.SIGNATURE_ERROR:
//                return RespCode2Msg.MSG_SIGNATURE_ERROR;
//            default:
//                return "无法识别的错误码";
//        }
//    }
//
//    public static RequestStatusEntity getRequestStatusResult(JsonObject jsonObject) {
//        JsonObject statObj = jsonObject.get("stat").getAsJsonObject();
//        if (statObj.has("code")) {
//
//            int code = statObj.get("code").getAsInt();
//            if (code != 0) {
//                // 对不同的code做 不同的异常处理
//                return new RequestStatusEntity(code, getErrorMsgByCode(code));
//            } else {
//                // code 为0的情况
//                if (statObj.has("stateList")) {
//                    JsonArray stateList = statObj.getAsJsonArray("stateList");
//                    int stateSize = stateList.size();
//                    for (JsonElement jsonElement : stateList) {
//                        JsonObject state = jsonElement.getAsJsonObject();
//                        int currentCode;
//                        if (state.has("code") && (currentCode = state.get("code").getAsInt()) != 0) {
//                            return new RequestStatusEntity(currentCode, getErrorMsgByCode(currentCode));
//                        }
//                    }
//                }
//            }
//        } else {
//            return new RequestStatusEntity(-100, "服务器非法");
//        }
//
//        return new RequestStatusEntity(0, "成功");
//
//    }
    //
}
