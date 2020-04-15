package com.huriati.project.tvkabel.ui.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.MainActivity;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.api.BaseApiService;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    Context context;
    BaseApiService baseApiService;
    SharedPrefManager sharedPrefManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application

                sharedPrefManager = new SharedPrefManager(context);
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, "");
                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, "");
                sharedPrefManager.saveSPString(SharedPrefManager.SP_AUTH, "");

//                context.startActivity(new Intent(context, LoginActivity.class)
//                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                baseApiService.tagihanRequest("1");
                finishActivity();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                context.startActivity(new Intent(context, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

//

        return root;
    }

    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }
}