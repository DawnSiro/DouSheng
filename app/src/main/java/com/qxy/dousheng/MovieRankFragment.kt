package com.qxy.dousheng

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class MovieRankFragment : Fragment() {
    private lateinit var itemDatabase: ItemDatabase
    private lateinit var itemDao: ItemDao
    private lateinit var viewModel: MovieRankViewModel

    companion object {
        fun newInstance() = MovieRankFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        itemDatabase = Room.databaseBuilder(
            requireActivity(),
            ItemDatabase::class.java,
            "movie_database"
        ).build()
        itemDao = itemDatabase.getItemDao()
        viewModel = ViewModelProvider(this).get(MovieRankViewModel::class.java)
        val buttonInsert: Button = requireActivity().findViewById(R.id.buttonInsert)
        val buttonClear: Button = requireActivity().findViewById(R.id.buttonClear)
        val textView: TextView = requireActivity().findViewById(R.id.textView)

        updateView(textView)

        buttonInsert.setOnClickListener {
            val item1 = Item("1", "1", "1", "1", "1")
            val item2 = Item("2", "2", "2", "2", "2")
            val item3 = Item("3", "3", "3", "3", "3")
            itemDao.insertItem(item1, item2, item3)
            updateView(textView)
        }
        buttonClear.setOnClickListener {
            itemDao.clearItem()
        }


        // TODO: Use the ViewModel
    }

    private fun updateView(textView: TextView) {
        val list: List<Item> = itemDao.allItem()
        var s: String = ""
        for (i: Item in list) {
            s += i.id + ": " + i.name + "=" + i.hot + '\n'
        }
        textView.text = s
    }
}