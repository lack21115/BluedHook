package me.lack.bluedhook;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.content.Context;
import android.content.ContextWrapper;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage {

    public static String pkgName1 = "com.soft.blued";
    public static String pkgName2 = "com.danlan.xiaolan";
    public static String pkgName3 = "com.blued.international";

    public static String WrapperProxy = ".MyWrapperProxyApplication";
    public static String MsgChattingPresent = ".ui.msg.presenter.MsgChattingPresent";
    public static String MsgNotifyPresent = ".ui.msg.adapter.MessageChatAdapter";
    public static String chatHelper = ".ui.msg.controller.tools.ChatHelperV4";

    public static String userInfo         = "com.blued.android.module.common.user.model.UserInfo";
    public static String bluedLoginResult = "com.blued.android.module.common.user.model.BluedLoginResult";

    public static ClassLoader classLoader;
    public static Context ctt;

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(pkgName1) &&
            !lpparam.packageName.equals(pkgName2) &&
            !lpparam.packageName.equals(pkgName3)) return;

        XposedBridge.log("zzz pkg" + lpparam.packageName);

        XC_MethodHook methodHook = new MethodHookContext();

        //findAndHookMethod(WrapperProxy, lpparam.classLoader, "attachBaseContext", new XC_MethodHook() {
        findAndHookMethod(ContextWrapper.class, "attachBaseContext", Context.class, methodHook);
    }
}
