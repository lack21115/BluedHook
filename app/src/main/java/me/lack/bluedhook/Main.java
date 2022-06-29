package me.lack.bluedhook;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import java.util.List;

import android.content.Context;
import android.content.ContextWrapper;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage {

    public static String pkgName1 = "com.soft.blued";
    public static String pkgName2 = "com.danlan.xiaolan";
    public static String WrapperProxy = ".MyWrapperProxyApplication";
    public static String MsgChattingPresent = ".ui.msg.presenter.MsgChattingPresent";
    public static String chatHelper = ".ui.msg.controller.tools.ChatHelperV4";

    public static int RECALL_BURNING_PIC = 1;
    public static int RECALL_BURNING_VIDEO = 2;
    public static int RECALL_MESSAGE = 0;

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(pkgName1) && !lpparam.packageName.equals(pkgName2)) return;
        lpparam.packageName = pkgName1;

        XposedBridge.log("zzz pkg" + lpparam.packageName);

        final ClassLoader[] classLoader = {null};
        final XC_MethodHook methodHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String classPath = lpparam.packageName + chatHelper;
                XposedBridge.log("zzz pkg class1" + classPath);
                List<Object> lst = (List<Object>)param.args[0];
                for (Object obj: lst) {
                    boolean isSelf = (boolean)XposedHelpers.callMethod(obj, "isFromSelf");
                    short msgType = XposedHelpers.getShortField(obj, "msgType");
                    Object msgContent = XposedHelpers.getObjectField(obj, "msgContent");
                    // XposedBridge.log("zzz type " + type);
                    // XposedBridge.log("zzz msgContent " + msgContent.toString());
                    if (isSelf && msgType == 1 && "test".equals(msgContent.toString())) {
                        XposedHelpers.setObjectField(obj, "msgContent", "hook");
                        continue;
                    }
                    if (isSelf) continue;

                    if (msgType == 24) {
                        Object instance = XposedHelpers.callStaticMethod(XposedHelpers.findClass(classPath, classLoader[0]), "a");
                        String flashPath = XposedHelpers.callMethod(instance, "a", obj).toString();
                        XposedHelpers.setShortField(obj, "msgType", (short)2);
                        XposedHelpers.setObjectField(obj, "msgContent", flashPath);
                    }

                    if ("".equals(msgContent.toString())) continue;

                    if (msgType == 55) {
                        String key = "61C0A240C4AF5F16DA0738512255BA16";
                        if (msgContent.contains(key)) {
                            String data = msgContent.split(key);
                            XposedHelpers.setObjectField(obj, "msgContent", data[0]);
                            XposedHelpers.setShortField(obj, "msgType", Short.parseShort(data[1]));
                        } else if (msgContent.contains("blued-burn")) {
                            XposedHelpers.setShortField(obj, "msgType", 24);
                        } else if (msgContent.contains("blued-chatfiles") && (msgContent.contains("jpg") || msgContent.contains("png"))) {
                            XposedHelpers.setShortField(obj, "msgType", 2);
                        } else if (msgContent.contains("blued-chatfiles") && (msgContent.contains("mp3"))) {
                            XposedHelpers.setShortField(obj, "msgType", 3);
                        } else {
                            XposedHelpers.setShortField(obj, "msgType", 1);
                        }
                    }
                }

            }
        };

        //findAndHookMethod(WrapperProxy, lpparam.classLoader, "attachBaseContext", new XC_MethodHook() {
        findAndHookMethod(ContextWrapper.class, "attachBaseContext", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Context ctt = (Context)param.args[0];
                classLoader[0] = ctt.getClassLoader();
                String classPath = lpparam.packageName + chatHelper;
                XposedBridge.log("zzz class0" + classPath);
                findAndHookMethod(lpparam.packageName + MsgChattingPresent, classLoader[0], "onMsgDataChanged", List.class, methodHook);
            }
        });
    }
}
