package by.bstu.svs.fit.lr3.recycler;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.Collection;
import java.util.List;

import by.bstu.svs.fit.lr3.R;
import by.bstu.svs.fit.lr3.manager.ImageManager;
import by.bstu.svs.fit.lr3.person.Person;

public class ListenerAdapter extends RecyclerView.Adapter<ListenerAdapter.ListenerViewHolder> {

    private List<Person> listeners;
    private File filesDir;

    public ListenerAdapter(List<Person> listeners, File filesDir) {
        this.listeners = listeners;
        this.filesDir = filesDir;
    }

    public void setItems(Collection<Person> tweets) {
        listeners.clear();
        listeners.addAll(tweets);
        notifyDataSetChanged();
    }

    public void clearItems() {
        listeners.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ListenerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listener_view, parent, false);
        return new ListenerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListenerViewHolder holder, int position) {
        holder.bind(listeners.get(position));
    }

    @Override
    public int getItemCount() {
        return listeners == null ? 0 : listeners.size();
    }

    class ListenerViewHolder extends RecyclerView.ViewHolder {

        private ImageView pictureImageView;
        private TextView nameTextView;
        private TextView emailTextView;

        public ListenerViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureImageView = itemView.findViewById(R.id.picture);
            nameTextView = itemView.findViewById(R.id.name);
            emailTextView = itemView.findViewById(R.id.email);
        }

        public void bind(Person person) {

            String name = person.getFirstName() + " " + person.getSecondName();
            nameTextView.setText(name);
            emailTextView.setText(person.getEmail());

            if (person.getImage() == null) {
                pictureImageView.setImageResource(R.drawable.empty_photo);
            }
            else {
                ImageManager.getBitMapFromFile(new File(filesDir.getAbsolutePath() + "/images", person.getImage()))
                        .ifPresent(pictureImageView::setImageBitmap);
            }

        }
    }

}
