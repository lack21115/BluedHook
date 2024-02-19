package me.lack.bluedhook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class MethodHookVip extends XC_MethodHook {

    @Override
    protected void afterHookedMethod(MethodHookParam param) {
        Object loginResult = param.getResult();
        XposedBridge.log("[BluedHook] "+"VipInfo: Updated vip_grade to 2");
        XposedHelpers.setIntField(loginResult, "vip_grade", 2);
    }

}
