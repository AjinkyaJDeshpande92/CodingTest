package com.mindvalley.mindvalleyexercise.modules.listing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindvalley.mindvalleyexercise.R;
import com.mindvalley.mindvalleyexercise.entities.DetailsResponse;
import com.mindvalley.mindvalleyexercise.modules.common.ItemActionListener;
import com.mindvalley.webservicemanager.interfaces.WebserviceResponseListener;
import com.mindvalley.webservicemanager.models.WebCallDataTypeDownload;
import com.mindvalley.webservicemanager.models.WebCallDataTypeDownloadImage;
import com.mindvalley.webservicemanager.utilities.WebserviceManagerDataTypeDownload;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * This adapter is used to render the Restaurant Working Hour Details UI to the User
 *
 * @author AjinkyaD
 * @version 1.0
 */

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.UserDetailsHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<DetailsResponse> arrlstData;
    private ItemActionListener itemActionListener;

    private WebserviceManagerDataTypeDownload mProvider;

    public ListingAdapter(Context context, ArrayList<DetailsResponse> arrlstData, ItemActionListener itemActionListener) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.arrlstData = arrlstData;
        this.itemActionListener = itemActionListener;
        mProvider = WebserviceManagerDataTypeDownload.getInstance();
    }

    @Override
    public UserDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserDetailsHolder(inflater.inflate(R.layout.custom_listing_item, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final UserDetailsHolder holder, final int position) {

        final DetailsResponse currentData = arrlstData.get(position);

        holder.txtvwLikes.setText(String.format("%d", currentData.getLikes()));
        holder.txtvwTitle.setText(String.format("Photo By %s", currentData.getUser().getName()));

        if (currentData.isLiked_by_user()) {
            holder.imgvwLike.setImageResource(R.drawable.like_filled);
        } else {
            holder.imgvwLike.setImageResource(R.drawable.like);
        }


        WebCallDataTypeDownload mDataTypeImageCancel = new WebCallDataTypeDownloadImage(currentData.getUrls().getThumb(), new WebserviceResponseListener() {
            @Override
            public void onStart(WebCallDataTypeDownload mDownloadDataType) {

            }

            @Override
            public void onSuccess(WebCallDataTypeDownload mDownloadDataType) {
                holder.imgvwThumbnail.setImageBitmap(((WebCallDataTypeDownloadImage) mDownloadDataType).getImageBitmap());
            }

            @Override
            public void onFailure(WebCallDataTypeDownload mDownloadDataType, int statusCode, byte[] errorResponse, Throwable e) {
                holder.imgvwThumbnail.setImageResource(R.drawable.broken);
            }

            @Override
            public void onRetry(WebCallDataTypeDownload mDownloadDataType, int retryNo) {

            }
        });
        mProvider.getRequest(mDataTypeImageCancel);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemActionListener != null) {
                    itemActionListener.onItemClicked(currentData, position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrlstData.size();
    }

    class UserDetailsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ximgvwThumbnail)
        ImageView imgvwThumbnail;
        @BindView(R.id.xtxtvwTitle)
        TextView txtvwTitle;
        @BindView(R.id.xtxtvwLikes)
        TextView txtvwLikes;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.ximgvwLike)
        ImageView imgvwLike;

        UserDetailsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void add(DetailsResponse newData) {

        arrlstData.add(arrlstData.size(), newData);
        notifyItemInserted(arrlstData.size());

    }


}
