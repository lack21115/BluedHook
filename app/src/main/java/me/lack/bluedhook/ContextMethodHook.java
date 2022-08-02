package me.lack.bluedhook;

import android.content.Context;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class ContextMethodHook extends XC_MethodHook {

    XC_MethodHook methodHook;

    public ContextMethodHook(XC_MethodHook methodHook) {
        this.methodHook = methodHook;
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        Context ctt = (Context)param.args[0];
        Main.classLoader = ctt.getClassLoader();
        String classPath = Main.pkgName1 + Main.chatHelper;
        XposedBridge.log("zzz class0" + classPath);
        findAndHookMethod(Main.pkgName1 + Main.MsgChattingPresent, Main.classLoader, "onMsgDataChanged", List.class, methodHook);
    }
}
