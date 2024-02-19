package me.lack.bluedhook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class MethodHookFlash extends XC_MethodHook {

    @Override
    protected void afterHookedMethod(MethodHookParam param) {
        XposedBridge.log("[BluedHook] 555 Flash");
        Object FlashNumberModel = param.getResult();
        XposedHelpers.setIntField(FlashNumberModel, "flash_left_times", 99);
    }

}
