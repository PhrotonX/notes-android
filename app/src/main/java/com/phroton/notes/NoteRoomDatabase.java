package com.phroton.notes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.phroton.notes.data.taggednote.TaggedNote;
import com.phroton.notes.data.taggednote.TaggedNoteDao;
import com.phroton.notes.data.taggednote.TaggedNoteViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class, Tag.class, TaggedNote.class}, version = 115, exportSchema = true/*,
    autoMigrations = {
        @AutoMigration(from = 106, to = 108),
            @AutoMigration(from = 107, to = 108)
    }*/
)
@TypeConverters({Converters.class})
public abstract class NoteRoomDatabase extends RoomDatabase{

    public abstract NoteDao noteDao();
    public abstract TagDao tagDao();
    public abstract TaggedNoteDao taggedNoteDao();
    private static volatile NoteRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NoteRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (NoteRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NoteRoomDatabase.class, "note_database")
                            .fallbackToDestructiveMigration()
                            .addMigrations(MIGRATION_106_107, MIGRATION_106_108, MIGRATION_107_108)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                TagDao tagDao = INSTANCE.tagDao();
                tagDao.deleteAll();

                Tag tag1 = new Tag("To Do");
                Tag tag2 = new Tag("Shopping");
                Tag tag3 = new Tag("Others");

                tagDao.insert(tag1);
                tagDao.insert(tag2);
                tagDao.insert(tag3);

                NoteDao noteDao = INSTANCE.noteDao();
                noteDao.deleteAll();

                Note note1 = new Note("Sample Data 1", "The quick brown fox jumps over" +
                        " the lazy dog");
                Note note2 = new Note("Sample Data 2", "Lorem ipsum dolor sit amet," +
                        " consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et " +
                        "dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                        "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
                        "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                        "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
                        "deserunt mollit anim id est laborum.");
                Note note3 = new Note("Sample Data 3", "Take notes by tapping the + " +
                        "button.");

                noteDao.insert(note1);
                noteDao.insert(note2);
                noteDao.insert(note3);

                TaggedNoteDao taggedNoteDao = INSTANCE.taggedNoteDao();
                taggedNoteDao.deleteAll();
            });
        }
    };

    public static Migration MIGRATION_106_107 = new Migration(106, 107){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("ALTER TABLE notes ADD COLUMN is_deleted INTEGER NOT NULL DEFAULT 0");
        }
    };
    public static Migration MIGRATION_106_108 = new Migration(106, 108){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("BEGIN TRANSACTION;" +
                    "CREATE TEMPORARY TABLE notes_backup(id, title, content, color, is_deleted);" +
                    "INSERT INTO notes_backup SELECT id, title, content, color, is_deleted FROM notes;" +
                    "DROP TABLE notes;" +
                    "CREATE TABLE notes(id, title, content, color, is_deleted);" +
                    "INSERT INTO notes SELECT id, title, content, color, is_deleted FROM notes_backup;" +
                    "DROP TABLE notes_backup;" +
                    "COMMIT;");
        }
    };
    public static Migration MIGRATION_107_108 = new Migration(107, 108){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("BEGIN TRANSACTION;" +
                    "CREATE TEMPORARY TABLE notes_backup(id, title, content, color, is_deleted);" +
                    "INSERT INTO notes_backup SELECT id, title, content, color, is_deleted FROM notes;" +
                    "DROP TABLE notes;" +
                    "CREATE TABLE notes(id, title, content, color, is_deleted);" +
                    "INSERT INTO notes SELECT id, title, content, color, is_deleted FROM notes_backup;" +
                    "DROP TABLE notes_backup;" +
                    "COMMIT;");
        }
    };
}
