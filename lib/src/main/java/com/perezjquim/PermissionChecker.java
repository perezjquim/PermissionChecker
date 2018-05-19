package com.perezjquim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import static com.perezjquim.UIHelper.toast;

public abstract class PermissionChecker
{
    private static Context context;

    private static CheckThread thread;
    private static String[] permissions;

    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    public static final int REQUEST_CODE = 1;
    private static final int MIN_REQUIRED_SDK = 23;
    private static final int SAMPLING_RATE = 1000;

    public static void init(Context _context)
    {
        context = _context;
        try
        {
            permissions = context
                .getPackageManager()
                .getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;

            if (Build.VERSION.SDK_INT >= MIN_REQUIRED_SDK)
            {
                thread = new CheckThread();
                thread.start();
            }
        }
        catch (PackageManager.NameNotFoundException e)
        { e.printStackTrace(); }
    }

    public static void restart()
    {
        thread = new CheckThread();
        thread.start();
    }

    private static void goToSettings()
    {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri =  Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        ((Activity)context).startActivityForResult(intent,REQUEST_CODE);
        toast(context,"Enable all permissions to continue");
    }

    private static boolean isPermissionChecked(String permission)
    {
        return context.checkCallingOrSelfPermission(permission) == GRANTED;
    }

    private static boolean isAllPermissionsChecked()
    {
        for(String p : permissions)
        {
            if(!isPermissionChecked(p))
                return false;
        }
        return true;
    }

    private static class CheckThread extends Thread
    {
        public void run()
        {
            while(isAllPermissionsChecked())
            {
                try
                { Thread.sleep(SAMPLING_RATE); }
                catch (InterruptedException e)
                { e.printStackTrace(); }
            }
            goToSettings();
        }
    }
}
