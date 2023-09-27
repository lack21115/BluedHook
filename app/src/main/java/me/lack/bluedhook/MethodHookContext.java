package me.lack.bluedhook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class MethodHookContext extends XC_MethodHook {

    @Override
    protected void afterHookedMethod(MethodHookParam param) {
        Main.ctt = (Context)param.args[0];
        Main.classLoader = Main.ctt.getClassLoader();
        String classPath = Main.pkgName1 + Main.chatHelper;
        XposedBridge.log("zzz class0" + classPath);
        findAndHookMethod(Main.pkgName1 + Main.MsgChattingPresent, Main.classLoader, "onMsgDataChanged", List.class, new MethodHookMsg());
        findAndHookMethod(Main.pkgName1 + Main.MsgNotifyPresent, Main.classLoader, "a", int.class, View.class, ViewGroup.class, new MethodHookNotify());
    }
}
