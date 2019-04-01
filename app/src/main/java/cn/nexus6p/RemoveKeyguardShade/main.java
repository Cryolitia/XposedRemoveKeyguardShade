package cn.nexus6p.RemoveKeyguardShade;

import android.content.res.XResources;
import android.graphics.Color;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.setFloatField;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class main implements IXposedHookLoadPackage, IXposedHookZygoteInit {
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.android.systemui"))
			return;

        final Class clazz = XposedHelpers.findClass("com.android.systemui.statusbar.phone.ScrimController",lpparam.classLoader);
        findAndHookMethod(clazz, "updateScrimKeyguard", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                setFloatField(param.thisObject,"mScrimBehindAlphaKeyguard",1.0E-4f);
            }
        });

	}

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XResources.setSystemWideReplacement("android","color","system_bar_background_semi_transparent", 0);
    }
}
