package com.websarva.wings.android.whatsyourmealtoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //レシピサイトのURL
    private static final String RECIPESITE_URL = "https://cookpad.com/";

    //BGM再生の準備
    MediaPlayer p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //次の画面遷移ボタンであるtitleClickボタンオブジェクトを取得
        Button titleClick = findViewById(R.id.titleClick);
        titleClickListener listener = new titleClickListener();
        titleClick.setOnClickListener(listener);

        //メニュー追加ボタンであるaddmenuボタンオブジェクトを取得
        Button titleAddMenu = findViewById(R.id.titleAddMenu);
        titleAddMenu.setOnClickListener(listener);

        //レシピサイトへ移動するボタンであるRecipeSiteボタンオブジェクトを取得
        Button titleRecipeSite = findViewById(R.id.titleRecipeSite);
        titleRecipeSite.setOnClickListener(listener);

        //BGMの読み込み
        p = MediaPlayer.create(getApplicationContext(),R.raw.pugtoosanpo);
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



    //ボタンをタッしたときのリスナクラス
    private class titleClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            //タップされた画面部品のidの値を取得
            int id = view.getId();
            //IdのR値に応じて処理を分岐
            switch (id) {
                //Clickボタンの場合
                case R.id.titleClick:
                    //次の画面へ遷移
                    //インテントオブジェクトを生成
                    Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
                    //選択画面の起動
                    startActivity(intent);
                    break;

                //addMenuボタンの場合
                case R.id.titleAddMenu:
                    //メニュー追加画面へ遷移
                    //Intent intentadd = new Intent(getApplication(), AddMenuActivity.class);
                    //startActivity(intentadd);
                    break;

                //RecipeSiteボタンの場合
                case R.id.titleRecipeSite:
                    //レシピサイトを表示画面へ遷移
                    Intent intentResipe = new Intent(MainActivity.this, ResipeSiteActivity.class);
                    startActivity(intentResipe);
                    break;
            }
        }
    }
}
