package ba.unsa.etf.rma.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.R
import ba.unsa.etf.rma.data.Movie
import com.bumptech.glide.Glide

class MovieListAdapter(
    private var dataSet: List<Movie>,
    private val onItemClicked: (movie: Movie, view1: View, view2: View) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private val posterPath = "https://image.tmdb.org/t/p/w342"
    /**
     *Klasa za pružanje referenci na sve elemente view-a
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleMovie: TextView = view.findViewById(R.id.movieTitle)
        val imageMovie: ImageView = view.findViewById(R.id.movieImage)
    }
    // Kreiraj novi view
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Kreiraj novi view koji definiše UI elementa liste
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_movie, viewGroup, false)
        return ViewHolder(view)
    }
    // Izmijeni sadržaj view-a
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //Pokupi element iz skupa podataka i zamijeni
        //sadržaj View sa odgovarajućim
        viewHolder.titleMovie.text = dataSet[position].title
//        var movieGenre = dataSet[position].genre
//        if (movieGenre == null) movieGenre = "thriller"
        val movieGenre = "thriller"
        val context = viewHolder.imageMovie.context
        val id: Int = context.resources.getIdentifier(movieGenre,"drawable", context.packageName)
        if (id == 0) viewHolder.imageMovie.setImageResource(R.drawable.movie)
        else viewHolder.imageMovie.setImageResource(id)

        Glide.with(context)
            .load(posterPath + dataSet[position].posterPath)
            .centerCrop()
            .placeholder(R.drawable.movie)
            .error(id)
            .fallback(id)
            .into(viewHolder.imageMovie)

        viewHolder.itemView.setOnClickListener{ onItemClicked(dataSet[position],viewHolder.imageMovie,viewHolder.titleMovie )}
    }
    // Vrati veličinu skupa
    override fun getItemCount() = dataSet.size

    fun updateMovies(movies: List<Movie>) {
        dataSet = movies
        notifyDataSetChanged()
    }
}