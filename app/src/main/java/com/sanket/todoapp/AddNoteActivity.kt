package com.sanket.todoapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "com.sanket.todoapp.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.sanket.todoapp.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.sanket.todoapp.EXTRA_PRIORITY"

        fun newIntent(context: Context) = Intent(context, AddNoteActivity::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.actionSave -> {
                saveNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val title = etTitle.text.toString()
        val description = etDescription.text.toString()
        val priority = npPriority.value

        if (title.isBlank()) {
            Toast.makeText(this, "Title cannot be blank", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, description)
        data.putExtra(EXTRA_PRIORITY, priority)

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun init() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add Note"

        npPriority.minValue = 1
        npPriority.maxValue = 10
    }

}
