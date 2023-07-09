package com.sanket.todoapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sanket.todoapp.databinding.ActivityAddNoteBinding

class AddEditNoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "com.sanket.todoapp.EXTRA_ID"
        const val EXTRA_TITLE = "com.sanket.todoapp.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.sanket.todoapp.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.sanket.todoapp.EXTRA_PRIORITY"

        fun newIntent(context: Context) = Intent(context, AddEditNoteActivity::class.java)

        fun newIntent(context: Context, note: Note): Intent {
            val intent = newIntent(context)
            intent.putExtra(EXTRA_ID, note.id)
            intent.putExtra(EXTRA_TITLE, note.title)
            intent.putExtra(EXTRA_DESCRIPTION, note.description)
            intent.putExtra(EXTRA_PRIORITY, note.priority)
            return intent
        }

    }

    private val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSave -> {
                saveNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()
        val priority = binding.npPriority.value

        if (title.isBlank()) {
            Toast.makeText(this, "Title cannot be blank", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, description)
        data.putExtra(EXTRA_PRIORITY, priority)

        if (intent.hasExtra(EXTRA_ID)) {
            data.putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun init() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            binding.apply {
                etTitle.setText(intent.getStringExtra(EXTRA_TITLE))
                etDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
                npPriority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
            }
        } else {
            title = "Add Note"
        }
        binding.apply {
            npPriority.minValue = 1
            npPriority.maxValue = 10
        }
    }

}
