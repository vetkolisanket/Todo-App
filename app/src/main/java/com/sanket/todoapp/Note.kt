package com.sanket.todoapp

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by Sanket on 2019-07-12.
 */
@Entity(tableName = "note_table")
class Note(
    val title: String,
    val description: String,
    val priority: Int,
    var isCompleted: Boolean
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore
    constructor(title: String, description: String, priority: Int) : this(
        title,
        description,
        priority,
        false
    )

}
