package com.zzt.zt_snackbar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class TestSnackbarUtilsActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context = null;
    private Button bt_memory,crazybutton;
    private Button bt_short,bt_long,bt_indefinite,bt_length_custom,
            bt_info,bt_confirm,bt_warn,bt_danger,bt_back_custom,
            bt_color_message,bt_color_action,bt_back_alpha,bt_action,bt_callback,
            bt_message_gravity_default,bt_message_gravity_center,bt_message_gravity_right,
            bt_message_leftright_drawable,bt_addview,bt_radius,bt_radius_stroke,
            bt_gravity_default,bt_gravity_top,bt_gravity_center,
            bt_margins,bt_above,bt_bellow,bt_multimethods;
    private SnackbarUtils instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_snackbar_utils);
        context = this;

        //
        crazybutton = (Button) findViewById(R.id.crazybutton);
        crazybutton.setOnClickListener(this);
        bt_memory = (Button) findViewById(R.id.bt_memory);
        bt_memory.setOnClickListener(this);
        //
        bt_short = (Button) findViewById(R.id.bt_short);
        findViewById(R.id.bt_short).setOnClickListener(this);
        bt_long = (Button) findViewById(R.id.bt_long);
        findViewById(R.id.bt_long).setOnClickListener(this);
        bt_indefinite = (Button) findViewById(R.id.bt_indefinite);
        findViewById(R.id.bt_indefinite).setOnClickListener(this);
        bt_length_custom = (Button) findViewById(R.id.bt_length_custom);
        findViewById(R.id.bt_length_custom).setOnClickListener(this);
        bt_info = (Button) findViewById(R.id.bt_info);
        findViewById(R.id.bt_info).setOnClickListener(this);
        bt_confirm = (Button) findViewById(R.id.bt_confirm);
        findViewById(R.id.bt_confirm).setOnClickListener(this);
        bt_warn = (Button) findViewById(R.id.bt_warn);
        findViewById(R.id.bt_warn).setOnClickListener(this);
        bt_danger = (Button) findViewById(R.id.bt_danger);
        findViewById(R.id.bt_danger).setOnClickListener(this);
        bt_back_custom = (Button) findViewById(R.id.bt_back_custom);
        findViewById(R.id.bt_back_custom).setOnClickListener(this);
        bt_color_message = (Button) findViewById(R.id.bt_color_message);
        findViewById(R.id.bt_color_message).setOnClickListener(this);
        bt_color_action = (Button) findViewById(R.id.bt_color_action);
        findViewById(R.id.bt_color_action).setOnClickListener(this);
        bt_back_alpha = (Button) findViewById(R.id.bt_back_alpha);
        findViewById(R.id.bt_back_alpha).setOnClickListener(this);
        bt_action = (Button) findViewById(R.id.bt_action);
        findViewById(R.id.bt_action).setOnClickListener(this);
        bt_callback = (Button) findViewById(R.id.bt_callback);
        findViewById(R.id.bt_callback).setOnClickListener(this);
        bt_message_gravity_default = (Button) findViewById(R.id.bt_message_gravity_default);
        findViewById(R.id.bt_message_gravity_default).setOnClickListener(this);
        bt_message_gravity_center = (Button) findViewById(R.id.bt_message_gravity_center);
        findViewById(R.id.bt_message_gravity_center).setOnClickListener(this);
        bt_message_gravity_right = (Button) findViewById(R.id.bt_message_gravity_right);
        findViewById(R.id.bt_message_gravity_right).setOnClickListener(this);
        bt_message_leftright_drawable = (Button) findViewById(R.id.bt_message_leftright_drawable);
        findViewById(R.id.bt_message_leftright_drawable).setOnClickListener(this);
        bt_addview = (Button) findViewById(R.id.bt_addview);
        findViewById(R.id.bt_addview).setOnClickListener(this);
        bt_radius = (Button) findViewById(R.id.bt_radius);
        findViewById(R.id.bt_radius).setOnClickListener(this);
        bt_radius_stroke = (Button) findViewById(R.id.bt_radius_stroke);
        findViewById(R.id.bt_radius_stroke).setOnClickListener(this);
        bt_gravity_default = (Button) findViewById(R.id.bt_gravity_default);
        findViewById(R.id.bt_gravity_default).setOnClickListener(this);
        bt_gravity_top = (Button) findViewById(R.id.bt_gravity_top);
        findViewById(R.id.bt_gravity_top).setOnClickListener(this);
        bt_gravity_center = (Button) findViewById(R.id.bt_gravity_center);
        findViewById(R.id.bt_gravity_center).setOnClickListener(this);
        bt_margins = (Button) findViewById(R.id.bt_margins);
        findViewById(R.id.bt_margins).setOnClickListener(this);
        bt_above = (Button) findViewById(R.id.bt_above);
        findViewById(R.id.bt_above).setOnClickListener(this);
        bt_bellow = (Button) findViewById(R.id.bt_bellow);
        findViewById(R.id.bt_bellow).setOnClickListener(this);
        bt_multimethods = (Button) findViewById(R.id.bt_multimethods);
        findViewById(R.id.bt_multimethods).setOnClickListener(this);
        instance = SnackbarUtils.Long(bt_confirm,"?????????:confirm").confirm();
    }

    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.e("Jet","Activity is null?"+(TestSnackbarUtilsActivity.this == null));
            if(TestSnackbarUtilsActivity.this.isDestroyed()){
                Log.e("Jet","???????????????");
                instance.show();
            }else{
                Log.e("Jet","?????????,20s???????????????");
                finish();
                handler.postDelayed(runnable,1000 * 20);
            }
        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_memory:
                //????????????????????????
                finish();
                handler.postDelayed(runnable,1000 * 20);
