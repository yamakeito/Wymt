package com.websarva.wings.android.whatsyourmealtoday;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

public class DatabaseHelper extends SQLiteOpenHelper {
    //データベースファイル名の定数フィールド
    private static final String DATABASE_NAME = "menuMaterial.db";
    //バージョン情報の定数フィールド
    private static final int DATABASE_VERSION = 1;

    //コンストラクタ
    public DatabaseHelper(Context context){
        //親クラスのコンストラクタの呼び出し
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //テーブル作成用SQL文字列の作成
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE menuMaterial (");
        sb.append("_id INTEGER PRIMARY KEY,");
        sb.append("menuName TEXT,");
        sb.append("menuMaterial TEXT");
        sb.append(");");
        String sql = sb.toString();

        //SQLの実行
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
