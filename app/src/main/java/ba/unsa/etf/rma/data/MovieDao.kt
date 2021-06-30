package ba.unsa.etf.rma.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie WHERE favourite=1")
    fun getAll(): LiveData<List<Movie>>
    @Insert
    suspend fun insertAll(vararg movies: Movie)
}