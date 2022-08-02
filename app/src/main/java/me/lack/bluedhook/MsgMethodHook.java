package me.lack.bluedhook;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class MsgMethodHook extends XC_MethodHook {

    public static short MSG_TYPE_TEXT       = 1;
    public static short MSG_TYPE_PIC        = 2;
    public static short MSG_TYPE_MUSIC      = 3;
    public static short MSG_TYPE_VIDEO      = 5;
    public static short MSG_TYPE_BURN_IMG   = 24;
    public static short MSG_TYPE_BURN_VIDEO = 25;
    public static short MSG_TYPE_RECALL     = 55;

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        String classPath = Main.pkgName1 + Main.chatHelper;
        XposedBridge.log("zzz pkg class1" + classPath);

        Object instance = XposedHelpers.callStaticMethod(XposedHelpers.findClass(classPath, Main.classLoader), "a");
        List<Object> lst = (List<Object>)param.args[0];

        for (Object obj: lst) {
            boolean isSelf = (boolean)XposedHelpers.callMethod(obj, "isFromSelf");
            short msgType = XposedHelpers.getShortField(obj, "msgType");
            Object msgContent = XposedHelpers.getObjectField(obj, "msgContent");
            String msgContentStr = msgContent.toString();

            XposedBridge.log("zzz type " + msgType);
            XposedBridge.log("zzz msgContent " + msgContentStr);

            if (isSelf && msgType == MSG_TYPE_TEXT && "test".equals(msgContentStr)) {
                XposedHelpers.setObjectField(obj, "msgContent", "hook");
                continue;
            }
            if (isSelf) continue;

            if (msgType == MSG_TYPE_BURN_IMG) {
                String flashPath = XposedHelpers.callMethod(instance, "a", obj).toString();
                XposedHelpers.setShortField(obj, "msgType", MSG_TYPE_PIC);
                XposedHelpers.setObjectField(obj, "msgContent", flashPath);
            }

            if (msgType == MSG_TYPE_BURN_VIDEO) {
                String flashPath = XposedHelpers.callMethod(instance, "b", obj).toString();
                XposedHelpers.setShortField(obj, "msgType", MSG_TYPE_VIDEO);
                XposedHelpers.setObjectField(obj, "msgContent", flashPath);
            }

            if ("".equals(msgContentStr)) continue;

            if (msgType == MSG_TYPE_RECALL) {
                if (msgContentStr.startsWith("RU")) {
                    String flashPath = XposedHelpers.callMethod(instance, "a", obj).toString();
                    XposedHelpers.setShortField(obj, "msgType", MSG_TYPE_PIC);
                    XposedHelpers.setObjectField(obj, "msgContent", flashPath);
                } else {
                    XposedHelpers.setShortField(obj, "msgType", MSG_TYPE_TEXT);
                }
            }
        }

    }
}