//                instance.show();
                break;
            case R.id.bt_short:
                //TODO implement
                SnackbarUtils.Short(bt_short,"????????????:???+info").info().show();
                break;
            case R.id.bt_long:
                //TODO implement
                SnackbarUtils.Long(bt_long,"????????????:???+info").info().show();
                break;
            case R.id.bt_indefinite:
                //TODO implement
                SnackbarUtils.Indefinite(bt_indefinite,"????????????:??????+info").info().show();
                break;
            case R.id.bt_length_custom:
                //TODO implement
                SnackbarUtils.Custom(bt_length_custom,"????????????:????????? 3???+info",1000*3).info().show();
                break;
            case R.id.bt_info:
                //TODO implement
                SnackbarUtils.Short(bt_info,"?????????:info").info().show();
                break;
            case R.id.bt_confirm:
                //TODO implement
                SnackbarUtils.Short(bt_confirm,"?????????:confirm").confirm().show();
                break;
            case R.id.bt_warn:
                //TODO implement
                SnackbarUtils.Short(bt_warn,"?????????:warning").warning().show();
                break;
            case R.id.bt_danger:
                //TODO implement
                SnackbarUtils.Short(bt_danger,"?????????:danger").danger().show();
                break;
            case R.id.bt_back_custom:
                //TODO implement
                SnackbarUtils.Short(bt_back_custom,"?????????:?????????").backColor(0XFF116699).show();
                break;
            case R.id.bt_color_message:
                //TODO implement
                SnackbarUtils.Short(bt_color_message,"??????TextView????????????").messageColor(Color.RED).show();
                break;
            case R.id.bt_color_action:
                //TODO implement
                SnackbarUtils.Short(bt_color_action,"??????Button????????????").actionColor(Color.GREEN).setAction("??????", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Button????????????",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
            case R.id.bt_back_alpha:
                //TODO implement
                SnackbarUtils.Short(bt_back_alpha,"?????????????????????").alpha(0.6f).show();
                break;
            case R.id.bt_action:
                //TODO implement
                SnackbarUtils.Short(bt_action,"?????????????????????????????????").setAction("????????????", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"???????????????!",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
            case R.id.bt_callback:
                //TODO implement
                SnackbarUtils.Short(bt_callback,"???????????????????????????").setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        Toast.makeText(context,"onDismissed!",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onShown(Snackbar snackbar) {
                        super.onShown(snackbar);
                        Toast.makeText(context,"onShown!",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
            case R.id.bt_message_gravity_default:
                //TODO implement
                SnackbarUtils.Short(bt_message_gravity_default,"????????????:??????").info().gravityFrameLayout(Gravity.CENTER).show();
                break;
            case R.id.bt_message_gravity_center:
                //TODO implement
                SnackbarUtils.Short(bt_message_gravity_center,"????????????:??????").confirm().gravityFrameLayout(Gravity.CENTER).messageCenter().show();
                break;
            case R.id.bt_message_gravity_right:
                //TODO implement
                SnackbarUtils.Short(bt_message_gravity_right,"????????????:??????").warning().gravityFrameLayout(Gravity.CENTER).messageRight().show();
                break;
            case R.id.bt_message_leftright_drawable:
                //TODO implement
                SnackbarUtils.Short(bt_message_leftright_drawable,"????????????????????????????????????").leftAndRightDrawable(R.mipmap.i9,R.mipmap.i11).show();
                break;
            case R.id.bt_addview:
                //TODO implement
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.mipmap.ic_launcher);
                SnackbarUtils.Short(bt_addview,"???Snackbar???????????????View").addView(imageView,0).show();
                break;
            case R.id.bt_radius:
                //TODO implement
                SnackbarUtils.Short(bt_radius,"?????????????????????").radius(24).show();
                break;
            case R.id.bt_radius_stroke:
                //TODO implement
                SnackbarUtils.Short(bt_radius_stroke,"??????????????????????????????").radius(30,4,Color.GREEN).show();
                break;
            case R.id.bt_gravity_default:
                //TODO implement
                SnackbarUtils.Short(bt_gravity_default,"Snackbar??????:??????").show();
                break;
            case R.id.bt_gravity_top:
                //TODO implement
                SnackbarUtils.Short(bt_gravity_top,"Snackbar??????:??????").gravityFrameLayout(Gravity.TOP).show();
                break;
            case R.id.bt_gravity_center:
                //TODO implement
                SnackbarUtils.Short(bt_gravity_center,"Snackbar??????:??????").gravityFrameLayout(Gravity.CENTER).show();
                break;
            case R.id.bt_margins:
                //TODO implement
                SnackbarUtils.Short(bt_margins,"??????Snackbar??????????????????").margins(16).show();
                break;
            case R.id.bt_above:
                //TODO implement
                int total = 0;
                //??????R.id.Content????????????????????????
                //?????????:????????????(???????????????Activity????????????,???????????????TitleBar?????????)
                int statusbarHeight = ScreenUtil.getStatusHeight(context);
                int actionbarHeight = ScreenUtil.getActionBarHeight(context);
//                total = statusbarHeight + actionbarHeight;
                //?????????:???Activity?????????????????????R.id.Content????????????????????????
                int[] l1 = new int[2];
                getWindow().findViewById(android.R.id.content).getLocationInWindow(l1);
                total = l1[1];
                SnackbarUtils.Short(bt_above,"??????Snackbar???????????????View?????????").above(bt_gravity_center,total,16,16).show();
                break;
            case R.id.bt_bellow:
                //TODO implement
                int total1 = 0;
                int[] l2 = new int[2];
                getWindow().findViewById(android.R.id.content).getLocationInWindow(l2);
                total1 = l2[1];
                SnackbarUtils.Short(bt_bellow,"??????Snackbar???????????????View?????????").bellow(bt_gravity_center,total1,32,32).show();
                break;
            case R.id.bt_multimethods:
                int total2 = 0;
                int[] l3 = new int[2];
                getWindow().findViewById(android.R.id.content).getLocationInWindow(l3);
                total2 = l3[1];
                SnackbarUtils.Custom(bt_multimethods,"5s+??????drawable+?????????+???????????????+??????View??????",1000*5)
                        .leftAndRightDrawable(R.mipmap.i10,R.mipmap.i11)
                        .backColor(0XFF668899)
                        .radius(16,4,Color.BLUE)
                        .bellow(bt_margins,total2,16,16)
                        .show();
                break;
            case R.id.crazybutton:
                while (true){
                    Log.e("Jet","???????????????????????????:"+(++mCount));
                    SnackbarUtils.Short(bt_short,"??????????????????"+mCount).info().gravityFrameLayout(Gravity.CENTER).show();
                }
        }
    }
    private int mCount = 0;
}
