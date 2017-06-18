package app.nutrimeat.meat.org.nutrimeat.Checkout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.payUMoney.sdk.PayUmoneySdkInitilizer;
import com.payUMoney.sdk.SdkConstants;
import com.sasidhar.smaps.payumoney.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.nutrimeat.meat.org.nutrimeat.CommonFunctions;
import app.nutrimeat.meat.org.nutrimeat.PrefManager;
import app.nutrimeat.meat.org.nutrimeat.R;
import app.nutrimeat.meat.org.nutrimeat.Textview.p_MyCustomTextView_mbold;
import app.nutrimeat.meat.org.nutrimeat.Textview.p_MyCustomTextView_regular;
import app.nutrimeat.meat.org.nutrimeat.payment.PaymentActivity;
import app.nutrimeat.meat.org.nutrimeat.product.ModelCart;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static app.nutrimeat.meat.org.nutrimeat.PrefManager.PREF_PRODUCT_CART;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = CheckoutActivity.class.getSimpleName();
    // ArrayList<ModelCart> ListofProdcuts = new ArrayList<>();
    RecyclerView Checkout_rv;

    ArrayList<Double> price = new ArrayList<>();
    //public double price = 0;
    p_MyCustomTextView_regular subtotal;
    private Button btnCheckout;
    private CheckoutAdapter checkoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subtotal = (p_MyCustomTextView_regular) findViewById(R.id.subtotal);
        p_MyCustomTextView_mbold empty_view = (p_MyCustomTextView_mbold) findViewById(R.id.empty_view);
        Checkout_rv = (RecyclerView) findViewById(R.id.Checkout_rv);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(this);
        final List<ModelCart> cart_itens = CommonFunctions.getSharedPreferenceProductList(CheckoutActivity.this, PREF_PRODUCT_CART);
        if (cart_itens != null) {
            ArrayList<ModelCart> listOfStrings = new ArrayList<>(cart_itens.size());
            listOfStrings.addAll(cart_itens);
            //ListofProdcuts = (ArrayList<ModelCart>) getIntent().getBundleExtra("bundle").getSerializable("isadd_to_cart");
            Checkout_rv.setLayoutManager(new LinearLayoutManager(this));
            if (listOfStrings.size() > 0) {
                empty_view.setVisibility(View.GONE);
                Checkout_rv.setVisibility(View.VISIBLE);
                checkoutAdapter = new CheckoutAdapter(listOfStrings, R.layout.checkout_item, CheckoutActivity.this);
                Checkout_rv.setAdapter(checkoutAdapter);
            } else {
                empty_view.setVisibility(View.VISIBLE);
                Checkout_rv.setVisibility(View.GONE);
            }
        } else {
            empty_view.setVisibility(View.VISIBLE);
            Checkout_rv.setVisibility(View.GONE);
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("product", "product");
        intent.putExtra("position", "two");
        setResult(Activity.RESULT_OK, intent);
        finish();
        //  super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckout:
                PrefManager manager = new PrefManager(getApplicationContext());
                if (manager.canCheckout()) {
                    final List<ModelCart> cart_itens = CommonFunctions.getSharedPreferenceProductList(CheckoutActivity.this, PREF_PRODUCT_CART);
                    if (cart_itens != null && cart_itens.size() > 0) {
                        Intent intent=new Intent(this, PaymentActivity.class);
                        startActivity(intent);
//                        navigateUserToPayment(cart_itens);
                    } else {
                        Toast.makeText(getApplicationContext(), "No items added to card", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "We cannot deliver order to your location", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void navigateUserToPayment(List<ModelCart> cart_itens) {
        PrefManager manager = new PrefManager(this);
        String tnxId = "0nf7" + System.currentTimeMillis();
        PayUmoneySdkInitilizer.PaymentParam.Builder builder = new
                PayUmoneySdkInitilizer.PaymentParam.Builder().
                setMerchantId("m3pWGL")
                .setKey("m3pWGL")
                .setIsDebug(true)                      // for Live mode -
                .setIsDebug(false)
                .setAmount(checkoutAdapter.getSub_total())
                .setTnxId(tnxId)
                .setPhone("8882434664")
                .setProductName("product_name")
                .setFirstName("piyush")
                .setEmail("test@payu.in")
                .setsUrl("http://www.nutrimeat.in/store/checkout_complete")
                .setfUrl("http://www.nutrimeat.in/store/checkout_complete")
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("");

        // declare paymentParam object
        PayUmoneySdkInitilizer.PaymentParam paymentParam = builder.build();
        HashMap<String, String> params = new HashMap<>();
        params.put("key", "m3pWGL");
        params.put("txnid", tnxId);
        params.put("amount", String.valueOf(checkoutAdapter.getSub_total()));
        params.put("productinfo", "Nutri Mean");
        params.put("firstname", manager.getName());
        params.put("email", manager.getEmail());
        String hash = Utils.generateHash(params, "RbHCQkbb");
//set the hash


        String serverCalculatedHash = hashCal("SHA-512", hash);
        paymentParam.setMerchantHash(hash);

        // Invoke the following function to open the checkout page.
        PayUmoneySdkInitilizer.startPaymentActivityForResult(CheckoutActivity.this, paymentParam);

    }

    public static String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException nsae) {
        }
        return hexString.toString();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayUmoneySdkInitilizer.PAYU_SDK_PAYMENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.i(TAG, "Success - Payment ID : " +
                        data.getStringExtra(SdkConstants.PAYMENT_ID));
                String
                        paymentId =
                        data.getStringExtra(SdkConstants.PAYMENT_ID);
            } else if (resultCode == RESULT_CANCELED) {
                Log.i(TAG, "cancelled");
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_FAILED) {
                Log.i(TAG, "failure");
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_BACK) {
                Log.i(TAG, "User returned without login");
            }
        }
    }
}
