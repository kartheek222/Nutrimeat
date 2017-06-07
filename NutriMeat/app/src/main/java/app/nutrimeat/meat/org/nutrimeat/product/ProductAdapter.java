package app.nutrimeat.meat.org.nutrimeat.product;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.nutrimeat.meat.org.nutrimeat.CommonFunctions;
import app.nutrimeat.meat.org.nutrimeat.ProductDetails.ProductDetailsActivity;
import app.nutrimeat.meat.org.nutrimeat.R;

import static app.nutrimeat.meat.org.nutrimeat.PrefManager.PREF_PRODUCT_CART;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product_Model> products;
    private int rowLayout;
    private Products context;
    ArrayList<String> isadd_to_cart = new ArrayList<>();
    ModelCart model_in_cart = new ModelCart();

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView product_image;
        TextView item_name;
        TextView item_price;

        Button addtocart;

        public ProductViewHolder(View v) {
            super(v);
            product_image = (RoundedImageView) v.findViewById(R.id.product_image);
            item_name = (TextView) v.findViewById(R.id.item_name);
            item_price = (TextView) v.findViewById(R.id.item_price);
            addtocart = (Button) v.findViewById(R.id.addtocart);
        }
    }

    public ProductAdapter(List<Product_Model> movies, int rowLayout, Products context, ArrayList<String> isadd_to_cart) {
        this.products = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        this.isadd_to_cart = isadd_to_cart;

    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ProductViewHolder(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final Product_Model product = products.get(position);
        Picasso.with(holder.product_image.getContext())
                .load("http://www.nutrimeat.in/assets/user/img/products/med/" + products.get(position).getItem_image())
                //  .resize(dp2px(220), 0)
                .into(holder.product_image);
        holder.item_name.setText(products.get(position).getItem_name());
        final List<ModelCart> listfromshared_preference = CommonFunctions.getSharedPreferenceProductList(context.getActivity(), PREF_PRODUCT_CART);

        final boolean productexist = checkifproductexist(listfromshared_preference, product.getItem_id());
        if (productexist) {
            holder.addtocart.setText("ADDED TO CART");
            holder.addtocart.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.green)));
        } else {
            holder.addtocart.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorAccent)));
        }

        holder.item_price.setText(String.valueOf(products.get(position).getItem_price()));
        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean productexist = checkifproductexist(listfromshared_preference, product.getItem_id());
                Intent intent = new Intent(context.getActivity(), ProductDetailsActivity.class);
                intent.putExtra("product", product);
                intent.putExtra("position", position);
                intent.putExtra("isadd_to_cart", isadd_to_cart);
                intent.putExtra("product_exist", productexist);
                intent.putExtra("model_in_cart", model_in_cart);

                context.startActivityForResult(intent, 1);
            }
        });

    }

    private boolean checkifproductexist(List<ModelCart> listfromshared_preference, int item_id) {
        if (listfromshared_preference != null) {
            for (int i = 0; i < listfromshared_preference.size(); i++) {
                ModelCart model = listfromshared_preference.get(i);
                if (model.getItem_id() == item_id) {
                    model_in_cart = model;
                    return true;

                }
            }
            return false;
        } else {
            return false;
        }
    }


    @Override
    public int getItemCount() {
        return products.size();
    }
}