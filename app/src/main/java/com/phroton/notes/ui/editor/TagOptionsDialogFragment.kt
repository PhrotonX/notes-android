package com.phroton.notes.ui.editor

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phroton.notes.R
import com.phroton.notes.Tag
import com.phroton.notes.TagViewModel
import com.phroton.notes.data.taggednote.TaggedNote
import com.phroton.notes.data.taggednote.TaggedNoteViewModel
import com.phroton.notes.ui.tags.TagViewHolder

class TagOptionsDialogFragment : DialogFragment() {
    private lateinit var mAdapter : TagOptionsAdapter;

    private lateinit var mTagViewModel: TagViewModel;
    private lateinit var mTaggedNoteViewModel: TaggedNoteViewModel;

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate the recycler view.
        val inflater = layoutInflater;
        val view = inflater.inflate(R.layout.options_tag_list, null);
        val recyclerView = view.findViewById<RecyclerView>(R.id.options_tag_list);
        recyclerView.layoutManager = LinearLayoutManager(context);

        // Obtain the view models.
        mTagViewModel = ViewModelProvider(this).get(TagViewModel::class.java);
        mTaggedNoteViewModel = ViewModelProvider(this).get(TaggedNoteViewModel::class.java);

        mTagViewModel.tags.observe(this, Observer<List<Tag>>{ tags ->

            // Set the adapter.
            mAdapter = TagOptionsAdapter(tags, null);
            recyclerView.setAdapter(mAdapter)

        });

        // Set the dialog box.
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.tags)
            .setView(view)
            .setPositiveButton(R.string.ok, object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    TODO("Not yet implemented")
                }
            })
            .setNegativeButton(R.string.cancel, object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    //do nothing.
                }
            })
            .create()
    }
}