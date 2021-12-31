package com.nowitgoesapps.fgts.inativo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableRow;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends AppCompatActivity implements AdvancedWebView.Listener {
    private static final String TAG = "MainActivity";

    private AdView mAdView;
    private AdvancedWebView mWebView;
//    public static int interstitialControl = 0;
//    private static InterstitialAd interstitialAd;
    private View progressBar;
    private boolean appOpened;
    private boolean interstitialShowed;
    private final String SITE_OPEN = "open";
    private final String SITE_CLOSE = "close";

    private String siteCaixaStatus = SITE_CLOSE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAds();
        initViews();
    }

    private void loadAds() {
        MobileAds.initialize(MainActivity.this, "ca-app-pub-4768510961285493~2365395168");

        AdRequest adRequest = new AdRequest.Builder().build();

        final TableRow tabRowAdView = (TableRow) findViewById(R.id.adview_container_tr);
        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mAdView.getParent() == null) tabRowAdView.addView(mAdView);
            }
        });

//        interstitialAd = new InterstitialAd(MainActivity.this);
//        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_id));
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.button_above).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(MainActivity.this, R.id.button_above);
                customDialog.show();
            }
        });

        findViewById(R.id.button_calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(MainActivity.this, R.id.button_calendar);
                customDialog.show();
            }
        });

        findViewById(R.id.button_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(MainActivity.this, R.id.button_more);
                customDialog.show();
            }
        });

        findViewById(R.id.button_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "\n\nBaixe você também o app " + getString(R.string.app_name) +
                        " e fique sempre antenado nas principais novidades sobre o seu FGTS inativo.\n " +
                        "Grátis na Google Play Store.\n Compartilhe com seus amigos:\n\n " +
                        "https://play.google.com/store/apps/details?id=" + getPackageName();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("play.google.com/apps/publish/?dev_acc=04250371099193251885#AppListPlacetext/plain");

                startActivity(sendIntent);
            }
        });
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mWebView = (AdvancedWebView) findViewById(R.id.web_view);
        mWebView.setListener(MainActivity.this, this);
        mWebView.loadUrl("http://fgtsinativo.blogspot.com.br");
        //mWebView.loadUrl("https://servicossociais.caixa.gov.br/internet.do?segmento=CIDADAO01&produto=FGTS");
        //String siteUrl = "https://servicossociais.caixa.gov.br/internet.do?segmento=CIDADAO01&produto=FGTS";
        //(new ParseURL()).execute(new String[]{siteUrl});
        
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();

//        interstitialAd.loadAd(adRequest);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        if(mAdView != null) {
            mAdView.resume();
        }
        /*if (interstitialAd != null && !interstitialAd.isLoaded()) {
            requestNewInterstitial();
        }*/
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        if(mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        if(mAdView != null) {
            mAdView.destroy();
        }
        System.exit(0);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onBackPressed() {
        /*if(siteCaixaStatus.equals(SITE_OPEN)) {
            mWebView.onBackPressed();
            siteCaixaStatus = SITE_CLOSE;
        }*/
        if (!mWebView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        if(progressBar.getVisibility() == View.INVISIBLE) {
            mWebView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageFinished(String url) {
        if(progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.INVISIBLE);
 /*           if(interstitialAd != null && interstitialAd.isLoaded() && appOpened) {
                if(!interstitialShowed && interstitialControl != 0 ) {
                    interstitialAd.show();
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            mWebView.setVisibility(View.VISIBLE);
                            interstitialShowed = true;
                            super.onAdClosed();
                        }
                    });
                    return;
                }
                interstitialControl++;
            }*/
            mWebView.setVisibility(View.VISIBLE);
            appOpened = true;
        }
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType,
                                    long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }
/*
    public static void showInterstitialAd() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
            //interstitialControl = 1;
        } else {
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
        }
    }*/
}
