package com.microsoft.imageseach.Binder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.imageseach.Model.Pages;
import com.microsoft.imageseach.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Tatyabhau Chavan on 5/4/2016.
 */
public class ImageLoaderAdapter extends RecyclerView.Adapter<ImageLoaderAdapter.ViewHolder> {


    private List<Pages> pages;

    public ImageLoaderAdapter(List<Pages> pages) {
        this.pages = pages;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Pages page = pages.get(position);
        holder.title.setText(page.getTitle());
        if (!page.getThumbNail().getSource().isEmpty()) {
            Picasso.with(holder.imgLogo.getContext()).load(page.getThumbNail().getSource()).placeholder(R.drawable.loaging).into(holder.imgLogo);
        }

    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imgLogo;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.lblTitle);
            imgLogo = (ImageView) itemView.findViewById(R.id.imgLogo);
        }
    }
}
