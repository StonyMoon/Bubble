package com.stonymoon.bubble.ui;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.Activity;


import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.stonymoon.bubble.R;
import com.stonymoon.bubble.util.HttpUtil;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;


import org.apaches.commons.codec.digest.DigestUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends Activity {


    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
    };
    Map<String, Object> parameters = new HashMap<String, Object>();

    @BindView(R.id.et_login_phone_number)
    TextView phoneNumberView;
    @BindView(R.id.et_login_password)
    EditText passwordView;
    @BindView(R.id.pbar_login_progress)
    ProgressBar progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin(phoneNumberView.getText().toString(), passwordView.getText().toString());
                    return true;
                }
                return false;
            }
        });


    }

    //申请权限
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(phoneNumberView, "登陆成功", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }


    private void attemptLogin(String phoneNum, String password) {
        String url = phoneNum + "/" + password;

        HttpUtil.sendHttpRequest(this)
                .rxGet(url, parameters, new RxStringCallback() {
                    @Override
                    public void onNext(Object tag, String response) {
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {
                        Toast.makeText(LoginActivity.this, "加载失败，请检查网络", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {

                    }
                });

    }

    private String token(String phone, double latitude, double longitude) {
        String key = phone + latitude + longitude + "stonymoon";
        return DigestUtils.md5Hex(key);

    }


}

