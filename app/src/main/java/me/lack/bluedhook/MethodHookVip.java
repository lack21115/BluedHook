package me.lack.bluedhook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class MethodHookVip extends XC_MethodHook {

    @Override
    protected void afterHookedMethod(MethodHookParam param) {
        XposedBridge.log("zzz 444 VipInfo");
        Object loginResult = param.getResult();
        XposedHelpers.setIntField(loginResult, "vip_grade", 2);
    }

}
