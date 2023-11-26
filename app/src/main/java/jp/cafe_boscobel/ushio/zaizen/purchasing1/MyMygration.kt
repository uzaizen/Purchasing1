package jp.cafe_boscobel.ushio.zaizen.purchasing1

import android.util.Log
import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

class MyMigration: RealmMigration{

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {

        val realmSchema = realm.schema // Realmのスキーマ
        var oldVersion = oldVersion

        Log.d("uztest","oldVersion = ${oldVersion}")

        if (oldVersion == 0L) { // スキーマのバージョンが以前のものであるとき
            Log.d("uztest","schema setting will start")
//            realmSchema.create("Rose") // NewModelを新規作成した
//                .addField("id", Long::class.javaPrimitiveType,FieldAttribute.PRIMARY_KEY) // @PrimaryKeyアノテーション付きのプロパティ
//                .addField("country", String::class.javaPrimitiveType) // nullを許容しない場合、REQUIREDが必要らしい（ソースがない……）
//                .addField("amount", Int::class.javaPrimitiveType)
        realmSchema.get("Task")!! //  ExistModelにプロパティを追加した
//                .addField("id", Long::class.javaObjectType) // class.javaObjectTypeはnullを許容する
                .addField("dimension", String::class.javaObjectType)
            oldVersion++ // マイグレーションしたためスキーマバージョンを上げる
        }
    }
}
