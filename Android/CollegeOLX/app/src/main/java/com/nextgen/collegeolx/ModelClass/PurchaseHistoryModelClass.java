package com.nextgen.collegeolx.ModelClass;

public class PurchaseHistoryModelClass {


    String id;
    String name;
    String image;
    String price;
    String orderid;

    public PurchaseHistoryModelClass(String id,String name,String image,String price,String orderid) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.orderid = orderid;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getImage(){
        return image;
    }

    public String getPrice(){
        return price;
    }

    public String getOrderid(){
        return orderid;
    }
}
