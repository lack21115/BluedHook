package me.lack.bluedhook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class MethodHookContext extends XC_MethodHook {

    public static Context ctt;
    List<XC_MethodHook> methodHooks = new ArrayList<>();

    public MethodHookContext(XC_MethodHook... methodHooks) {
        this.methodHooks.addAll(Arrays.asList(methodHooks));
        //this.methodHooks.add(methodHooks[0]);
        //this.methodHooks.add(methodHooks[1]);
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        ctt = (Context)param.args[0];
        Main.classLoader = ctt.getClassLoader();
        String classPath = Main.pkgName1 + Main.chatHelper;
        XposedBridge.log("zzz class0" + classPath);
        findAndHookMethod(Main.pkgName1 + Main.MsgChattingPresent, Main.classLoader, "onMsgDataChanged", List.class, methodHooks.get(0));
        findAndHookMethod(Main.pkgName1 + Main.MsgNotifyPresent, Main.classLoader, "a", int.class, View.class, ViewGroup.class, methodHooks.get(1));
    }
}
