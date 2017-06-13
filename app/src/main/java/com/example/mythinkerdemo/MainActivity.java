package com.example.mythinkerdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mythinkerdemo.tinker.app.BaseBuildInfo;
import com.example.mythinkerdemo.tinker.app.BuildInfo;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

public class MainActivity extends AppCompatActivity {

    Button add;
    private Button cancel;
    private Button mShowInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Toast.makeText(getApplicationContext(),"i am change,new patch",Toast.LENGTH_SHORT).show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Tinker.with(getApplicationContext()).isTinkerLoaded()) {
                    Log.e("patch", "i am loading patch ");
                    Toast.makeText(getApplicationContext(), "i am loading patch ", Toast.LENGTH_SHORT).show();

                    TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                            Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + "/patch_signed_7zip.apk");
                    if (Tinker.with(getApplicationContext()).isTinkerLoaded()) {
                        Log.e("patch", "i am loading patch sucess");
                        Toast.makeText(getApplicationContext(), "i am loading patch sucess", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.e("patch", "i am loading patch false");
                        Toast.makeText(getApplicationContext(), "i am loading patch false,no find patch", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.e("patch", "patch is loaded to add");
                    Toast.makeText(getApplicationContext(), "patch is loaded to add", Toast.LENGTH_SHORT).show();
                }


            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Tinker.with(getApplicationContext()).isTinkerLoaded()) {
                    Tinker.with(getApplicationContext()).cleanPatch();
                    Log.e("patch", "i am cleanning patch");
                    Toast.makeText(getApplicationContext(), "i am cleanning patch", Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("patch", "no patch is loaded to cancel");
                    Toast.makeText(getApplicationContext(), "no patch is loaded to cancel", Toast.LENGTH_SHORT).show();

                }

            }
        });

        mShowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo(MainActivity.this);
            }
        });

        Log.e("change","i am change,new patch");

    }

    private void initView() {
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
        mShowInfo = (Button) findViewById(R.id.show_info);

    }

    public boolean showInfo(Context context) {
        // add more Build Info
        final StringBuilder sb = new StringBuilder();
        Tinker tinker = Tinker.with(getApplicationContext());
        if (tinker.isTinkerLoaded()) {
            sb.append(String.format("[patch is loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName(ShareConstants.TINKER_ID)));
            sb.append(String.format("[packageConfig patchMessage] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName("patchMessage")));
            sb.append(String.format("[TINKER_ID Rom Space] %d k \n", tinker.getTinkerRomSpace()));

        } else {
            sb.append(String.format("[patch is not loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", ShareTinkerInternals.getManifestTinkerID(getApplicationContext())));
        }
        sb.append(String.format("[BaseBuildInfo Message] %s \n", BaseBuildInfo.TEST_MESSAGE));

        final TextView v = new TextView(context);
        v.setText(sb);
        v.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        v.setTextColor(0xFF000000);
        v.setTypeface(Typeface.MONOSPACE);
        final int padding = 16;
        v.setPadding(padding, padding, padding, padding);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(v);
        final AlertDialog alert = builder.create();
        alert.show();
        return true;
    }
}
