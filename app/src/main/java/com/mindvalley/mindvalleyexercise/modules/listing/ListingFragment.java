package com.mindvalley.mindvalleyexercise.modules.listing;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.mindvalley.mindvalleyexercise.R;
import com.mindvalley.mindvalleyexercise.entities.DetailsResponse;
import com.mindvalley.mindvalleyexercise.modules.LandingActivity;
import com.mindvalley.mindvalleyexercise.modules.common.DialogListener;
import com.mindvalley.mindvalleyexercise.modules.common.FragmentInstanceHandler;
import com.mindvalley.mindvalleyexercise.modules.common.FragmentName;
import com.mindvalley.mindvalleyexercise.modules.common.ItemActionListener;
import com.mindvalley.mindvalleyexercise.modules.common.PermissionsListener;
import com.mindvalley.mindvalleyexercise.modules.details.DetailsFragment;
import com.mindvalley.mindvalleyexercise.utils.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * This Fragment is used to show the Users List.
 * <p>
 *
 * @author AjinkyaD
 * @version 1.0
 */
public class ListingFragment extends Fragment implements ListingView, PermissionsListener, ItemActionListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, DialogListener {

    FragmentInstanceHandler fragmentInstanceHandler;
    ListingPresenter listingPresenter;
    @BindView(R.id.xcVwUsers)
    RecyclerView rcVwUsers;
    @BindView(R.id.xswpLayUsersList)
    SwipeRefreshLayout swpLayUsersList;
    @BindView(R.id.ximgvwClearCache)
    FloatingActionButton fabClearCache;
    @BindView(R.id.xrellayMainParent)
    RelativeLayout rellayMainParent;


    private final int INTERNET_PERMISSION = 1, CLEAR_CACHE = 2;

    ListingAdapter listingAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing, vg, false);
        ButterKnife.bind(this, view);
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
                rellayMainParent.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rellayMainParent.setVisibility(View.VISIBLE);
                rellayMainParent.requestLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rellayMainParent.startAnimation(animation);
        listingPresenter = new ListingPresenterImplementor(getActivity(), this);
        swpLayUsersList.setOnRefreshListener(this);
        fabClearCache.setOnClickListener(this);
        ((LandingActivity) getActivity()).requestAppPermissions(new String[]{Manifest.permission.INTERNET}, R.string.enable_feature, INTERNET_PERMISSION, this);
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
    public void renderUserList(ArrayList<DetailsResponse> users) {

        listingAdapter = new ListingAdapter(getActivity(), new ArrayList<DetailsResponse>(), this);

        SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));

        animator.setAddDuration(getResources().getInteger(android.R.integer.config_longAnimTime));

        this.rcVwUsers.setItemAnimator(animator);

        rcVwUsers.setAdapter(listingAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        rcVwUsers.setLayoutManager(linearLayoutManager);


        for (DetailsResponse detailsResponse :
                users) {
            listingAdapter.add(detailsResponse);
        }
        if (swpLayUsersList.isRefreshing())
            swpLayUsersList.setRefreshing(false);
        swpLayUsersList.setEnabled(true);
    }

    @Override
    public void displayErrorMessage(String errorMessage) {
        swpLayUsersList.setEnabled(true);
    }

    @Override
    public void onPermissionGranted(int requestCode) {

        swpLayUsersList.setEnabled(false);
        listingPresenter.fetchUsers();

    }

    @Override
    public void onPermissionRejected(String[] requestedPermissions, int requestCode, int stringCode) {

    }

    @Override
    public void onItemClicked(Object currentObject, int position) {

        final DetailsResponse currentData = (DetailsResponse) currentObject;

        //Activity is in active state
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_fade_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rellayMainParent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rellayMainParent.setVisibility(View.INVISIBLE);
                rellayMainParent.requestLayout();
                DetailsFragment detailsFragment = new DetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", currentData);
                detailsFragment.setArguments(bundle);
                fragmentInstanceHandler.changeFragment(detailsFragment, FragmentName.DETAILS, true, true);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (Utilities.isAirplaneModeWithNoWIFI(getActivity()) || !Utilities.isNetworkAvailable(getActivity())) {
            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", currentData);
            detailsFragment.setArguments(bundle);
            fragmentInstanceHandler.changeFragment(detailsFragment, FragmentName.DETAILS, true, true);
        } else {
            rellayMainParent.startAnimation(animation);
        }


    }

    @Override
    public void onRefresh() {

        //Pull to Refresh is called
        listingPresenter.fetchUsers();
    }

    @Override
    public void onClick(View view) {
        if (view == fabClearCache) {

            Utilities.showAlertDialogMultipleOptions(getActivity(), CLEAR_CACHE, this, getResources().getString(R.string.clear_cache), getResources().getString(R.string.clear_cache_description), getResources().getString(R.string.yes), getResources().getString(R.string.no));
        }
    }

    @Override
    public void onPositiveAction(int dialogID, Object updatedData) {
        listingPresenter.clearCache();
        listingPresenter.fetchUsers();
    }

    @Override
    public void onNegativeAction(int dialogID, Object updatedData) {

    }
}
