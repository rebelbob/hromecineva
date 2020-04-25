package com.mustplay.moviejournal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.mustplay.moviejournal.Movie;
import com.mustplay.moviejournal.R;
import com.mustplay.moviejournal.download.DownloadMoviePage;
import com.mustplay.moviejournal.util.MovieStorage;
import com.squareup.picasso.Picasso;

public class MoviePageFragment extends Fragment {
    private int curMoviePos;
    private View root;
    private ImageButton addMovieButton;
    private Movie.Status status;
    private static MoviePageFragment fragment;

    MoviePageFragment() {}

    public MoviePageFragment(int curMoviePos, Movie.Status status) {
        this.curMoviePos = curMoviePos;
        this.status = status;
        fragment = this;
    }

    public static MoviePageFragment getInstance() {
        if (fragment == null) {
            fragment = new MoviePageFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_movie_page, container, false);

        addMovieButton = root.findViewById(R.id.add_movie);
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieStorage.addToMark(curMoviePos);
            }
        });

        //загрузка данных в методе onDataLoad(), а метод вызывается в DownloadMoviePage()
        new DownloadMoviePage().execute(MovieStorage.getMovie(curMoviePos, status));
        return root;
    }

    public void onDataLoad(){
        ImageView poster = root.findViewById(R.id.poster);
        TextView description = root.findViewById(R.id.description);
        TextView title = root.findViewById(R.id.title);

        Picasso.with(getContext()).load(MovieStorage.getMovie(curMoviePos, status).getPosterUrl()).into(poster);
        title.setText(MovieStorage.getMovie(curMoviePos, status).getTitle());
        description.setText(MovieStorage.getMovie(curMoviePos, status).getDescription());
    }
}
