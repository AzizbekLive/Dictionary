package uz.gita.dictionary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import uz.gita.dictionary.database.DictionaryDataBase
import uz.gita.dictionary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DictionaryDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragment = HomeScreen()

        fragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, fragment)
            .commit()
        db = DictionaryDataBase(this)

        binding.bottomBar.onItemSelected = {

            when (it) {
                0 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_holder, HomeScreen())
                        .commit()
                }

                1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_holder, TranslateScreen())
                        .commit()
                }

                2 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_holder, FavouriteScreen())
                        .commit()
                }

                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_holder, HomeScreen())
                        .commit()
                }
            }
        }
    }
}