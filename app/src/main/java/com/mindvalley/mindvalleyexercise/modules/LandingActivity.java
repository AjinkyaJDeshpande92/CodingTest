package com.mindvalley.mindvalleyexercise.modules;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;

import com.mindvalley.mindvalleyexercise.R;
import com.mindvalley.mindvalleyexercise.modules.common.FragmentInstanceHandler;
import com.mindvalley.mindvalleyexercise.modules.common.FragmentName;
import com.mindvalley.mindvalleyexercise.modules.common.PermissionsListener;
import com.mindvalley.mindvalleyexercise.modules.details.DetailsFragment;
import com.mindvalley.mindvalleyexercise.modules.listing.ListingFragment;
import com.mindvalley.mindvalleyexercise.modules.splash.SplashFragment;
import com.mindvalley.mindvalleyexercise.modules.web.WebViewFragment;


public class LandingActivity extends AppCompatActivity implements FragmentInstanceHandler {

    private PermissionsListener permissionsListener;
    private SparseIntArray mErrorString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);
        mErrorString = new SparseIntArray();
        changeFragment(new SplashFragment(), FragmentName.SPLASH, true, true);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
            updateUIState();
        } else {
            super.onBackPressed();
        }

    }

    private void updateUIState() {

        Fragment currentFragment = getCurrentTopFragment();

        if (currentFragment instanceof SplashFragment) {
            //Kill the App
            this.finish();
        } else if (currentFragment instanceof ListingFragment) {
            // Current Visible Fragment is ListingFragment
            ListingFragment listingFragment = (ListingFragment) currentFragment;

            listingFragment.initObjects();

        } else if (currentFragment instanceof DetailsFragment) {
            // Current Visible Fragment is DetailsFragment
            DetailsFragment detailsFragment = (DetailsFragment) currentFragment;

            detailsFragment.initObjects();

        } else if (currentFragment instanceof WebViewFragment) {
            // Current Visible Fragment is WebViewFragment
            WebViewFragment webViewFragment = (WebViewFragment) currentFragment;

            webViewFragment.initObjects();

        }
    }

    /**
     * This function is used to get the current Top Fragment from the Back stack
     *
     * @return - The Fragment on the Top
     */
    private Fragment getCurrentTopFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        //Performs any previous pending operations in the queue for the fragments
        fragmentManager.executePendingTransactions();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentManager.findFragmentByTag(fragmentTag);
    }

    @Override
    public void changeFragment(Fragment currentFragment, FragmentName fragmentName, boolean addToBackStack, boolean removePrevious) {
        FragmentManager fm = getFragmentManager();
        try {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.custom_fade_in, R.animator.custom_fade_out, R.animator.custom_fade_in, R.animator.custom_fade_out);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack("" + fragmentName);
            }
            fragmentTransaction.add(R.id.fragmentHolder, currentFragment, "" + fragmentName);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function is used to handle the runtime permissions through out the Application.
     *
     * @param requestedPermissions - The List of Permissions requested
     * @param stringId             - The Heading for the Permission Request
     * @param requestCode          - The Request Code
     * @param permissionsListener  - The Permissions Listener
     */
    public void requestAppPermissions(final String[] requestedPermissions,
                                      final int stringId, final int requestCode, PermissionsListener permissionsListener) {
        mErrorString.put(requestCode, stringId);
        this.permissionsListener = permissionsListener;
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale = false;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale) {
                if (permissionsListener != null) {
                    permissionsListener.onPermissionRejected(requestedPermissions, requestCode, stringId);
                }
            } else {
                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
                if (permissionsListener != null) {
                    permissionsListener.onPermissionRejected(requestedPermissions, requestCode, stringId);
                }
            }

        } else {
            if (permissionsListener != null) {
                permissionsListener.onPermissionGranted(requestCode);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if (permissionsListener != null) {
                permissionsListener.onPermissionGranted(requestCode);
            }
        }
    }

}
