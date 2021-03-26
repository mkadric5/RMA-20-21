package ba.unsa.etf.rma

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    /**
     *Klasa za pružanje referenci na sve elemente view-a
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        init {
            // Definisanje akcija na elemente
            textView = view.findViewById(R.id.textView3)
        }
    }
    // Kreiraj novi view
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Kreiraj novi view koji definiše UI elementa liste
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)
        return ViewHolder(view)
    }
    // Izmijeni sadržaj view-a
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //Pokupi element iz skupa podataka i zamijeni
        //sadržaj View sa odgovarajućim
        viewHolder.textView.text = dataSet[position]
    }
    // Vrati veličinu skupa
    override fun getItemCount() = dataSet.size
}

class MyAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val elements: ArrayList<String>):
        ArrayAdapter<String>(context, layoutResource, elements) {
    override fun getView(position: Int, newView: View?, parent: ViewGroup): View {
        var newView = newView
        newView = LayoutInflater.from(context).inflate(R.layout.list_item, parent,
            false)
        val textView = newView.findViewById<TextView>(R.id.textView3)
        val element = elements.get(position)
        textView.text=element
        return newView
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var btnApply: Button
    private lateinit var unosFld: EditText
    private lateinit var recycleView: RecyclerView
    private val listaVrijednosti = arrayListOf<String>();
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnApply = findViewById(R.id.apply)
        unosFld = findViewById(R.id.unos)
        recycleView = findViewById(R.id.listView1)
        recycleView.setLayoutManager(LinearLayoutManager(this))

        napuniListu()

        adapter = CustomAdapter(listaVrijednosti)
        recycleView.adapter = adapter

        btnApply.setOnClickListener {
            insertInList()
        }
    }

    private fun napuniListu() {
        for (i in 1..20)
        listaVrijednosti.add("jabuka")
    }

    private fun insertInList() {
        listaVrijednosti.add(0, unosFld.text.toString())
        adapter.notifyDataSetChanged()
        unosFld.setText("")
    }
}

