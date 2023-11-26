package jp.cafe_boscobel.ushio.zaizen.purchasing1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(context: Context): BaseAdapter() {
    private val mLayoutInflater: LayoutInflater
    var mTaskList = mutableListOf<Task>()

    init {
        this.mLayoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return mTaskList.size
    }

    override fun getItem(position: Int): Any {
        return mTaskList[position]
    }

    override fun getItemId(position: Int): Long {
        return mTaskList[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view : View = convertView ?: mLayoutInflater.inflate(R.layout.activity_taskadapter, null)

        val textView1 = view.findViewById<TextView>(R.id.text1)
        val textView2 = view.findViewById<TextView>(R.id.text2)
        val textView3 = view.findViewById<TextView>(R.id.text3)

        textView1.text = mTaskList[position].title
        textView2.text = mTaskList[position].amount.toString()
        when(menuswitch) {
            0 ->
            textView3.text = mTaskList[position].sort1.toString()
            1 ->
            textView3.text = mTaskList[position].sort2.toString()
            2 ->
            textView3.text = mTaskList[position].sort2.toString()
        }
/*        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPANESE)
        val date = mTaskList[position].date
        textView2.text = simpleDateFormat.format(date)

 */

//        textView3.text = "test3"
        return view
    }
}