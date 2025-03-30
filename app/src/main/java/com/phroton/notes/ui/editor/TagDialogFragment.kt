package com.phroton.notes.ui.editor

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.compose.material3.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.phroton.notes.R

class TagDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var inflater = layoutInflater.inflate(R.layout.options_tag_list, null);
        var view = inflater.findViewById<RecyclerView>(R.id.options_tag_list);

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
                    TODO("Not yet implemented")
                }
            })
            .create()
    }
}