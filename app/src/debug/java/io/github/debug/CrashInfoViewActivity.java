package io.github.debug;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ivan.github.BuildConfig;
import com.ivan.github.R;
import com.ivan.github.app.ToolbarActivity;
import com.ivan.github.common.util.ExceptionUtils;

/**
 * display crash information
 *
 * @author  Ivan on 2017/6/6 11:12.
 * @version v0.1
 * @since   v1.0
 */
public class CrashInfoViewActivity extends ToolbarActivity implements View.OnClickListener {
    public static final String EXTRA_DETAIL = CrashInfoViewActivity.class.getName() + ".detail";

    private TextView mTvTvDetail;

    private Throwable mThrowable;

    public static void start(Context context, Throwable e) {
        Intent i = new Intent(context, CrashInfoViewActivity.class);
        i.putExtra(EXTRA_DETAIL, e);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_info_view);
        initView();
        initData();
    }

    /**
     * initialize the view
     */
    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.crash_info);
        ActionBar actionBar = getSupportActionBar();
        setSupportActionBar(toolbar);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        TextView tvVersionCode = findViewById(R.id.tv_version_code);
        TextView tvVersionName = findViewById(R.id.tv_version_name);
        mTvTvDetail = findViewById(R.id.tv_detail);
        FloatingActionButton mFab = findViewById(R.id.fab);

        tvVersionCode.setText(String.valueOf(BuildConfig.VERSION_CODE));
        tvVersionName.setText(BuildConfig.VERSION_NAME);
        mFab.setOnClickListener(this);
    }

    /**
     * initialize the data
     */
    private void initData() {
        Intent intent = getIntent();
        mThrowable = (Throwable) intent.getSerializableExtra(EXTRA_DETAIL);
        if (mThrowable == null) {
            return;
        }
        mTvTvDetail.setText(ExceptionUtils.getExceptionStack(mThrowable));
    }

    @Override
    public void onClick(View v) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("Crash Information", ExceptionUtils.getCrashInfo(mThrowable));
        clipboardManager.setPrimaryClip(data);
        Snackbar.make(mTvTvDetail, R.string.copied, Snackbar.LENGTH_SHORT).show();
    }
}
