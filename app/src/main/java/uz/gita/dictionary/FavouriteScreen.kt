package uz.gita.dictionary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import uz.gita.dictionary.adapter.FavoritAdapter
import uz.gita.dictionary.adapter.startFragment
import uz.gita.dictionary.database.DictionaryDataBase
import uz.gita.dictionary.databinding.FavouriteScreenBinding

class FavouriteScreen : Fragment(R.layout.favourite_screen) {

    private lateinit var binding: FavouriteScreenBinding
    private lateinit var adapter: FavoritAdapter
    private lateinit var contex: Context
//    private lateinit var adapter_favorit:Adapter

    private lateinit var db: DictionaryDataBase


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FavouriteScreenBinding.inflate(inflater, container, false)
        if (container != null) {
            contex = container.context
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = FavoritAdapter()
//        adapter_favorit = Adapter()
        db = DictionaryDataBase(contex)

        plasHolder(db.getAllfavorite().count == 0)

        initAdapter()
        onClick()
    }


    private fun initAdapter() {
        binding.recyclerView.adapter = adapter
        adapter.setCursor(db.getAllfavorite())
        adapter.setchangeLisener { id, value, position ->
            db.updateWords(id, value)
            adapter.setCursor(db.getAllfavorite())
            plasHolder(db.getAllfavorite().count == 0)
            adapter.notifyItemChanged(position)
        }
    }

    fun plasHolder(bool: Boolean) {
        binding.animationView.isVisible = bool
        binding.animationView.playAnimation()
    }

    fun onClick() {
        binding.imageView2.setOnClickListener {
            val intent = Intent(contex, MainActivity::class.java)
            startActivity(intent)
        }
        adapter.setInfoPosition { positoin ->
            val bundle = Bundle()
            val fragment = InfoScreen()
            fragment.arguments = bundle
            bundle.putSerializable("uzbek", positoin)
            startFragment(fragment)

        }

    }
}

