package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class BasicDetailsFragment extends Fragment {
	
	private Button showHideAdditional;
	private Button save;
	private Button cancel;
	private EditText name;
	private EditText phone;
	private EditText email;
	private EditText address;
	private EditText jobTitle;
	private EditText company;
	private EditText website;
	private EditText im;
	
	
	@Override
	  public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle state) {
	    return layoutInflater.inflate(R.layout.fragment_basic_details, container, false);
	  }
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		showHideAdditional = (Button) getActivity().findViewById(R.id.button1);
		save = (Button) getActivity().findViewById(R.id.button2);
		cancel = (Button) getActivity().findViewById(R.id.button3);
		
		name = (EditText) getActivity().findViewById(R.id.editText1);
		phone = (EditText) getActivity().findViewById(R.id.editText2);
		email = (EditText) getActivity().findViewById(R.id.editText3);
		address = (EditText) getActivity().findViewById(R.id.editText4);
		
		showHideAdditional.setOnClickListener(new ButtonListener());
		save.setOnClickListener(new ButtonListener());
		cancel.setOnClickListener(new ButtonListener());
		
	}
	
	private class ButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			Button btn = (Button) view;
			
			if (btn == showHideAdditional) {
				FragmentManager fragmentManager = getActivity().getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				AdditionalDetailsFragment additionalDetailsFragment = (AdditionalDetailsFragment)fragmentManager.findFragmentById(R.id.containerBottom);
				
				if (additionalDetailsFragment == null) {
				  fragmentTransaction.add(R.id.containerBottom, new AdditionalDetailsFragment());
				  btn.setText(getActivity().getResources().getString(R.string.hide_additional_fields));
				  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
				} else {
				  fragmentTransaction.remove(additionalDetailsFragment);
				  btn.setText(getActivity().getResources().getString(R.string.show_additional_fields));
				  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
				
				}
				fragmentTransaction.commit();
			} else if (btn == save) {
				jobTitle = (EditText) getActivity().findViewById(R.id.editText5);
				company = (EditText) getActivity().findViewById(R.id.editText6);
				website = (EditText) getActivity().findViewById(R.id.editText7);
				im = (EditText) getActivity().findViewById(R.id.editText8);
				
				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
				
				if (name != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText());
				}
				if (phone != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.getText());
				}
				if (email != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email.getText());
				}
				if (address != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address.getText());
				}
				if (jobTitle != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle.getText());
				}
				if (company != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company.getText());
				}
				
				ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
				
				if (website != null) {
				  ContentValues websiteRow = new ContentValues();
				  websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
				  websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website.getText().toString());
				  contactData.add(websiteRow);
				}
				
				if (im != null) {
				  ContentValues imRow = new ContentValues();
				  imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
				  imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im.getText().toString());
				  contactData.add(imRow);
				}
				
				intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
				getActivity().startActivity(intent);
			} else if (btn == cancel) {
				getActivity().finish();
			}
			
		}
		
	}

}
