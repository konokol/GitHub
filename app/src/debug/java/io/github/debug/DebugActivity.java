package io.github.debug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ivan.github.R;
import com.ivan.github.app.ToolbarActivity;

/**
 * @author Ivan
 */
public class DebugActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv1 = findViewById(R.id.debug_text);
        TextView tv2 = findViewById(R.id.debug_text2);

        String debugStr = getString(R.string.debug_text);

        tv1.setText(debugStr);
        tv2.setText(debugStr);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_debug;
    }

    @SuppressWarnings("NumericOverflow")
    public void makeCrash(View view) {
        int a = 10 / 0;
    }

    public void getPhoneInformation(View view) {
        startActivity(new Intent(this, PhoneInformationActivity.class));
    }
}
