package lazy.demo;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv_model;
    int dayNightUiMode;
    private int mDayNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    /**
     * 初始化view
     */
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_model = (TextView) findViewById(R.id.tv_model);
        //不知道为什么没有生效，难道支持支车载模式
        UiModeManager UiModeManagerServise = (UiModeManager) getSystemService(
                Context.UI_MODE_SERVICE);
        UiModeManagerServise.setNightMode(UiModeManager.MODE_NIGHT_AUTO);
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    /**
     * 根据用户设置不同的白天和晚上的不同模式，设置适合白天或者晚上显示的字体的颜色
     */
    @Override protected void onResume() {
        super.onResume();
        //获取显示的模式
        int uiMode = getResources().getConfiguration().uiMode;

        dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;
        uiModeDayOrNight(dayNightUiMode);
    }


    private void uiModeDayOrNight(int dayNightUiMode) {
        if (dayNightUiMode == Configuration.UI_MODE_NIGHT_NO) {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_NO;
            tv_model.setText(R.string.day_mode);
        } else if (dayNightUiMode == Configuration.UI_MODE_NIGHT_YES) {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_YES;
            tv_model.setText(R.string.night_model);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       if (id == R.id.action_day_night_yes) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
            return true;
        } else if (id == R.id.action_day_night_no) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
            return true;
        } else {
            if (id == R.id.action_bottom_sheet_dialog) {
               BottomSheetDialogView.show(this, mDayNightMode);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
