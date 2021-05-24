package ba.unsa.etf.rma.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import ba.unsa.etf.rma.R
import ba.unsa.etf.rma.viewmodel.MovieDetailViewModel

class AdditionalInfoFragment(val movieId: Long): Fragment() {
    private lateinit var itemsList: ListView
    private lateinit var itemsListAdapter: ArrayAdapter<String>
    private var movieDetailViewModel = MovieDetailViewModel(null,this@AdditionalInfoFragment::populateInfo)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.additional_info_fragment, container, false)
        itemsList = view.findViewById(R.id.itemsList)
        itemsListAdapter = ArrayAdapter(context!!,android.R.layout.simple_list_item_1,mutableListOf())
        itemsList.adapter = itemsListAdapter

        if (tag == "actors")
            movieDetailViewModel.getMovieActors(movieId)
        else if (tag == "similar movies")
            movieDetailViewModel.getSimilarMovies(movieId)

        return view
    }

    private fun populateInfo(infoList: List<String>) {
        itemsListAdapter.clear()
        itemsListAdapter.addAll(infoList)
        itemsListAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(movieId: Long): AdditionalInfoFragment = AdditionalInfoFragment(movieId)
    }
}