package uz.gita.dictionary.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dictionary.R
import uz.gita.dictionary.databinding.ItemDictionaryBinding
import uz.gita.dictionary.local.DictionaryModel

class FavoritAdapter : RecyclerView.Adapter<FavoritAdapter.Holder>() {

    private lateinit var cursor: Cursor
    private var changeLisener: ((Int, Int, Int) -> Unit)? = null

    fun setCursor(cursor: Cursor) {
        this.cursor = cursor
    }

    private var InfoPosition: ((DictionaryModel) -> Unit)? = null

    fun setInfoPosition(block: (DictionaryModel) -> Unit) {
        InfoPosition = block
    }

    fun setchangeLisener(block: ((Int, Int, Int) -> Unit)) {
        changeLisener = block
    }

    inner class Holder(private val binding: ItemDictionaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageView.setOnClickListener {
                val data = getWordsByPosition(adapterPosition)
                if (data.is_favorite == 0) {
                    changeLisener?.invoke(data.id, 1, adapterPosition)
                } else {
                    changeLisener?.invoke(data.id, 0, adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
            binding.root.setOnClickListener {
                InfoPosition?.invoke(getWordsByPosition(adapterPosition))
            }
        }

        fun bind(data: DictionaryModel) {
            binding.apply {
                textEnglish.text = data.english
                typ.text = data.type
                if (data.is_favorite == 0) {
                    imageView.setImageResource(R.drawable.baseline_favorite_border_24w)
                } else {
                    imageView.setImageResource(R.drawable.baseline_favorite_24w)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemDictionaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getWordsByPosition(position))
    }

    @SuppressLint("Range")
    fun getWordsByPosition(postion: Int): DictionaryModel {
        cursor.moveToPosition(postion)
        return DictionaryModel(
            cursor.getInt(cursor.getColumnIndex("id")),
            cursor.getString(cursor.getColumnIndex("english")),
            cursor.getString(cursor.getColumnIndex("type")),
            cursor.getString(cursor.getColumnIndex("transcript")),
            cursor.getString(cursor.getColumnIndex("uzbek")),
            cursor.getString(cursor.getColumnIndex("countable")),
            cursor.getInt(cursor.getColumnIndex("is_favourite"))
        )
    }
}