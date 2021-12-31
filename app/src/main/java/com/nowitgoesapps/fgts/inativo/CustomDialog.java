package com.nowitgoesapps.fgts.inativo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elvis on 27/12/16.
 */

public class CustomDialog extends Dialog {

    private static final String TAG = CustomDialog.class.getSimpleName();

    private Context context;
    //private int position;
    private List<AdapterRowView> adapterRowViews;

    public CustomDialog(@NonNull Context pContext, int pBtnId) {
        super(pContext);

        context = pContext;

        buildView(pBtnId);
    }

    private void buildView(int pBtnId) {
        switch (pBtnId) {
            case R.id.button_above:
                showAboveDialog();
                break;
            case R.id.button_calendar:
                showCalendarDialog();
                break;
            case R.id.button_more:
                showMoreApps();
                break;
            default:
                break;
        }

    }

    private void showMoreApps() {
        setContentView(R.layout.dialog_more_apps);

        Integer[] thumbIntegers = {
                R.drawable.beplus, R.drawable.salmos, R.drawable.biblia, R.drawable.listadecompras,
                R.drawable.finances, R.drawable.embaixadinhas, R.drawable.gogojohnny,
                R.drawable.flightdrone, R.drawable.hexa, R.drawable.wp_polygon
        };

        String[] appsNames = {
                "Be+ Selfie", "Orações & Salmos", "Mensagens Sagradas", "Lista de Compras - Planilha",
                "Financeirando", "Copa das Embaixadinhas", "Go! Go! Johnny", "Flight Drone",
                "Copa Rumo ao Hexa", "Wallpapers Polygon"
        };

        String[] packages = {
                "com.kbdrops.beplus", "com.lohanamahala.oracoes.salmos.two", "com.elvis.biblia.msg",
                "com.elvis.shopping.list", "com.elvis.financeirando",
                "com.elvis.copadasembaixadinhas", "com.elvis.gogojohnny",
                "com.elvis.flightdrone", "com.elvis.coparumoaohexa.br",
                "com.elvis.wallpaper.polygon"
        };

        adapterRowViews = new ArrayList<>();
        for(int i = 0; i < thumbIntegers.length; i++) {
            AdapterRowView adapterRowView = new AdapterRowView(thumbIntegers[i], appsNames[i], packages[i]);
            adapterRowViews.add(adapterRowView);
        }

        //position = 0;

        //list view
        ListView listView = (ListView) findViewById(R.id.gridView1);
        listView.setAdapter(new ArrayAdapterMore(context, adapterRowViews));//wallpaperThumbIntegers, wallpaperNames));//new ImageAdapter(this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                AdapterRowView adapterRowView = adapterRowViews.get(position);
                IntentUtils.intentGooglePlay(context, adapterRowView.getPackageName());
            }
        });

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void showCalendarDialog() {
        setContentView(R.layout.dialog_about);

        ((TextView) findViewById(R.id.dialog_title)).setText(context.getString(R.string.calendar_title));

        String message = "\nFique atento nas datas de saque do seu FGTS inativo:\n\n" +
                "\n\n De 10 de março a 9 de abril: pode sacar quem nasceu em janeiro e fevereiro;\n" +
                "\n" +
                "De 10 de abril a 11 de maio: pode sacar quem nasceu em março, abril e maio;\n" +
                "\n" +
                "De 12 de maio a 15 de junho: pode sacar quem nasceu em junho, julho e agosto;\n" +
                "\n" +
                "De 16 de junho a 13 de julho: pode sacar quem nasceu em setembro, outubro e novembro;\n" +
                "\n" +
                "De 14 a 31 de julho: pode sacar quem nasceu em dezembro.\n" +
                "\n" +
                "Até 31 de agosto será depositado sua parte do lucro do FGTS de 2016.\n" +
                "\n" +
                "\n" +
                "Aproveite e conheça abaixo nosso aplicativo FGTS Corrigido que mostra todas as diferenças que você poderá receber " +
                "após o julgamento da ADI 5090 que se encontra no STF.\n\n";

        TextView text = (TextView) findViewById(R.id.about_text);
        text.setText(message);

        ImageButton imageButton = new ImageButton(context);
        //imageButton.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageButton.setImageDrawable(context.getDrawable(R.drawable.ic_fgts_corrigido));
        } else {
            imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fgts_corrigido));
        }
        ((TableRow) findViewById(R.id.extras_container)).addView(imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.intentGooglePlay(context, "com.elvis.fgtscorrigido.app");
            }
        });
/*
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(MainActivity.interstitialControl == 0) MainActivity.showInterstitialAd();
            }
        });
*/
        findViewById(R.id.btn_oK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void showAboveDialog() {
        setContentView(R.layout.dialog_about);

        ((TextView) findViewById(R.id.dialog_title)).setText(context.getString(R.string.about_title));

        String message = "\nAplicativo desenvolvido apenas para informações sobre o FGTS inativo com a finalidade de informar ao trabalhador " +
                "brasileiro sobre as principais notícias referente autorização do saque do FGTS inativo divulgado pelo Governo Federal no dia 22 de dezembro de 2016.\n";
        message += "\nEnquanto aguardamos a divulgação do cronograma de saque que será divulgado pela Caixa Econômica Federal em fevereiro de 2017 conforme noticiado através da imprensa. " +
                "isso é um direito de todo cidadão brasileiro, afinal o Brasil é um país democrático que permite cada um de nós cobrar os direitos " +
                "conquistados ao longo de nossa história.\n";
        message += "\nEsse aplicativo não tem nenhum vínculo com a Caixa Econômica Federal, nem partido político ou orgão governamental ou não governamental, " +
                "assim a finalidade é meramente informativa assegurada pela Constituição Federal Brasileira artigo 5º inciso IX onde se diz que " +
                "é livre a expressão da atividade intelectual, artística, científica e de comunicação, independentemente de censura ou licença.\n";
        message += "\n\n Versão do aplicativo " + context.getString(R.string.app_version) +"\n";
        message += "\nNow It Goes Apps\n";
        message += "\ne-mail: now.it.goes.apps@gmail.com\n";
        message += "\n\nObrigado por adquirir o aplicativo.\n\n";

        TextView text = (TextView) findViewById(R.id.about_text);
        text.setText(message);

        findViewById(R.id.btn_oK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
