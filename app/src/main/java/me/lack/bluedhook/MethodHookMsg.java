package me.lack.bluedhook;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class MethodHookMsg extends XC_MethodHook {

    public static short MSG_TYPE_TEXT       = 1;
    public static short MSG_TYPE_PIC        = 2;
    public static short MSG_TYPE_MUSIC      = 3;
    public static short MSG_TYPE_VIDEO      = 5;
    public static short MSG_TYPE_BURN_IMG   = 24;
    public static short MSG_TYPE_BURN_VIDEO = 25;
    public static short MSG_TYPE_RECALL     = 55;

    public static String ADDITIONAL_FIELD_NOTIFY = "notify";

    @Override
    protected void beforeHookedMethod(MethodHookParam param) {
        String classPath = Main.pkgName1 + Main.chatHelper;
        XposedBridge.log("[BluedHook] pkg class1" + classPath);

        Object instance = XposedHelpers.callStaticMethod(XposedHelpers.findClass(classPath, Main.classLoader), "a");
        List<Object> lst = (List<Object>)param.args[0];

        for (Object obj: lst) {
            boolean isSelf = (boolean)XposedHelpers.callMethod(obj, "isFromSelf");
            short msgType = XposedHelpers.getShortField(obj, "msgType");
            Object msgContent = XposedHelpers.getObjectField(obj, "msgContent");
            String msgContentStr = msgContent.toString();

            XposedBridge.log("[BluedHook] type " + msgType);
            XposedBridge.log("[BluedHook] msgContent " + msgContentStr);

            if (isSelf && msgType == MSG_TYPE_TEXT && "test".equals(msgContentStr)) {
                XposedHelpers.setObjectField(obj, "msgContent", "hook");
                continue;
            }
            if (isSelf) continue;

            if (msgType == MSG_TYPE_BURN_IMG) {
                String flashPath = XposedHelpers.callMethod(instance, "a", obj).toString();
                XposedHelpers.setShortField(obj, "msgType", MSG_TYPE_PIC);
                XposedHelpers.setObjectField(obj, "msgContent", flashPath);
                XposedHelpers.setAdditionalInstanceField(obj, ADDITIONAL_FIELD_NOTIFY, "对方发来一张闪照");
            }

            if (msgType == MSG_TYPE_BURN_VIDEO) {
                String flashPath = XposedHelpers.callMethod(instance, "b", obj).toString();
                XposedHelpers.setShortField(obj, "msgType", MSG_TYPE_VIDEO);
                XposedHelpers.setObjectField(obj, "msgContent", flashPath);
                XposedHelpers.setAdditionalInstanceField(obj, ADDITIONAL_FIELD_NOTIFY, "对方发来一个闪拍");
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
                XposedHelpers.setAdditionalInstanceField(obj, ADDITIONAL_FIELD_NOTIFY, "对方撤回了一条消息");
            }
        }

    }
}
