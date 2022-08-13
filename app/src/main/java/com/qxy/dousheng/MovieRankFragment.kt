package com.qxy.dousheng

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider

class MovieRankFragment : Fragment() {
    private lateinit var itemDatabase: ItemDatabase
    private lateinit var itemDao: ItemDao
    private lateinit var viewModel: MovieRankViewModel
    private lateinit var allItemLive: LiveData<List<Item>>

    companion object : AsyncTask<Item, Int, Boolean>() {
        private lateinit var itemDao: ItemDao

        fun newInstance() = MovieRankFragment()

        @JvmStatic
        fun insertItem(itemDao: ItemDao) {
            this.itemDao = itemDao
        }

        // 后台异步更新数据
        override fun doInBackground(vararg item: Item): Boolean {
            itemDao.insertItem(*item)
            return true
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            itemDatabase = ItemDatabase.getDatabase(requireActivity())
            itemDao = itemDatabase.getItemDao()
            allItemLive = itemDao.allItemLive()
            viewModel = ViewModelProvider(this).get(MovieRankViewModel::class.java)
            val buttonInsert: Button = requireActivity().findViewById(R.id.buttonInsert)
            val buttonClear: Button = requireActivity().findViewById(R.id.buttonClear)
            val textView: TextView = requireActivity().findViewById(R.id.textView)

            allItemLive.observe(requireActivity()) {
                var s: String = ""
                for (i: Item in it) {
                    s += i.id + ": " + i.name + "=" + i.hot + '\n'
                }
                textView.text = s
            }

            buttonInsert.setOnClickListener {
                val item1 = Item("1", "1", "1", "1", "1")
                val item2 = Item("2", "2", "2", "2", "2")
                val item3 = Item("3", "3", "3", "3", "3")
                itemDao.insertItem(item1, item2, item3)
            }

            buttonClear.setOnClickListener {
                itemDao.clearItem()
            }
        }

        // TODO: Use the ViewModel
    }

}