package de.sh86.exchangepolicybypass;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by Sebastian Heinecke on 11.12.2016.
 */
public class Main implements IXposedHookLoadPackage {
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.samsung.android.email.provider")) {
            try {
                XposedBridge.log("sh86 ExchBypass loaded: " + lpparam.packageName);

                findAndHookMethod("com.samsung.android.email.sync.exchange.adapter.ProvisionParser", lpparam.classLoader, "hasSupportablePolicySet", new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("ProvisionParser: hasSupportablePolicySet - true");
                        return true;
                    }
                });
                findAndHookMethod("com.samsung.android.email.provider.SecurityPolicy", lpparam.classLoader, "isActive","com.samsung.android.emailcommon.service.PolicySet", new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("SecurityPolicy: isActive - true");
                        return true;
                    }
                });
                findAndHookMethod("com.samsung.android.email.provider.SecurityPolicy", lpparam.classLoader, "isActiveAdmin", new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("SecurityPolicy: isActiveAdmin - true");
                        return true;
                    }
                });
            } catch (Exception e) {
                XposedBridge.log("Exception: " + e.getMessage());
            }
        }
    }
}