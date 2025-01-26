package com.phroton.notes.ui.tags;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.phroton.notes.R;
import com.phroton.notes.RequestCode;
import com.phroton.notes.Tag;
import com.phroton.notes.TagViewModel;
import com.phroton.notes.ui.FABView;
import com.phroton.notes.ui.editor.EditorActivity;
import com.phroton.notes.ui.taggednote.TaggedNoteFragment;

import java.util.ArrayList;
import java.util.List;

public class TagFragment extends FABView {
    private TagAdapter mTagAdapter;
    private RecyclerView mTagRecyclerView;
    private TagViewModel mTagViewModel;

    public TagFragment() {
        // Required empty public constructor
    }

    public TagViewModel getViewModel(){
        return mTagViewModel;
    }

    public RecyclerView getRecyclerView(){
        return mTagRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView){
        mTagRecyclerView = recyclerView;
    }

    public void setViewModel(AndroidViewModel viewModel){
        mTagViewModel = (TagViewModel) viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mTagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tag_list, container, false);

        //Initialize RecyclerView
        mTagRecyclerView = (RecyclerView) root.findViewById(R.id.tag_list);

        //Set the context menu for RecyclerView.
        registerForContextMenu(mTagRecyclerView);

        mTagRecyclerView.setOnCreateContextMenuListener(this);

        //Obtain the data from the DB.
        LiveData<List<Tag>> tags = getViewModel().getTags();
        if(tags != null){
            tags.observe(getViewLifecycleOwner(), new Observer<List<Tag>>() {
                @Override
                public void onChanged(List<Tag> tags) {
                    //Initialize the RecyclerView.Adapter.
                    initializeRecycleViewAdapter(tags);
                }
            });
        }else{
            //Fill the list with error tags as placeholder value
            //@TODO: Move to TagViewModel. This code is data-related.
            List<Tag> errorTag = new ArrayList<>();
            errorTag.add(new Tag("Error 1"));
            errorTag.add(new Tag("Error 2"));
            errorTag.add(new Tag("Error 3"));

            //Initialize the RecyclerView.Adapter.
            initializeRecycleViewAdapter(errorTag);
        }

        //Set the floating action button.
        setFloatingActionButton(root.findViewById(R.id.fab_add_tag));

        // Inflate the layout for this fragment
        return root;

    }

    /**
     * Set the click action of the floating action button.
     *
     * @return The listener to for FAB.
     * */
    @Override
    protected View.OnClickListener onFabClick() {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showTagEditDialog(null);
            }
        };
    }

    @Override
    protected ActivityResultLauncher<Intent> onFabIntent() {
        return null;
    }

    private void initializeRecycleViewAdapter(List<Tag> tags) {
        //Set the tags into the TagAdapter.
        mTagAdapter = new TagAdapter(getContext(), tags);

        //Set the adapter into the RecyclerView.
        mTagRecyclerView.setAdapter(mTagAdapter);

        mTagAdapter.setOnClickListener(new TagAdapter.OnClickListener() {
            @Override
            public void onClick(Tag tag) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Tag.EXTRA_TAG, tag);

                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
                        .navigate(R.id.action_tagFragment_to_taggedNoteFragment, bundle);
            }
        });

        initializeOnContextItemSelectedListener();

        //Set the layout manager.
        if(mTagAdapter.getItemCount() > 0){
            //Set to GridLayoutManager if the item count is greater than 0.
            /*mTagRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),
                    GridLayoutManager.DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL, false));*/
            mTagRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }else{
            //Set to LinearLayoutManager if the item count is 0 or less.
            //Used to avoid crashes with empty items on a GridLayoutManager.
            mTagRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    /**
     * @param tag Pass a tag object to enter editing mode or null to create a new tag.
     * */
    private void showTagEditDialog(@Nullable Tag tag){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_tag, null, false);

        //Check if is in editing more or not.
        String title = (tag != null) ? getResources().getString(R.string.edit_tag) : getResources().getString(R.string.new_tag);

        //Obtain edit text object.
        EditText edit = (EditText)dialogView.findViewById(R.id.edit_text_tag);

        //Set text into edit field if the dialog is in editing mode.
        if(tag != null){
            edit.setText(tag.getName());
        }

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setTitle(title)
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String entry = edit.getText().toString();

                        if(tag != null){
                            //Update tag if in editing mode.
                            tag.setName(entry);
                            getViewModel().update(tag);
                        }else{
                            //Create tag if not in editing mode.
                            getViewModel().createTag(entry);
                        }

                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();

        dialog.show();
    }

    private void initializeOnContextItemSelectedListener(){
        mTagAdapter.setOnContextItemSelected(new TagAdapter.OnContextItemSelected() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onContextItemSelected(@NonNull MenuItem item, Tag tag) {
                int position = -1;

                try{
                    position = mTagAdapter.getSelectedPosition();
                }catch(Exception e){
                    Log.d(TagFragment.class.getName(), e.getLocalizedMessage(), e);
                    return false;
                }

                switch(item.getItemId()){
                    case R.id.menu_tag_edit:
                        showTagEditDialog(tag);
                        return true;
                    case R.id.menu_tag_delete:
                        mTagViewModel.delete(tag);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}