package com.websarva.wings.android.whatsyourmealtoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class ResipeSiteActivity extends AppCompatActivity {
    //BGM再生の準備
    MediaPlayer p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resipe_site);

        //インテントオブジェクトを取得
        Intent intent = getIntent();

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://cookpad.com/");

        //BGMの読み込み
        p = MediaPlayer.create(getApplicationContext(),R.raw.ukiukilalala);
        //BGMを連続で再生
        p.setLooping(true);

    }

    //画面が表示される度にBGMを再生が実行される
    @Override
    protected void onResume(){
        super.onResume();
        p.start(); //再生
    }

    //画面非常時にBGM停止が実行される
    @Override
    protected void onPause(){
        super.onPause();
        p.pause(); //一時停止
    }

    //アプリ停止時にBGMのメモリを解放し、音楽プレーヤーを破棄する
    @Override
    protected void onDestroy(){
        super.onDestroy();
        p.release(); //メモリの解放
        p = null; //音楽プレーヤーを破棄
    }

    //戻るボタンをタップしたときの処理
    public void onBackButtonClick(View view){
        finish();
    }


}