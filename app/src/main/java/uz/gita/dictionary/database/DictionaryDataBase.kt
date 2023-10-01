package uz.gita.dictionary.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import uz.gita.dictionary.local.DBHelper
import uz.gita.dictionary.local.DictionaryModel

class DictionaryDataBase(contex: Context) : DBHelper(contex, "dictionary.db", 1) {

    private val tabelName = "dictionary"

    fun getAllWords(): Cursor {
        return this.database().rawQuery("Select *from dictionary", null)
    }

    fun updateWords(id: Int, value: Int) {
        val cv = ContentValues()
        cv.put("is_favourite", value)
        this.database().update(tabelName, cv, "$tabelName.id==$id", null)
    }

    fun getAllfavorite(): Cursor {
        return this.database().rawQuery("select *from $tabelName  where is_favourite == 1", null)
    }

    fun getWordsByEnglish(message: String): Cursor {
        return this.database()
            .rawQuery("select *from $tabelName where english like \"$message%\"", null)
    }

    fun getWordsByUzbek(message: String): Cursor {
        return this.database()
            .rawQuery("select *from $tabelName where uzbek like \"$message%\"", null)
    }

    @SuppressLint("Range")
    fun getWordsByPosition(position: Int, cursor: Cursor): DictionaryModel {
        val cursor = cursor ?: getAllWords()
        cursor.moveToPosition(position)
        val dictionaryModel = DictionaryModel(
            cursor.getInt(cursor.getColumnIndex("id")),
            cursor.getString(cursor.getColumnIndex("english")),
            cursor.getString(cursor.getColumnIndex("type")),
            cursor.getString(cursor.getColumnIndex("transcript")),
            cursor.getString(cursor.getColumnIndex("uzbek")),
            cursor.getString(cursor.getColumnIndex("counteble")),
            cursor.getInt(cursor.getColumnIndex("is_favorite")),
        )
        return dictionaryModel
    }


}