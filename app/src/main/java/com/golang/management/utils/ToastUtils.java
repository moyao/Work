package com.golang.management.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.ClipboardManager;
import android.widget.Toast;

public class ToastUtils {
    private static ProgressDialog dialog;
    private static Dialog dialogs;
    public static void showToast(Context ctx, String text) {
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
    }
    //返回一个列表对话框
    public static android.app.AlertDialog.Builder getListDialogBuilder(Context context, String[] items, String title, DialogInterface.OnClickListener clickListener) {
        return new AlertDialog.Builder(context).setTitle(title).setItems(items, clickListener);
    }
    public static void copy(String content, Context context) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }
}
