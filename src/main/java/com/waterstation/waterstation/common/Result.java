package com.waterstation.waterstation.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    private int code;
    private String Message;
    private Object data;

    public static Result setMessageData(int code, String msg, Object data){
        Result resultUtil = new Result();
        resultUtil.setCode(code);
        resultUtil.setData(data);
        resultUtil.setMessage(msg);
        return resultUtil;
    }

    public static Result success(){
        return setMessageData(200, null, null);
    }

    public static Result success(String msg){
        return setMessageData(200, msg, null);
    }

    public static Result success(String msg, Object data){
        return setMessageData(200, msg, data);
    }

    public static Result fail(){
        return setMessageData(404, null, null);
    }

    public static Result fail(String msg){
        return setMessageData(404, msg, null);
    }

    public static Result fail(String msg, Object data){
        return setMessageData(404, msg, data);
    }

    public static Result taskCountFull(){
        return setMessageData(401, "看广告次数已经用完了", null);
    }

    public static Result addPointSuccess(){
        return setMessageData(200, "看广告增加积分成功", null);
    }

    public static Result addPointFail(){
        return setMessageData(400, "看广告增加积分失败", null);
    }

    public static Result pointNotEnough(){return setMessageData(400, "积分不足", null);}
    public static Result adviceNotExist(){return setMessageData(404, "设备不存在", null);}
}

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//public class Result {
//    private int code;
//    private String Message;
//    private Object data;
//
//    public static Result setMessageData(int code, String msg, Object data){
//        com.waterstation.waterstation.common.Result result = new com.waterstation.waterstation.common.Result();
//        result.setCode(code);
//        result.setData(data);
//        result.setMessage(msg);
//        return result;
//    }
//
//    public static Result success(){
//        return setMessageData(0, null, null);
//    }
//
//    public static Result success(String msg){
//        return setMessageData(0, msg, null);
//    }
//
//    public static Result success(String msg, Object data){
//        return setMessageData(0, msg, data);
//    }
//
//    public static Result fail(){
//        return setMessageData(-1, null, null);
//    }
//
//    public static Result fail(String msg){
//        return setMessageData(-1, msg, null);
//    }
//
//    public static Result fail(String msg, Object data){
//        return setMessageData(-1, msg, data);
//    }
//}
