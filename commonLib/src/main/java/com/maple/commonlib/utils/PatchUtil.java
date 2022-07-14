package com.maple.commonlib.utils;

/**
 *  new Thread(() -> {
 *                 File oldApkFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "old.apk");
 *                 File newApkFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "new.apk");
 *                 File patchFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "patch");
 *                 PatchUtil.patchAPK(oldApkFile.getAbsolutePath(),newApkFile.getAbsolutePath(),patchFile.getAbsolutePath());
 *                 //安装APK
 *                 AppUtils.installApp(newApkFile);
 *  }).start();
 */
public class PatchUtil {
    static {
        System.loadLibrary("native-lib");
    }
    /**
     * native方法 使用路径为oldApkPath的apk与路径为patchPath的补丁包，合成新的apk，并存储于newApkPath
     *
     * 返回：0，说明操作成功
     *
     * @param oldApkPath 示例:/sdcard/old.apk
     * @param newApkPath 示例:/sdcard/new.apk
     * @param patchPath  示例:/sdcard/xx.patch
     * @return
     */
    public static native int patch(String oldApkPath, String newApkPath,
                                   String patchPath);
}
