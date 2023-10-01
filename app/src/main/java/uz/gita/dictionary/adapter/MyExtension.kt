package uz.gita.dictionary.adapter

import androidx.fragment.app.Fragment
import uz.gita.dictionary.R

fun Fragment.finish() {
    parentFragmentManager.popBackStack()
}

fun Fragment.startFragment(fragment: Fragment) {
    parentFragmentManager.beginTransaction()
        .addToBackStack(fragment::class.java.name)
        .replace(R.id.fragment_holder, fragment)
        .commit()
}