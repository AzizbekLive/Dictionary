package uz.gita.dictionary

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import uz.gita.dictionary.databinding.InfoActivityBinding
import uz.gita.dictionary.local.DictionaryModel
import java.util.Locale

@Suppress("DEPRECATION")
class InfoScreen : Fragment(R.layout.info_activity), TextToSpeech.OnInitListener {

    private lateinit var binding: InfoActivityBinding
    private var tts: TextToSpeech? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = InfoActivityBinding.inflate(inflater, container, false)
        tts = TextToSpeech(context, this)

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.mainbg)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val age = this.arguments
        val data = age?.getSerializable("uzbek") as DictionaryModel
//        Log.d("TTT", data.english)
        binding.textView2.text = data.uzbek
        binding.textView3.text = data.type
        binding.textView4.text = data.transcript
        binding.textView5.text = data.counteble
        binding.englishWord.text = data.english

//        binding.imageView2.setOnClickListener {
//            startFragment(FragmentFirst())
//        }
        binding.imageView3.setOnClickListener {
            speakOut()
        }

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            } else {
                binding.imageView3.isEnabled = true
            }
        }
    }

    private fun speakOut() {
        val text = binding.englishWord.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}