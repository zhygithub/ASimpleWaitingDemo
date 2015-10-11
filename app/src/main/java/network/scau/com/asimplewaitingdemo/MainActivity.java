package network.scau.com.asimplewaitingdemo;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        view = (WaitingAnimationView) findViewById(R.id.id_wv);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if(show){
////                    view.cancle();
////                    show= false;
////                }else{
////                    view.start();
////                    show = true;
////                }
//                view.finish();
//            }
//        });
//
        rl = (Button) findViewById(R.id.rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaitingView.showWaitingView(MainActivity.this, v,0x3300ff00,40f);

            }
        });

//        View layoutView = LayoutInflater.from(this).inflate(R.layout.pupop, null);
//        wv = (WaitingAnimationView) layoutView.findViewById(R.id.id_wv);
//        wv.setListener(finish);
//        wv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if(show){
////                    view.cancle();
////                    show= false;
////                }else{
////                    view.start();
////                    show = true;
////                }
//                wv.finish();
//            }
//        });
//        popupWindow = new PopupWindow(layoutView, 300, 300,
//                false);
//        rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
//                wv.start();
//            }
//        });


//        if (popupWindow != null)
//            popupWindow.dismiss();
//        popupWindow = null;


    }

    public void click(View view) {
        if (WaitingView.isExist()) {
            WaitingView.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
