package me.lack.bluedhook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class MethodHookContext extends XC_MethodHook {

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws ClassNotFoundException {
        Main.ctt = (Context)param.args[0];
        Main.classLoader = Main.ctt.getClassLoader();
        String classPath = Main.pkgName1 + Main.chatHelper;
        XposedBridge.log("zzz class0" + classPath);
        findAndHookMethod(Main.pkgName1 + Main.MsgChattingPresent, Main.classLoader, "onMsgDataChanged", List.class, new MethodHookMsg());
        findAndHookMethod(Main.pkgName1 + Main.MsgNotifyPresent, Main.classLoader, "a", int.class, View.class, ViewGroup.class, new MethodHookNotify());

        Class targetClass = Main.classLoader.loadClass(Main.userInfo);
        Class resultClass = Main.classLoader.loadClass(Main.bluedLoginResult);
        Method[] methods = XposedHelpers.findMethodsByExactParameters(targetClass, resultClass);
        for (Method method: methods) {
            XposedHelpers.findAndHookMethod(Main.userInfo, Main.classLoader, method.getName(), new MethodHookVip());
        }

        XposedHelpers.findAndHookMethod(Main.FlashPhotoManager, Main.classLoader, "b", new MethodHookFlash());
    }
}
