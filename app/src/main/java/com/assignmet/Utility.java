package com.assignmet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;

public class Utility {
    public  static String convertImageBase64(String  path){
        String imageString="";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return imageString;
    }
    public static boolean checkExternalStoragePermission(Activity context)
    {
        String permission = "android.permission.READ_EXTERNAL_STORAGE";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    public static void readExPermissionDialog(Activity context){

        String permission = "android.permission.READ_EXTERNAL_STORAGE";
        ActivityCompat.requestPermissions(context, new String[]{permission}, Constants.READ_EXTERNAL_STORAGE);
    }

    public static boolean checkCameraPermission(Activity mContext) {

        String permission = "android.permission.READ_EXTERNAL_STORAGE";
        String permission1 = "android.permission.CAMERA";
        int res = mContext.checkCallingOrSelfPermission(permission);
        int res1 = mContext.checkCallingOrSelfPermission(permission1);
        return (res == PackageManager.PERMISSION_GRANTED && res1 == PackageManager.PERMISSION_GRANTED);
    }
    public static void cameraPermissionDialog(Activity context){

        String permission = "android.permission.READ_EXTERNAL_STORAGE";
        String permission1 = "android.permission.CAMERA";
        ActivityCompat.requestPermissions(context, new String[]{permission,permission1}, Constants.CAMERA_PERMISSION_CODE);
    }

    public static void writePermissionDialog(Activity context){

        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        ActivityCompat.requestPermissions(context, new String[]{permission}, Constants.READ_EXTERNAL_STORAGE);
    }
    public static void toastShort(Activity mContext, String message) {

        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Uri uri, Context context ) {
        String filePath = "";
        if (uri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();

        }else if (isDownloadsDocument(uri)) {
            final String id = DocumentsContract.getDocumentId(uri);
            if (!TextUtils.isEmpty(id)) {
                if (id.startsWith("raw:")) {
                    return id.replaceFirst("raw:", "");
                }
                try {
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                } catch (NumberFormatException e) {
                    Log.e("FileUtils", "Downloads provider returned unexpected uri " + uri.toString(), e);
                    return null;
                }
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        return filePath;
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }
    public static String getRealPathFromURI(String contentURI,Activity mContext) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = mContext.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }
}
