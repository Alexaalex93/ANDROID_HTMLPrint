package com.example.cice.htmlprint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    /**
     * Print Framework
     * - imprimir en Papel
     * - Imprimir a PDF
     * - Imprimir a Google Drive
     * Google Cloud Print & Google Drive
     * Print Services Plugin
     */

    public WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Button button = (Button) findViewById(R.id.button);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Imprimir HTM
                final WebView webView = new WebView(MainActivity.this);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return false;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        createWebPrintJob(webView);
                        myWebView = null;
                    }

                });

                String htmlDocument = "<html><body><h1>Android Prueba Impresion </h1> <p>"
                        + "Contenido de prueba </p> </body></html>";
                webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintHelper imagePrinter = new PrintHelper(MainActivity.this);
                //SCALE_MODE_FILL
                //SCALE_MODE_FIT
                imagePrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);

                //COLOR_MODE_COLOR
                //COLOR_MODE_MONOCHROME
                imagePrinter.setColorMode(PrintHelper.COLOR_MODE_COLOR);

                Bitmap bitmap = BitmapFactory.decodeResource(getResources()
                        , R.drawable.ic_action_name);
                imagePrinter.printBitmap("Mi impresion", bitmap);

            }
        });
        final WebView myWebView2 = (WebView) findViewById(R.id.webView);
        myWebView2.loadUrl("http://www.marca.com/");
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                createWebPrintJob(myWebView2);
            }
        });

    }

    private void createWebPrintJob(WebView webView) {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printDocumentAdapter = webView.createPrintDocumentAdapter("Mi Documento");
        String jobName = "Prueba de Impresion";
        printManager.print(jobName, printDocumentAdapter, new PrintAttributes.Builder().build());
    }
}
