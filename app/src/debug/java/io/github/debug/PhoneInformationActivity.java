package io.github.debug;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.github.utils.SecureSharedPreference;
import com.ivan.github.R;
import com.ivan.github.app.ToolbarActivity;

/**
 * Phone Details
 *
 * @author  Ivan on 2018-12-23 13:10.
 * @version v0.1
 * @since   v0.1.0
 */
public class PhoneInformationActivity extends ToolbarActivity {

    private TextView mTVContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onPostCreateView() {
        super.onPostCreateView();
        toolbar.setTitle(R.string.phone_information);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_phone_infomation;
    }

    private void initView() {
        mTVContent = findViewById(R.id.tv_content);
        mTVContent.setText(getPhoneDetail());
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private String getPhoneDetail() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("serialNum: ").append(SecureSharedPreference.getDeviceSerialNumber(getApplication())).append('\n');
        return stringBuilder.toString();
    }
}
