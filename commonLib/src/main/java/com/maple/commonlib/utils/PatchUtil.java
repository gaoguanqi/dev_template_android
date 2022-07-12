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
     * 合并APK文件
     * @param oldApkFile 旧APK文件路径
     * @param newApkFile 新APK文件路径（存储生成的APK的路径）
     * @param patchFile 差异文件
     */
    public native static void patchAPK(String oldApkFile,String newApkFile,String patchFile);
}
