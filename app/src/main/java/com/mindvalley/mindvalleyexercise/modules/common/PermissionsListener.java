package com.mindvalley.mindvalleyexercise.modules.common;

/**
 * This interface is used to listen the Permission request made by the application and the action taken by the user on the same.
 *
 * @author AjinkyaD
 * @version 1.0
 */

public interface PermissionsListener {

    /**
     * This function is called when the User grants the permission
     *
     * @param requestCode - The requested Permission code
     */
    void onPermissionGranted(int requestCode);

    /**
     * This function is called when the User rejects the permission requested
     *
     * @param requestedPermissions - The requested Permissions
     * @param requestCode          - The requested Permission code
     * @param stringCode           - The String to be displayed
     */
    void onPermissionRejected(String[] requestedPermissions, int requestCode, int stringCode);
}
