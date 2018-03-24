package com.mindvalley.mindvalleyexercise.modules.web;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.mindvalley.mindvalleyexercise.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Fragment is used to show the Reset Password Screen to the User.
 * <p>
 * User enters valid Email ID on UI so that the reset password link is shared with the user.
 *
 * @author AjinkyaD
 * @version 1.0
 */
public class WebViewFragment extends Fragment {
    @BindView(R.id.xwbVwDisplay)
    WebView wbVwDisplay;
    @BindView(R.id.xrellayMainParent)
    RelativeLayout rellayMainParent;

    String webURL = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, vg, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            webURL = (String) bundle.getSerializable("data");
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initObjects();
    }

    /**
     * This function is used to initialise the objects that are going to be used in this fragment
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void initObjects() {
        wbVwDisplay.loadUrl(webURL);

        // Enable Javascript
        WebSettings webSettings = wbVwDisplay.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        wbVwDisplay.setWebViewClient(new WebViewClient());
    }


}
