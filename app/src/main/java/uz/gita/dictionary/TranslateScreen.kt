package uz.gita.dictionary


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import uz.gita.dictionary.adapter.Adapter
import uz.gita.dictionary.adapter.startFragment
import uz.gita.dictionary.database.DictionaryDataBase
import uz.gita.dictionary.databinding.TranslateScreenBinding

class TranslateScreen : Fragment(R.layout.translate_screen) {

    private lateinit var binding: TranslateScreenBinding
    private lateinit var adapter: Adapter
    private lateinit var contex: Context
    private lateinit var handle: Handler
    private lateinit var db: DictionaryDataBase
    private var searchText = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = TranslateScreenBinding.inflate(inflater, container, false)
        if (container != null) {
            contex = container.context
        }
        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.mainbg)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = Adapter()
        db = DictionaryDataBase(contex)

        search()
        initAdapter()
        onClik()

        binding.info.setOnClickListener {
            startFragment(AppInfoScreen())
        }
    }


    private fun search() {
        handle = Handler(Looper.getMainLooper())
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(message: String?): Boolean {
                handle.removeCallbacksAndMessages(null)
                searchText = message.toString()
                message?.let {
                    if (message.isEmpty()) {
                        binding.resacilview.visibility = View.GONE
                    } else {
                        val searchResults = db.getWordsByEnglish(it.trim())
                        if (searchResults.count > 0) {
                            binding.resacilview.visibility = View.VISIBLE
                            adapter.setCursor(searchResults)
                            adapter.setQuery(it.trim())
                            adapter.notifyDataSetChanged()
                        } else {
                            binding.resacilview.visibility = View.GONE
                        }
                        adapter.setCursor((db.getWordsByEnglish(it.trim())))
                        adapter.setQuery(it.trim())
                        adapter.notifyDataSetChanged()
                    }
                }
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(message: String?): Boolean {
                searchText = message.toString()
                message?.let {
                    if (message.isEmpty()) {
                        binding.resacilview.visibility = View.GONE
                    } else {
                        val searchResults = db.getWordsByEnglish(it.trim())
                        if (searchResults.count > 0) {
                            binding.resacilview.visibility = View.VISIBLE
                            adapter.setCursor(searchResults)
                            adapter.setQuery(it.trim())
                            adapter.notifyDataSetChanged()
                        } else {
                            binding.resacilview.visibility = View.GONE
                        }
                        adapter.setCursor((db.getWordsByEnglish(it.trim())))
                        adapter.setQuery(it.trim())
                        adapter.notifyDataSetChanged()
                    }
                }
                return true
            }
        })
    }

    private fun initAdapter() {
        binding.resacilview.visibility = View.INVISIBLE
        binding.resacilview.adapter = adapter
        adapter.setCursor(db.getAllWords())
        adapter.setOnFavClickListener { id, value, position ->
            db.updateWords(id, value)
            adapter.setCursor(db.getWordsByEnglish(searchText))
            adapter.notifyItemChanged(position)
        }
    }

    private fun onClik() {
        binding.resacilview.visibility = View.GONE
        adapter.setInfoPosition { positoin ->
            val bundle = Bundle()

            val fragment = InfoScreen()
            fragment.arguments = bundle
            bundle.putSerializable("uzbek", positoin)

            startFragment(fragment)
        }
    }

}