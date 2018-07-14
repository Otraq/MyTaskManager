package com.example.hosse.mytaskmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context mCtx;
    private List<Note> noteList1;
    private int mode;
    private NoteList noteList = NoteList.getInstance();

    NoteAdapter(Context mCtx, int mode) {
        this.mCtx = mCtx;
        this.mode = mode;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card_layout, null);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = noteList.getNewNoteList(mode).get(position);
        holder.bindNote(note);
    }

    @Override
    public int getItemCount() {
        return noteList.getNewNoteList(mode).size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {


        private TextView dateTime;
        private TextView title;
        private Note currentNote;
        private ImageView titleImage;
        private TextDrawable drawable;

        public NoteViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_view);
            dateTime = itemView.findViewById(R.id.date_view);
            titleImage = itemView.findViewById(R.id.image_title);
        }

        public void bindNote(Note note) {
            currentNote = note;

            title.setText(currentNote.getTitle());
            dateTime.setText(currentNote.getDateTime().toString());
            drawable = TextDrawable.builder().buildRound(currentNote.getTitle().substring(0, 1), Color.BLACK);
            titleImage.setImageDrawable(drawable);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("noteid", String.valueOf(currentNote.getId()));
                    Intent intent = DetailsActivity.newIntent(mCtx, currentNote.getId());
                    mCtx.startActivity(intent);
                }
            });
        }
    }
}
