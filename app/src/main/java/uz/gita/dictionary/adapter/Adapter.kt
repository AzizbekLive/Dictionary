package uz.gita.dictionary.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dictionary.R
import uz.gita.dictionary.databinding.ItemDictionaryBinding
import uz.gita.dictionary.local.DictionaryModel

class Adapter : RecyclerView.Adapter<Adapter.Holder>() {

    private lateinit var cursor: Cursor

    private var queriy = ""
    private var OnFavClickListener: ((Int, Int, Int) -> Unit)? = null

    private var InfoPosition: ((DictionaryModel) -> Unit)? = null

    fun setInfoPosition(block: (DictionaryModel) -> Unit) {
        InfoPosition = block
    }

    fun setOnFavClickListener(block: (Int, Int, Int) -> Unit) {
        OnFavClickListener = block
    }

    fun setQuery(q: String) {
        queriy = q
    }

    fun setCursor(cursor: Cursor) {
        this.cursor = cursor
    }

    inner class Holder(private val item: ItemDictionaryBinding) : RecyclerView.ViewHolder(item.root) {
        init {
            item.imageView.setOnClickListener {
                val data = getWordsByPosition(adapterPosition)
                if (data.is_favorite == 0) {
                    OnFavClickListener?.invoke(data.id, 1, adapterPosition)
                } else {
                    OnFavClickListener?.invoke(data.id, 0, adapterPosition)
                }
            }

            item.root.setOnClickListener {
                InfoPosition?.invoke(getWordsByPosition(adapterPosition))
            }
        }

        fun bind(data: DictionaryModel) {
            item.apply {
                if (queriy.isEmpty()) {
                    textEnglish.text = data.english
                } else {
                    textEnglish.text = data.english.spannable(queriy, itemView.context)
                }
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

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(getWordsByPosition(position))

    @SuppressLint("Range")
    fun getWordsByPosition(position: Int): DictionaryModel {
        cursor.moveToPosition(position)
        return DictionaryModel(
            cursor.getInt(cursor.getColumnIndex("id")),
            cursor.getString(cursor.getColumnIndex("english")),
            cursor.getString(cursor.getColumnIndex("type")),
            cursor.getString(cursor.getColumnIndex("transcript")),
            cursor.getString(cursor.getColumnIndex("uzbek")),
            cursor.getString(cursor.getColumnIndex("countable")),
            cursor.getInt(cursor.getColumnIndex("is_favourite")),
        )
    }
}