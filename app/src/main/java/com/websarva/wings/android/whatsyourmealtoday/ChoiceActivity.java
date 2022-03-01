package com.websarva.wings.android.whatsyourmealtoday;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ChoiceActivity extends AppCompatActivity {
    //選択されたカクテルの主キーIDを表すフィールド
    private int _menuListId = -1;
    //選択されたカクテル名を表すフィールド
    private String _menuListName = "";
    //データベースヘルパーオブジェクト
    private DatabaseHelper _helper;
    //BGM再生の準備
    MediaPlayer p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        //DBヘルパーオブジェクトを生成
        _helper = new DatabaseHelper(ChoiceActivity.this);

        //メニューリスト用ListView(lvMenu)を取得
        ListView lvMenu = findViewById(R.id.lvMenu);
        //lvMenuにリスナを登録
        lvMenu.setOnItemClickListener(new ListItemClickListener());

        //ランダムボタンオブジェクトを取得
        Button btnRandom = findViewById(R.id.btnRandom);
        btnRandom.setOnClickListener(new randomClickListener());

        //BGMの読み込み
        p = MediaPlayer.create(getApplicationContext(), R.raw.odekakebiyori);
        //BGMを連続で再生
        p.setLooping(true);
    }


    //保存ボタンがタップされたときの処理メソッド
    public void onSaveButtonClick(View view) {
        //必要な材料の情報を取得
        EditText etMaterial = findViewById(R.id.etMaterial);
        String material = etMaterial.getText().toString();

        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        SQLiteDatabase db = _helper.getWritableDatabase();

        db.beginTransaction();
        try {

            //リストで選択されたメニューのメモデータを削除。その後、インサートを実施
            //削除用SQL文字列を用意
            String sqlDelete = "DELETE FROM menuMaterial WHERE _id = ?";
            //SQL文字列を元にプリペアードステートを取得
            SQLiteStatement stmt = db.compileStatement(sqlDelete);
            //変数のバインド
            stmt.bindLong(1, _menuListId);
            //削除SQLの実行
            stmt.executeUpdateDelete();

            //インサート用SQL文字列の用意
            String sqlInsert = "INSERT INTO menuMaterial(_id,menuName,menuMaterial)VALUES(?,?,?)";
            //SQL文字列を元にプリペアードステートを取得
            stmt = db.compileStatement(sqlInsert);
            //変数のバインド
            stmt.bindLong(1, _menuListId);
            stmt.bindString(2, _menuListName);
            stmt.bindString(3, material);
            //インサートSQLの実行
            stmt.executeInsert();

            //トランザクション成功
            db.setTransactionSuccessful();

            //保存されたメニュー名をトーストで表示
            String show = _menuListName + "が保存されました";
            Toast.makeText(ChoiceActivity.this,show,Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //トランザクション終了
            db.endTransaction();
        }

        //材料欄の入力値を消去
        etMaterial.setText("");
        //選択したメニュー名を「未選択」に変更
        TextView tvChoiceMenu = findViewById(R.id.tvChoiceMenu);
        tvChoiceMenu.setText(getString(R.string.tv_name));
        //「保存」ボタンをタップ出来ないように変更
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setEnabled(false);
    }

    //リストがタップされたときの処理が記述されたメンバクラス
    public class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //タップされた行番号をフィールドの主キーIDに代入
                    _menuListId = position;
                    //タップされた行のデータを取得。これがメニュー名となるので、フィールドに代入
                    _menuListName = (String) parent.getItemAtPosition(position);
                    //メニュー名を表示するTextViewに表示メニューを設定
                    TextView tvChoiceMenu = findViewById(R.id.tvChoiceMenu);
                    tvChoiceMenu.setText(_menuListName);
                    //「保存」ボタンをタップ出来るように変更
                    Button btnSave = findViewById(R.id.btnSave);
                    btnSave.setEnabled(true);

                    //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
                    SQLiteDatabase db = _helper.getWritableDatabase();
                    //主キーによる検索SQL文字列の用意
                    String sql = "SELECT * FROM menuMaterial WHERE _id =" + _menuListId;
                    //SQL実行
                    Cursor cursor = db.rawQuery(sql, null);
                    String material = "";
                    //SQL実行の戻り値であるカーソルオブジェクトをループさせてデータベース内のデータを取得
                    while (cursor.moveToNext()) {
                        //カラムのインデックス値を取得
                        int idxMate = cursor.getColumnIndex("menuMaterial");
                        //カラムのインデックス値を元に実際のデータを取得
                        material = cursor.getString(idxMate);
                    }
                    //必要な材料のEditTextの各画面部品を取得し、データベースの値を反映
                    EditText etMaterial = findViewById(R.id.etMaterial);
                    etMaterial.setText(material);
            }
        }
    //[決められない]ボタンが押されたときの処理
    public class randomClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //Randomクラス利用する
            Random random = new Random();
            //nextInt()に配列の大きさを入れる予定
            int randomValue = random.nextInt(23);

            //タップされた行番号をフィールドの主キーIDに代入
            _menuListId = randomValue;

            //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
            SQLiteDatabase dbb = _helper.getWritableDatabase();
            //主キーによる検索SQL文字列の用意
            String sql = "SELECT * FROM menuMaterial WHERE _id =" + _menuListId;
            //SQL実行
            Cursor cursor = dbb.rawQuery(sql, null);
            String material = "";
            //SQL実行の戻り値であるカーソルオブジェクトをループさせてデータベース内のデータを取得
            while (cursor.moveToNext()) {
                //カラムのインデックス値を取得
                int idxMate = cursor.getColumnIndex("menuMaterial");
                //カラムのインデックス値を元に実際のデータを取得
                material = cursor.getString(idxMate);
            }

            //必要な材料のEditTextの各画面部品を取得し、データベースの値を反映
            EditText eetMaterial = findViewById(R.id.etMaterial);
            eetMaterial.setText(material);
        }
    }

    //戻るボタンをタップしたときの処理
    public void onBackButtonClick(View view) {
        finish();
    }

    //画面が表示される度にBGMを再生が実行される
    @Override
    protected void onResume() {
        super.onResume();
        p.start(); //再生
    }

    //画面非常時にBGM停止が実行される
    @Override
    protected void onPause() {
        super.onPause();
        p.pause(); //一時停止
    }

    //アプリ停止時にBGMのメモリを解放し、音楽プレーヤーを破棄する
    @Override
    protected void onDestroy() {
        //DBヘルパーオブジェクトの解放
        _helper.close();
        super.onDestroy();
        p.release(); //メモリの解放
        p = null; //音楽プレーヤーを破棄
    }
}


