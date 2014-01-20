package com.example.PRS;

import android.graphics.drawable.Drawable;

public class Product {

    public String title;
    public Drawable productImage;
    public String description;
    public double price;
    public boolean selected;

    public Product(String title, Drawable productImage, String description,
                   double price) {
        this.title = title;
        this.productImage = productImage;
        this.description = description;
        this.price = price;
    }

    public boolean equals(Object obj)		{
     if(this == obj)
            		return true;
        		if((obj == null) || (obj.getClass() != this.getClass()))
           				return false;
       			// object must be Test at this point
       	Product test = (Product)obj;

        return title.equals(test.title);
      }

    public int hashCode()	{
        		int hash = 7;
        			hash = 31 * hash + title.hashCode();
        			hash = 31 * hash + (null == description ? 0 : description.hashCode());
        			return hash;
      }

}

