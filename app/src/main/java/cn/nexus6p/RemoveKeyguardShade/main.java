package cn.nexus6p.RemoveKeyguardShade;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.setFloatField;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class main implements IXposedHookLoadPackage{
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.android.systemui"))
			return;

        final Class clazz = XposedHelpers.findClass("com.android.systemui.statusbar.phone.ScrimController",lpparam.classLoader);

        try {
        findAndHookMethod(clazz, "updateScrimKeyguard", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                setFloatField(param.thisObject,"mScrimBehindAlphaKeyguard",1.0E-4f);
            }
            });
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        try {
            XposedBridge.hookAllConstructors(clazz, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    setFloatField(param.thisObject,"mScrimBehindAlphaKeyguard",1.0E-4f);
                }
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    setFloatField(param.thisObject,"mScrimBehindAlphaKeyguard",1.0E-4f);
                }
            });
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod(clazz, "scheduleUpdate", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    Object[] states = (Object[]) XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.android.systemui.statusbar.phone.ScrimState", lpparam.classLoader), "values");
                    final float alpha = 1.0E-4f;
                    for (Object state : states) {
                        XposedHelpers.callMethod(state, "setScrimBehindAlphaKeyguard", alpha);
                    }
                }
            });
        } catch (Throwable t) {
            t.printStackTrace();
        }
	}

    /*@Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XResources.setSystemWideReplacement("android","color","system_bar_background_semi_transparent", 0);
    }*/
}
