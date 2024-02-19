package me.lack.bluedhook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class MethodHookAdultIdentify extends XC_MethodHook {

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        Object loginResult = param.thisObject;
        int updatedValue = 0;
        try {
            XposedHelpers.setIntField(loginResult, "is_kids", updatedValue);
            String logMessage = "[BluedHook] "+"Updated 'is_kids' field to: " + updatedValue;
            XposedBridge.log(logMessage);
        } catch (Exception e) {
            String errorMessage = "[BluedHook] "+"Error updating 'is_kids' field: " + e.getMessage();
            XposedBridge.log(errorMessage);
        }
    }
}
