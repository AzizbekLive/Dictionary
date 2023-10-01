package uz.gita.dictionary.local

import java.io.Serializable

data class DictionaryModel(
    val id:Int,
    val english:String,
    val type:String,
    val transcript:String,
    val uzbek:String,
    val counteble:String,
    val is_favorite:Int
):Serializable