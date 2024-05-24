package com.nextgen.collegeolx.ModelClass;

public class CartModelClass {

    String id;
    String pname;
    String image;
    String price;

    public CartModelClass(String id,String pname,String image,String price) {
        this.id = id;
        this.pname = pname;
        this.image = image;
        this.price = price;
    }

    public String getId(){
        return id;
    }

    public String getPname(){
        return pname;
    }

    public String getImage(){
        return image;
    }

    public String getPrice(){
        return price;
    }
}
