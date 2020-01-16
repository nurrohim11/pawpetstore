package com.asus.ecommerceapp.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.asus.ecommerceapp.R;

public class MyDialogFragment extends DialogFragment {
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        final String TAG="DIALOG";
//
//        if (getArguments() != null) {
//            if (getArguments().getBoolean("notAlertDialog")) {
//                return super.onCreateDialog(savedInstanceState);
//            }
//        }
//        if (getArguments() != null){
//            Log.d(TAG,"ID_PRODUK"+ getArguments().getString("id"));
//        }
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Alert Dialog");
//        builder.setMessage("Alert Dialog inside DialogFragment");
//
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dismiss();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dismiss();
//            }
//        });
//        return builder.create();
//
//    }
    int minteger=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_dialog, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final EditText idProduk = view.findViewById(R.id.id_produk);
        final EditText namaProduk = view.findViewById(R.id.nama_produk);
        final EditText h = view.findViewById(R.id.integer_number);
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("nama")))
            idProduk.setText(getArguments().getString("nama"));

        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("id")))
            namaProduk.setText(getArguments().getString("id"));

        Button btnDone = view.findViewById(R.id.btnUpdate);
        Button dec= view.findViewById(R.id.decrease);
        Button inc= view.findViewById(R.id.increase);
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minteger = minteger-1;
                h.setText(""+minteger);
            }
        });
        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minteger = minteger+1;
                h.setText(""+minteger);
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogListener dialogListener = (DialogListener) getActivity();
//                dialogListener.onFinishEditDialog(editText.getText().toString());
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
//    private void display(int number) {
//        EditText displayInteger = (EditText) getActivity().findViewById(
//                R.id.integer_number);
//        displayInteger.setText("" + number);
//    }
//    public void increaseInteger(View view) {
//        minteger = minteger + 1;
//        display(minteger);
//
//    }public void decreaseInteger(View view) {
//        minteger = minteger - 1;
//        display(minteger);
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("API123", "onCreate");

        boolean setFullScreen = false;
        if (getArguments() != null) {
            setFullScreen = getArguments().getBoolean("fullScreen");
        }

        if (setFullScreen)
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }


}
