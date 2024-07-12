package hr.algebra.schengenexplorer.Model

data class Item(
    var _id: Long?,
    val commonName: String,
    val officialName: String,
    val flagDescription: String?,
    val picturePath: String,
    var read: Boolean
)
