package jp.cafe_boscobel.ushio.zaizen.purchasing1

import java.io.Serializable
import java.util.Date
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Task : RealmObject(), Serializable {
    var title: String = ""      // タイトル
    var contents: String = ""   // 内容
    var date: Date = Date()     // 日時
    var amount : Int = 0
    var sort1 : Int = 0
    var sort2 : Int = 0

    // idをプライマリーキーとして設定
    @PrimaryKey
    var id: Int = 0

   var dimension:String = ""
}