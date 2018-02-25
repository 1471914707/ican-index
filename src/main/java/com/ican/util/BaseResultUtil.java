package com.ican.util;

public class BaseResultUtil {

    private final static String MSG_SUCCESS = "请求成功";

    private final static String MSG_ERROR = "系统错误";

    private final static String MSG_PARAMETER_ERROR = "参数错误";

    public static BaseResult initResult(){
        BaseResult result = new BaseResult();
        result.setCode(1);
        result.setMsg(MSG_ERROR);
        return result;
    };

/*    public void setSuccess(BaseResult result, String msg) {
        result.setMsg(msg);
        result.setCode(0);
    }*/

    public static BaseResult setSuccess(BaseResult result, Object data) {
        result.setCode(0);
        result.setMsg(MSG_SUCCESS);
        result.setData(data);
        return result;
    }

    public static BaseResult setSuccess(BaseResult result, String msg, Object data) {
        result.setCode(0);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

}
