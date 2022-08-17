package com.qxy.dousheng.ui.ranking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qxy.dousheng.*
import com.qxy.dousheng.adapter.RankAdapter
import com.qxy.dousheng.model.RankItem
import com.qxy.dousheng.network.OkHttpCallback
import com.qxy.dousheng.network.RankOkHttpUtils


class ArtRankFragment : Fragment() {

    private lateinit var viewModel: ArtRankViewModel

    companion object {
        fun newInstance() = ArtRankFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_art_rank, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[ArtRankViewModel::class.java]

            val list: List<RankItem> = if (viewModel.getLiveData().value != null) {
                viewModel.getLiveData().value!!
            } else listOf()
            val adapter = RankAdapter(list)
            val recyclerView: RecyclerView = requireView().findViewById(R.id.art_recycler)

            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.adapter = adapter

            viewModel.getLiveData().observe(requireActivity()) {
                adapter.rankList = it
                adapter.notifyDataSetChanged()
            }


            RankOkHttpUtils.doArtGet(object : OkHttpCallback {
                override fun isFail() {
                    Log.d("okHttp", "Callback 出错")
                    Toast.makeText(requireActivity(), "OkHttp出错", Toast.LENGTH_SHORT).show()
                }

                override fun isSuccess(json: String?) {
                    if (json != null) {
                        Log.d("okHttp", "Callback: $json")
                        viewModel.update(json)
                    } else {
                        Toast.makeText(requireActivity(), "Response 为空", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            })
//                for (i in 1..30) {
//                    val rankItem = RankItem(i.toString(), i.toString(), i.toString(), i, 1)
//                    viewModel.insertItem(rankItem)
//                }
        }
    }

}