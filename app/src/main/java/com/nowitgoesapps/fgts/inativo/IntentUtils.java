package com.nowitgoesapps.fgts.inativo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by elvis on 28/12/16.
 */

public class IntentUtils {
    static public void intentGooglePlay(Context pContext, String pPackageName) {
        String url;

        try {
            pContext.getPackageManager().getPackageInfo("com.android.vending", 0);

            url = "market://details?id=" + pPackageName;
        } catch ( final Exception e ) {
            url = "https://play.google.com/store/apps/details?id=" + pPackageName;
        }

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        pContext.startActivity(intent);
    }

    static public void intentBrowser(Context pContext, String pUrl) {
        Uri uri = Uri.parse(pUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        pContext.startActivity(intent);
    }
}
