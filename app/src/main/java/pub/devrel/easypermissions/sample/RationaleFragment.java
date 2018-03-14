package pub.devrel.easypermissions.sample;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import java.util.List;

public class RationaleFragment extends Fragment implements EasyPermissions.RationaleDialogCallback {
    private static final String TAG = "RationaleFragment";
    private static final int RC_STORAGE_PERM = 123;
    public static final String PERMISSION_STORAGE =  Manifest.permission.WRITE_EXTERNAL_STORAGE;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Create view
        View v = inflater.inflate(R.layout.fragment_rational, container);

        // Button click listener
        v.findViewById(R.id.button_storage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageTask();
            }
        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_STORAGE_PERM)
    private void storageTask() {
        if (EasyPermissions.hasPermissions(getContext(), PERMISSION_STORAGE)) {
            // Have permission, do the thing!
            showToast("TODO: Storage things...");
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_storage),
                                               RC_STORAGE_PERM, PERMISSION_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        showToast("Storage granted");
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.toString());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        showToast("Storage denied");
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.toString());
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRationaleDialogButtonClicked(int which, int requestCode, @NonNull List<String> perms) {
        if (which == Dialog.BUTTON_POSITIVE) {
            showToast("Rational dialog positive button clicked");
        } else {
            showToast("Rational dialog negative button clicked");
        }
        Log.d(TAG, "onRationaleDialogButtonClicked:" + requestCode + ":" + perms.toString());
    }
}
