package com.ivan.github.app;

import androidx.appcompat.widget.Toolbar;

import com.ivan.github.R;

/**
 * Activity with a default toolbar
 *
 * @author  Ivan on 2018-12-23 14:09.
 * @version v0.1
 * @since   v1.0
 */
public class ToolbarActivity extends BaseActivity {

    protected Toolbar toolbar;

    @Override
    protected void onPostCreateView() {
        super.onPostCreateView();
        setUpToolbar();
    }

    protected void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            initToolbar(toolbar);
        }
    }
}
