package de.rhuber.homedash;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkCookieManager;

public class BrowserActivityLegacy extends BrowserActivity {

    private XWalkView xWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_browser_legacy);
        xWebView=(XWalkView)findViewById(R.id.activity_browser_webview);

        xWebView.setResourceClient(new XWalkResourceClient(xWebView) {

            Snackbar snackbar;
            {
                snackbar = Snackbar.make(xWebView, "", Snackbar.LENGTH_INDEFINITE);
            }

            @Override
            public void onProgressChanged(XWalkView view, int progressInPercent) {
                if (!displayProgress) return;

                if (progressInPercent == 100)
                    snackbar.dismiss();
                else
                {
                    String text = "Loading " + progressInPercent + "% " + view.getUrl();
                    snackbar.setText(text);
                    snackbar.show();
                }
            }

        });

        XWalkSettings webSettings = xWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        Log.i(TAG, webSettings.getUserAgentString());

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadUrl(final String url) {
        xWebView.loadUrl(url);
    }

    @Override
    protected void evaluateJavascript(final String js) {
        xWebView.evaluateJavascript(js, null);
    }

    @Override
    protected void clearCache() {
        xWebView.clearCache(true);
        XWalkCookieManager manager = new XWalkCookieManager();
        manager.removeAllCookie();
    }

    @Override
    protected void reload() {
        xWebView.reload(XWalkView.RELOAD_NORMAL);
    }

}
