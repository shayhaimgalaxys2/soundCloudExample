package com.example.shay.soundcloudexample.project.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.shay.soundcloudexample.R;
import com.example.shay.soundcloudexample.project.app.AppController;
import com.example.shay.soundcloudexample.project.dataObjects.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shay on 17/02/2016.
 */
public class CollectionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int RAW = 0;
    private static final int MP3 = 1;
    private static final int WAV = 2;
    private static final int OTHER_FORMAT = 3;


    private Context context;
    private List<Collection> collectionList;
    private boolean isGrigLayout;


    public class RawViewHolder extends RecyclerView.ViewHolder {

        public TextView textTv;


        public RawViewHolder(View view) {
            super(view);
            textTv = (TextView) view.findViewById(R.id.textTv);

        }
    }

    public class MP3ViewHolder extends RecyclerView.ViewHolder {

        public TextView textTv;
        public NetworkImageView smallImageIv;

        public MP3ViewHolder(View view) {
            super(view);
            textTv = (TextView) view.findViewById(R.id.textTv);
            smallImageIv = (NetworkImageView) view.findViewById(R.id.smallImageIv);
        }
    }

    public class WavViewHolder extends RecyclerView.ViewHolder {

        public TextView textTv;
        public NetworkImageView smallImageIv;

        public WavViewHolder(View view) {
            super(view);
            textTv = (TextView) view.findViewById(R.id.textTv);
            smallImageIv = (NetworkImageView) view.findViewById(R.id.smallImageIv);
        }
    }


    public class OtherFormatViewHolder extends RecyclerView.ViewHolder {

        public TextView textTv;
        public NetworkImageView smallImageIv;

        public OtherFormatViewHolder(View view) {
            super(view);
            textTv = (TextView) view.findViewById(R.id.textTv);
            smallImageIv = (NetworkImageView) view.findViewById(R.id.smallImageIv);
        }
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {


        public NetworkImageView smallImageIv;

        public GridViewHolder(View view) {
            super(view);
            smallImageIv = (NetworkImageView) view.findViewById(R.id.smallImageIv);
        }
    }


    public CollectionsAdapter(Context context, List<Collection> collectionList, boolean isGridLayout) {
        this.context = context;
        this.collectionList = collectionList;
        this.isGrigLayout = isGridLayout;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;


        if (isGrigLayout) {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.collection_grid_list_row, parent, false);

            return new GridViewHolder(itemView);

        } else {


            switch (viewType) {
                case RAW:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.collection_raw_list_row, parent, false);

                    return new RawViewHolder(itemView);

                case MP3:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.collection_mp3_list_row, parent, false);

                    return new MP3ViewHolder(itemView);

                case WAV:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.collection_wav_list_row, parent, false);

                    return new WavViewHolder(itemView);

                case OTHER_FORMAT:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.collection_other_format_list_row, parent, false);

                    return new OtherFormatViewHolder(itemView);


                default:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.collection_other_format_list_row, parent, false);

                    return new OtherFormatViewHolder(itemView);
            }

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Collection collection = collectionList.get(position);

        if (isGrigLayout) {
            setImage(collection, ((GridViewHolder) holder).smallImageIv);
        } else {


            if (holder.getClass().getSimpleName().equals(RawViewHolder.class.getSimpleName())) {
                ((RawViewHolder) holder).textTv.setText(collection.getTitle());
            } else if (holder.getClass().getSimpleName().equals(MP3ViewHolder.class.getSimpleName())) {
                ((MP3ViewHolder) holder).textTv.setText(collection.getTitle());
                setImage(collection, ((MP3ViewHolder) holder).smallImageIv);
            } else if (holder.getClass().getSimpleName().equals(WavViewHolder.class.getSimpleName())) {
                ((WavViewHolder) holder).textTv.setText(collection.getTitle());
                setImage(collection, ((WavViewHolder) holder).smallImageIv);
            } else {
                ((OtherFormatViewHolder) holder).textTv.setText(collection.getTitle());
                setImage(collection, ((OtherFormatViewHolder) holder).smallImageIv);
            }

        }

    }

    private void setImage(Collection collection, NetworkImageView smallImageIv) {

        if (!collection.getArtworkUrl().equals("")) {

            ImageLoader mImageLoader = AppController.getInstance().getImageLoader();
            mImageLoader.get(collection.getArtworkUrl(), ImageLoader.getImageListener(smallImageIv,
                    R.drawable.placeholder, R.drawable.placeholder));
            smallImageIv.setImageUrl(collection.getArtworkUrl(), mImageLoader);


        }

    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    @Override
    public int getItemViewType(int position) {

        Collection collection = collectionList.get(position);
        return collection.getOriginalFormatEnum().ordinal();


    }
}
