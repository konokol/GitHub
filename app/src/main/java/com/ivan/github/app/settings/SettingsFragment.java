package com.ivan.github.app.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ivan.github.GitHub;
import com.ivan.github.R;
import com.ivan.github.app.BaseFragment;
import com.ivan.github.app.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Ivan
 */
public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private Button mBtnLogout;
    private TextView mTvSwitchAccount;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mBtnLogout = rootView.findViewById(R.id.btn_logout);
        mBtnLogout.setOnClickListener(this);
        mTvSwitchAccount = rootView.findViewById(R.id.tv_switch_account);
        if (GitHub.appComponent().userCenter().isLogin()) {
            mBtnLogout.setVisibility(View.VISIBLE);
            mTvSwitchAccount.setVisibility(View.VISIBLE);
        } else {
            mBtnLogout.setVisibility(View.GONE);
            mTvSwitchAccount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_logout) {
            doLogout();
        }
    }

    private void doLogout() {
        if (getActivity() == null) {
            return;
        }
        GitHub.appComponent().userCenter().logout();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
