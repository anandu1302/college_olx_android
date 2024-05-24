package com.nextgen.collegeolx.ModelClass;

public class MyProductsModelClass {

    String id;
    String pname;
    String desc;
    String image;
    String price;

    public MyProductsModelClass(String id,String pname,String desc,String image,String price) {
        this.id = id;
        this.pname = pname;
        this.desc = desc;
        this.image = image;
        this.price = price;
    }

    public String getId(){
        return id;
    }

    public String getPname(){
        return pname;
    }

    public String getDesc(){
        return desc;
    }

    public String getImage(){
        return image;
    }

    public String getPrice(){
        return price;
    }
}
