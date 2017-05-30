package app.nutrimeat.meat.org.nutrimeat;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREF_NAME = "Nutrimeat-welcome";
    public static final String PREF_PRODUCT_CART = "productsincart";

    public boolean getIsGuestLogin() {
        return pref.getBoolean(IS_GUEST_LOGIN, true);
    }

    public void setIsGuestLogin(boolean status) {
        editor.putBoolean(IS_GUEST_LOGIN, status);
        editor.commit();
    }

    private static final String IS_GUEST_LOGIN = "IsGuestLogin";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String MOBILE = "mobile";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setEmail(String email) {
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(EMAIL, null);
    }

    public void setName(String name) {
        editor.putString(NAME, name);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public String getName() {
        return pref.getString(NAME, null);
    }

    public void setMobile(String mobile) {
        editor.putString(MOBILE, mobile);
        editor.commit();
    }

    public String getMobile() {
        return pref.getString(MOBILE, null);
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}