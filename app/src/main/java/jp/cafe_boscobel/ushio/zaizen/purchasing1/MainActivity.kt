package jp.cafe_boscobel.ushio.zaizen.purchasing1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MotionEventCompat

const val EXTRA_TASK = "jp.cafe_boscobel.ushio.zaizen.purchasing1.TASK"
public var menuswitch = 0  //メニューの選択により0,1,2を設定

class MainActivity : AppCompatActivity() {
    private lateinit var mRealm: Realm
    private val mRealmListener = object : RealmChangeListener<Realm> {
        override fun onChange(element: Realm) {
            reloadListView()
        }
    }

    private lateinit var mTaskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener { view ->
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        // Realmの設定
        mRealm = Realm.getDefaultInstance()
        mRealm.addChangeListener(mRealmListener)

        // ListViewの設定
        mTaskAdapter = TaskAdapter(this)

        //ListViewをタップしたときの処理
        listView1.setOnItemClickListener { parent, _, position, _ ->
            //入力・編集する画面に遷移させる
            val task = parent.adapter.getItem(position) as Task
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra(EXTRA_TASK, task.id)
            startActivity(intent)
        }

        //ListViewを長押ししたときの処理
        listView1.setOnItemLongClickListener { parent, _, position, _ ->

                // タスクを削除する
                val task = parent.adapter.getItem(position) as Task

                //ダイアログを表示する
                val builder = AlertDialog.Builder(this)

                builder.setTitle("削除")
                builder.setMessage(task.title + "を削除しますか")

                builder.setPositiveButton("OK") { _, _ ->
                    val results = mRealm.where(Task::class.java).equalTo("id", task.id).findAll()

                    mRealm.beginTransaction()
                    results.deleteAllFromRealm()
                    mRealm.commitTransaction()

                    reloadListView()
                }

                builder.setNegativeButton("CANCEL", null)

                val dialog = builder.create()
                dialog.show()

                true

        }
            reloadListView()

    }


        private fun reloadListView() {
        //Realmデータベースから、「すべてのデータを取得して新しい日時順に並べた結果」を取得
//        val taskRealmResults = mRealm.where(Task::class.java).findAll().sort("sort1", Sort.ASCENDING)

         val taskRealmResults1 = mRealm.where(Task::class.java).findAll().sort("sort1", Sort.ASCENDING)
         val taskRealmResults2 = mRealm.where(Task::class.java).greaterThan("amount",0).findAll().sort("sort2",Sort.ASCENDING)
         val taskRealmResults3 = mRealm.where(Task::class.java).findAll().sort("sort2",Sort.ASCENDING)
        //上記の結果をTaskListとしてセットする
        when (menuswitch) {
            0 ->
            mTaskAdapter.mTaskList = mRealm.copyFromRealm(taskRealmResults1)
            1 ->
            mTaskAdapter.mTaskList = mRealm.copyFromRealm(taskRealmResults2)
            2 ->
              mTaskAdapter.mTaskList = mRealm.copyFromRealm(taskRealmResults3)
        }
        //TaskのListView用のアダプタに渡す
        listView1.adapter = mTaskAdapter

        //表示を更新するために、アダプタにデー０田が変更されたことを知らせる
        mTaskAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        var returnVal = true
        when (item.itemId) {
            R.id.sort1_settings -> menuswitch = 0
            R.id.sort2_settings -> menuswitch = 1
            R.id.sort2all_settings -> menuswitch = 2
            R.id.amount_zero -> amountzero()
            else ->
                returnVal = super.onOptionsItemSelected(item)
        }
        reloadListView()
        return returnVal
    }

    fun amountzero () {

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        
        val taskRealmResults0 = mRealm.where(Task::class.java).findAll()
        mTaskAdapter.mTaskList = mRealm.copyFromRealm(taskRealmResults0)

        for (i in 0..mTaskAdapter.mTaskList.size-1) {
            mTaskAdapter.mTaskList[i].amount=0
        }
        realm.copyToRealmOrUpdate(mTaskAdapter.mTaskList)

        realm.commitTransaction()

        realm.close()

    }
}
