/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.ArrayList;
import java.util.List;
import pojo_classes.Child_SubReport;

/**
 *
 * @author Tajmirkhan
 */
public class Personbean_getter {
     String id,main_test,units,normalRang,result,note,price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
     List<Child_SubReport> child_surReport_list;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain_test() {
        return main_test;
    }

    public void setMain_test(String main_test) {
        this.main_test = main_test;
    }

    public List<Child_SubReport> getChild_surReport_list() {
        return child_surReport_list;
    }

    public void setChild_surReport_list(List<Child_SubReport> child_surReport_list) {
        this.child_surReport_list = child_surReport_list;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getNormalRang() {
        return normalRang;
    }

    public void setNormalRang(String normalRang) {
        this.normalRang = normalRang;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
     
    
}
