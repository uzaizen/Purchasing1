package jp.cafe_boscobel.ushio.zaizen.purchasing1

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class TaskApp: Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val builder = RealmConfiguration.Builder()
            .schemaVersion(1L)
                .migration(MyMigration())
                .build()

        Realm.setDefaultConfiguration(builder)

    }
}