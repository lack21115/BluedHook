package me.lack.bluedhook;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class MethodHookNotify extends XC_MethodHook {

    @Override
    protected void afterHookedMethod(MethodHookParam param) {
        XposedBridge.log("[BluedHook] 333 MethodHookNotify");

        ViewGroup root = (ViewGroup)param.getResult();
        if (root.findViewWithTag(0x0000001) != null) {
            root.removeView(root.findViewWithTag(0x0000001));
        }
        List<Object> data = (List<Object>)XposedHelpers.getObjectField(param.thisObject, "a");
        Integer index = (Integer)param.args[0];
        Object message = data.get(index);
        String notifyText = (String)XposedHelpers.getAdditionalInstanceField(message, MethodHookMsg.ADDITIONAL_FIELD_NOTIFY);
        View notify = MakeNotifyTextView(Main.ctt, notifyText);
        root.addView(notify);
        param.setResult(root);
    }

    protected TextView MakeNotifyTextView(Context context, String s) {
        TextView textView = new TextView(context);

        textView.setTag(1);
        textView.setTextSize(12.0f);
        textView.setText(s);
        textView.setTextColor(Color.parseColor("#ADAFB0"));
        textView.setGravity(1);
        float marginPercent = 4.5f; // dpi=320 -> 480 -> 4.5f
        float densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        if (densityDpi != 540) {
            densityDpi -= 540;
            marginPercent += ((-densityDpi) / 10) * 0.105f;
        }
        textView.setPadding((int) (context.getResources().getDisplayMetrics().widthPixels / marginPercent), 5, 20, 5);

        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //layoutParams.setMargins(60, 40, 60, 0);
        //layoutParams.gravity = 1;
        textView.setLayoutParams(layoutParams);
        return textView;
    }

}

