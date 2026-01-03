/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javabeanset;

import bean.Personbean_getter;
import java.util.ArrayList;
import java.util.List;
import pojo_classes.Child_SubReport;

/**
 *
 * @author Tajmirkhan
 */
public class TestFactory {
   
   public static ArrayList<Personbean_getter> dataBeanList;
   
           
        public void initilaizedTheJasperData(ArrayList<Personbean_getter> dataBeanList){
            this.dataBeanList=dataBeanList;
        }         
    public static ArrayList<Personbean_getter> generatCollection(){
        

//        List<Child_SubReport> child_sub_lis=new ArrayList<Child_SubReport>();
//      
//                Personbean_getter   obj=new Personbean_getter();
//        
////              Child_SubReport child_sub=new Child_SubReport();
////              child_sub.setId("1");
////              child_sub.setChild_test("child_tst");
////              child_sub.setUnits("units%%");
////              child_sub.setNormalRang("1000");
////              child_sub.setResult("23");
////              child_sub_lis.add(child_sub);
//                      
//              obj.setId("1");
//              obj.setMain_test("Main_tst");
//              obj.setUnits("units%%");
//              obj.setNormalRang("1000");
//              obj.setResult("23");
//              
//              //obj.setNote("NOTE:   this test is very dangerus pleas");
//              
//              
//              System.out.println("static method call");
//              dataBeanList.add(obj);
//             
//              Personbean_getter   obj2=new Personbean_getter();
//              for(int i=0;i<=3;i++){
//              Child_SubReport child_sub1=new Child_SubReport();
//              child_sub1.setId("33");
//              child_sub1.setChild_test("child_tst this is main test ttsst sdfk tajmirkhan");
//              child_sub1.setUnits("units%%");
//              child_sub1.setNormalRang("1000");
//              child_sub1.setResult("23");
//              child_sub_lis.add(child_sub1);
//              }
//              
//              obj2.setId("2");
//              obj2.setMain_test("MainMain");
//              obj2.setUnits("Unit2");
//              obj2.setNormalRang("2000");
//              obj2.setResult("222");
//              
//              obj2.setNote("2this test is very dangerus pleas 2this test is very dangerus pleas 2555555555this test is very dangerus pleas  2555555555this test is very dangerus pleas");
//              obj2.setChild_surReport_list(child_sub_lis);
//              
//              System.out.println("static method call");
             // dataBeanList.add(obj2);
       
     return dataBeanList;
    }
    
   
}
