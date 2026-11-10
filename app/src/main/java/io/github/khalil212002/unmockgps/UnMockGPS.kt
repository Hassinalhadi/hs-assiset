package io.github.khalil212002.unmockgps

import android.location.Location
import android.os.Bundle
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class UnMockGPS : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        // 1. THE LOCATION FIX (Mimic the Military Jammer)
        try {
            val locationClass = Location::class.java.name

            // Beat standard mock detectors
            XposedHelpers.findAndHookMethod(locationClass, lpparam.classLoader, "isMock", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = false
                }
            })
            XposedHelpers.findAndHookMethod(locationClass, lpparam.classLoader, "isFromMockProvider", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = false
                }
            })

            // Add realistic hardware accuracy and altitude
            XposedHelpers.findAndHookMethod(locationClass, lpparam.classLoader, "getAccuracy", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = 5.0f
                } // 5 meters
            })
            XposedHelpers.findAndHookMethod(locationClass, lpparam.classLoader, "hasAccuracy", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })

            // Scrub the Bundle of hidden fake-gps flags
            XposedHelpers.findAndHookMethod(locationClass, lpparam.classLoader, "getExtras", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val extras = param.result as? Bundle
                    if (extras != null) {
                        extras.remove("mockLocation")
                        param.result = extras
                    }
                }
            })
        } catch (e: Throwable) {
            XposedBridge.log("UnmockDebug: Location hook failed ${e.message}")
        }
    }
}