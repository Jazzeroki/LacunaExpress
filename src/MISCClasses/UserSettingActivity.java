package MISCClasses;

import com.JazzDevStudio.LacunaExpress.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class UserSettingActivity extends PreferenceActivity {
 
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.xml.settings);
 
    }
}