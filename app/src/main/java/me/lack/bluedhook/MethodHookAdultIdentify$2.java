package me.lack.bluedhook;

import android.util.Log;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class MethodHookAdultIdentify$2 extends XC_MethodHook {

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        // 获取原始参数值
        int originalValue = (int) param.args[0];
        String logMessage = "[BluedHook] " + "Original value passed to setNeedAdultVerify: " + originalValue;
        XposedBridge.log(logMessage);

        // 修改参数值，始终把此参数设置为0
        param.args[0] = 0;
        logMessage = "[BluedHook] " + "Modified value set to 0";
        XposedBridge.log(logMessage);
    }

}
