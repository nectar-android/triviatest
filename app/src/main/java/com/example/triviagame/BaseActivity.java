package com.example.triviagame;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.triviagame.utils.Utils;

import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * this is common activity which extends AppCompatActivity
 * and this used and base of all other activity
 */
public class BaseActivity extends AppCompatActivity {

    public static BaseActivity baseActivity;
    public Utils utils = Utils.getInstance();//single ton class
    public ProgressDialog dialog;
    //used in login screen
    private ArrayList<String> manifests;

    /**
     * @return single instance of base class
     */
    public static BaseActivity getInstance() {
        if (baseActivity == null) {
            synchronized (Utils.class) {
                if (baseActivity == null) {
                    baseActivity = new BaseActivity();

                }
            }
        }
        return baseActivity;
    }

    /**
     * dismissing keyboard on outside
     */
    public static void hideSoftKeyboard(AppCompatActivity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            AppCompatActivity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * progress indicator to show before background call
     */
    public void startProgress(Activity activity) {
        try {
            if (dialog == null)
                dialog = new ProgressDialog(activity);
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(R.layout.dialog_progress);

            /*
             * dismiss dialog in 4 second , if got any screen freezing issue
             * */
            new CountDownTimer(1000, 4000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    dismissProgress();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * to load progress bar
     */
    public void dismissProgress() {
        try {
            if (dialog != null) {
                dialog.cancel();
                dialog.dismiss();
            }
            dialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        //for single instance of context
        if (utils.context == null) {
            synchronized (BaseActivity.class) {
                if (utils.context == null) {
                    utils.context = BaseActivity.this;
                    utils.initializeObj();//initializing objects
                }
            }
        }
//        manifests = new ArrayList<String>();
//        permissionList();
//        if (checkAndRequestPermissions()) {
//        }
    }

    /**
     * Add all required permission to be allowed by user
     */
    private void permissionList() {
        manifests.add(READ_PHONE_STATE);
//        manifests.add(ACCESS_FINE_LOCATION);
//        manifests.add(ACCESS_COARSE_LOCATION);
        manifests.add(READ_EXTERNAL_STORAGE);
        manifests.add(WRITE_EXTERNAL_STORAGE);
//        manifests.add(CAMERA);
    }

    public void appSetting() {
        new AlertDialog.Builder(this)
                .setMessage("Application permission required to start survey")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }


    /**
     * off backbutton from all pages
     */
    @Override
    public void onBackPressed() {
    }


}
