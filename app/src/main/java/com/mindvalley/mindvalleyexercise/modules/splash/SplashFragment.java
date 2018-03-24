package com.mindvalley.mindvalleyexercise.modules.splash;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindvalley.mindvalleyexercise.R;
import com.mindvalley.mindvalleyexercise.modules.common.FragmentInstanceHandler;
import com.mindvalley.mindvalleyexercise.modules.common.FragmentName;
import com.mindvalley.mindvalleyexercise.modules.listing.ListingFragment;
import com.mindvalley.mindvalleyexercise.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Fragment is used to show the Splash Screen to the User.
 * <p>
 *
 * @author AjinkyaD
 * @version 1.0
 */
public class SplashFragment extends Fragment implements SplashView {

    FragmentInstanceHandler fragmentInstanceHandler;
    SplashPresenter splashPresenter;
    @BindView(R.id.xtxtvwWelcome)
    TextView txtvwWelcome;
    @BindView(R.id.xtxtvwMindvalley)
    TextView txtvwMindvalley;
    @BindView(R.id.xlinlayWelcomeTextHolder)
    LinearLayout linlayWelcomeTextHolder;
    @BindView(R.id.xrellayMainParent)
    RelativeLayout rellayMainParent;


    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, vg, false);
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
        splashPresenter = new SplashPresenterImplementor(this);
        splashPresenter.showSplashAnimation();
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
    public void navigateToListingScreen() {

        //We need to change the fragment to the Search Fragment.
        if (fragmentInstanceHandler != null) {
            getFragmentManager().popBackStack();
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
                    ListingFragment listingFragment = new ListingFragment();
                    fragmentInstanceHandler.changeFragment(listingFragment, FragmentName.LISTING, true, true);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if (Utilities.isAirplaneModeWithNoWIFI(getActivity()) || !Utilities.isNetworkAvailable(getActivity())) {
                ListingFragment listingFragment = new ListingFragment();
                fragmentInstanceHandler.changeFragment(listingFragment, FragmentName.LISTING, true, true);
            } else {
                rellayMainParent.startAnimation(animation);
            }

        }
    }

    @Override
    public void animateViews() {
        linlayWelcomeTextHolder.setVisibility(View.VISIBLE);

        Animation fadeIn1 = new AlphaAnimation(0, 1);
        fadeIn1.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn1.setDuration(1000);
        txtvwWelcome.setAnimation(fadeIn1);

        Animation fadeIn2 = new AlphaAnimation(0, 1);
        fadeIn2.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn2.setDuration(1000);
        fadeIn2.setStartOffset(1000);
        txtvwMindvalley.setAnimation(fadeIn2);


        splashPresenter.delayNavigation();

    }
}
