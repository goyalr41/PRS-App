package com.example.PRS;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {

    private List<Product> mProductList;
    public static LayoutInflater mInflater;
    private boolean mShowQuantity;

    public ProductAdapter(List<Product> list, LayoutInflater inflater) {
        mProductList = list;
        mInflater = inflater;
        //mShowQuantity = showQuantity;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_row_shopping, null);
            item = new ViewItem();

            item.productImageView = (ImageView) convertView
                    .findViewById(R.id.list_imageshop);

            item.productTitle = (TextView) convertView
                    .findViewById(R.id.tvCityShop);

            item.productQuantity = (TextView) convertView
                    .findViewById(R.id.currentquant);

            item.productCost = (TextView) convertView
                    .findViewById(R.id.tvTempShop);
             item.minubutton = (ImageView) convertView.findViewById(R.id.addimageView2);

            item.minubutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check to see that a valid quantity was entered
                    int quantity = Integer.parseInt(item.productQuantity.getText().toString().trim());
                    ShoppingCartHelper.setQuantity(mProductList.get(position), quantity-1);
                    /*if(ShoppingCartActivity.mProductAdapter != null) {
                        ShoppingCartActivity.mProductAdapter.notifyDataSetChanged();
                    }   */
                    // If we make it here, a valid quantity was entered

                   Product curProduct = mProductList.get(position);

                    item.productImageView.setImageDrawable(curProduct.productImage);
                    item.productTitle.setText(curProduct.title);
                    String s = "";
                    Double m  = (curProduct.price) * ShoppingCartHelper.getProductQuantity(curProduct);
                    s = s + m;
                    s.trim();
                    item.productCost.setText(s);
                    int gf = ShoppingCartHelper.getProductQuantity(curProduct);
                    String s1 = "";
                    s1 = s1 + gf;
                    s1.trim();
                    item.productQuantity.setText(s1);


                }
            });

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        Product curProduct = mProductList.get(position);

        item.productImageView.setImageDrawable(curProduct.productImage);
        item.productTitle.setText(curProduct.title);
        String s = "";
        Double m  = (curProduct.price) * ShoppingCartHelper.getProductQuantity(curProduct);
        s = s + m;
        s.trim();
        item.productCost.setText(s);
        int gf = ShoppingCartHelper.getProductQuantity(curProduct);
        String s1 = "";
        s1 = s1 + gf;
        s1.trim();
        item.productQuantity.setText(s1);

        // Show the quantity in the cart or not
        /*if (mShowQuantity) {
            item.productQuantity.setText("Quantity: "
                    + ShoppingCartHelper.getProductQuantity(curProduct));
        } else {
            // Hid the view
            item.productQuantity.setVisibility(View.GONE);
        } */

        return convertView;
    }

    private class ViewItem {
        ImageView productImageView;
        TextView productTitle;
        TextView productQuantity;
        TextView productCost;
        ImageView minubutton;
    }

}
