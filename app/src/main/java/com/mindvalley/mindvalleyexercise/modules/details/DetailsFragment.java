package com.mindvalley.mindvalleyexercise.modules.details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindvalley.mindvalleyexercise.R;
import com.mindvalley.mindvalleyexercise.entities.DetailsResponse;
import com.mindvalley.mindvalleyexercise.entities.UserDetailsResponse;
import com.mindvalley.mindvalleyexercise.modules.common.FragmentInstanceHandler;
import com.mindvalley.mindvalleyexercise.modules.common.FragmentName;
import com.mindvalley.mindvalleyexercise.modules.web.WebViewFragment;
import com.mindvalley.mindvalleyexercise.utils.Utilities;
import com.mindvalley.webservicemanager.interfaces.WebserviceResponseListener;
import com.mindvalley.webservicemanager.models.WebCallDataTypeDownload;
import com.mindvalley.webservicemanager.models.WebCallDataTypeDownloadImage;
import com.mindvalley.webservicemanager.utilities.WebserviceManagerDataTypeDownload;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Fragment is used to show the Splash Screen to the User.
 * <p>
 *
 * @author AjinkyaD
 * @version 1.0
 */
public class DetailsFragment extends Fragment implements DetailsView, View.OnClickListener {

    FragmentInstanceHandler fragmentInstanceHandler;
    DetailsPresenter detailsPresenter;
    DetailsResponse currentData;
    @BindView(R.id.ximgvwFullView)
    ImageView imgvwFullView;
    @BindView(R.id.xtxtvwTitle)
    TextView txtvwTitle;
    @BindView(R.id.ximgvwLike)
    ImageView imgvwLike;
    @BindView(R.id.xtxtvwLikes)
    TextView txtvwLikes;
    @BindView(R.id.xlinlayParent)
    LinearLayout linlayParent;
    @BindView(R.id.xtxtvwImageDimensions)
    TextView txtvwImageDimensions;
    @BindView(R.id.xtxtvwImagePublishedDate)
    TextView txtvwImagePublishedDate;
    @BindView(R.id.xtxtvwImageCategory)
    TextView txtvwImageCategory;

    private WebserviceManagerDataTypeDownload mProvider;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, vg, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentData = (DetailsResponse) bundle.getSerializable("data");
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initObjects();
    }

    /**
     * This function is used to initialise the objects that are going to be used in this fragment
     */
    public void initObjects() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_fade_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linlayParent.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linlayParent.setVisibility(View.VISIBLE);
                linlayParent.requestLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        linlayParent.startAnimation(animation);
        mProvider = WebserviceManagerDataTypeDownload.getInstance();
        txtvwTitle.setOnClickListener(this);
        detailsPresenter = new DetailsPresenterImplementor(this);
        detailsPresenter.renderDetails(currentData);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentInstanceHandler = (FragmentInstanceHandler) getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentInstanceHandler = (FragmentInstanceHandler) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void renderPhoto(String full) {
        WebCallDataTypeDownload mDataTypeImageCancel = new WebCallDataTypeDownloadImage(currentData.getUrls().getThumb(), new WebserviceResponseListener() {
            @Override
            public void onStart(WebCallDataTypeDownload mDownloadDataType) {

            }

            @Override
            public void onSuccess(WebCallDataTypeDownload mDownloadDataType) {
                imgvwFullView.setImageBitmap(((WebCallDataTypeDownloadImage) mDownloadDataType).getImageBitmap());
            }

            @Override
            public void onFailure(WebCallDataTypeDownload mDownloadDataType, int statusCode, byte[] errorResponse, Throwable e) {
                imgvwFullView.setImageResource(R.drawable.broken);
            }

            @Override
            public void onRetry(WebCallDataTypeDownload mDownloadDataType, int retryNo) {

            }
        });
        mProvider.getRequest(mDataTypeImageCancel);
    }

    @Override
    public void setBackgroundColor(String color) {
        linlayParent.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void renderUserDetails(UserDetailsResponse user) {
        txtvwTitle.setText(String.format("Photo By %s", user.getName()));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void renderPhotoDetails(DetailsResponse currentData) {

        txtvwLikes.setText(String.format("%d", currentData.getLikes()));
        txtvwImageDimensions.setText(String.format("Image Dimensions - %d x %d", currentData.getWidth(), currentData.getHeight()));
//        txtvwImagePublishedDate.setText(String.format("Creation Date - %s", Utilities.formattedDateFromDate(Utilities.getDateFromString("yyyy-MM-dd'T'HH:mm:ssXXX", currentData.getCreated_at()), "dd-MM-yyyy")));
    }

    @Override
    public void renderCategories(String displayCategories) {
        txtvwImageCategory.setText(String.format("Image Category - %s", displayCategories));
    }

    @Override
    public void onClick(View view) {
        if (view == txtvwTitle) {
            //Navigate him to User Profile
            //Activity is in active state
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_fade_out);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    linlayParent.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linlayParent.setVisibility(View.INVISIBLE);
                    linlayParent.requestLayout();
                    WebViewFragment webViewFragment = new WebViewFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", currentData.getUser().getLinks().getHtml());
                    webViewFragment.setArguments(bundle);
                    fragmentInstanceHandler.changeFragment(webViewFragment, FragmentName.WEB, true, true);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if (Utilities.isAirplaneModeWithNoWIFI(getActivity()) || !Utilities.isNetworkAvailable(getActivity())) {
                WebViewFragment webViewFragment = new WebViewFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", currentData.getUser().getLinks().getHtml());
                webViewFragment.setArguments(bundle);
                fragmentInstanceHandler.changeFragment(webViewFragment, FragmentName.WEB, true, true);
            } else {
                linlayParent.startAnimation(animation);
            }

        }
    }
}
