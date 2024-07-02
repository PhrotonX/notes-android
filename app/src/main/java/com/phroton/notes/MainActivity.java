package com.phroton.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.phroton.notes.databinding.ActivityMainBinding;
import com.phroton.notes.ui.editor.EditorActivity;
import com.phroton.notes.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static final int CREATE_NOTE_REQUEST = 0;

    private NoteViewModel mNoteViewModel;

    private ActivityResultLauncher<Intent> mInsertContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        mInsertContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Note note;
                    switch(result.getResultCode()){
                        case RESULT_OK:
                            note = Note.unpackCurrentNote(result.getData(), false);
                            mNoteViewModel.insert(note);
                            break;
                        case RESULT_CANCELED:
                            //Toast.makeText(getApplicationContext(), "MainActivity: Canceled", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "MainActivity: Error", Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
        });

        /*if(mNote != null){
            mNoteViewModel.insert(mNote);
        }*/

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                //startActivityForResult(intent, CREATE_NOTE_REQUEST);
                mInsertContent.launch(intent);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*
        TextView navSubtitle = (TextView)findViewById(R.id.textView);
        String navSubtitleStr = navSubtitle.getText().toString();
        navSubtitleStr += " " + BuildConfig.VERSION_NAME;
        navSubtitle.setText(navSubtitleStr);*/

        if(savedInstanceState == null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.home_fragment_root, new HomeFragment());
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}