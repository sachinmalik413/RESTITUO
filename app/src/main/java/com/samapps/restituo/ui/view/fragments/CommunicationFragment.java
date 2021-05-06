package com.samapps.restituo.ui.view.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Contacts;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.samapps.restituo.R;
import com.samapps.restituo.ui.view.CreateRestituoActivity;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunicationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunicationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // for importing contacts

    private static final int CONTACT_PICK_CODE = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommunicationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunicationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunicationFragment newInstance(String param1, String param2) {
        CommunicationFragment fragment = new CommunicationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_communication, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Button)view.findViewById(R.id.next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CreateRestituoActivity)getActivity()).addFragment(3);
            }
        });

        ImageButton contact=view.findViewById(R.id.importContact);
       // TextView phone = view.findViewById(R.id.EdPhone);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickContactIntent();
            }
        });
    }

    private void pickContactIntent(){
        //intent to pick contact
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView Number = getView().findViewById(R.id.tvPhone);
        TextView Name = getView().findViewById(R.id.tvName);
        //handle intent results
        if (resultCode == RESULT_OK) {
            //calls when user click a contact from list

            if (requestCode == CONTACT_PICK_CODE) {
                Number.setText("");
                Name.setText("");

                Cursor cursor1, cursor2;

                //get data from intent
                Uri uri = data.getData();

                cursor1 = getContext().getContentResolver().query(uri, null, null, null, null);

                if (cursor1.moveToFirst()) {
                    //get contact details
                    String contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                    String contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String idResults = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    int idResultHold = Integer.parseInt(idResults);

                   // contactTv.append("ID: " + contactId);
                   // contactTv.append("\nName: " + contactName);
                    Name.setText(contactName);

                    if (idResultHold == 1) {
                        cursor2 = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                                null,
                                null
                        );
                        //a contact may have multiple phone numbers
                        while (cursor2.moveToNext()) {
                            //get phone number
                            String contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //set details

                            //contactTv.append("\nPhone: " + contactNumber);
                            Number.setText(contactNumber);

                        }
                        cursor2.close();
                    }
                    cursor1.close();
                }
            }

        } else {
            //calls when user click back button | don't pick contact
        }
    }

}