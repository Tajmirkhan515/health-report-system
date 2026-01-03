/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package healthreport;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author Tajmirkhan
 */
public class DataBase {
    String conString = "jdbc:mysql://localhost:3306/test_lab";  
   String username="root";
    String pasword="";
    Connection con;
     
    DataBase()
                  {
        try {
            con=(Connection) DriverManager.getConnection(conString, username, pasword);
            System.out.println("connection is successfully");
                    }
         catch (SQLException e) {
               JOptionPane.showMessageDialog(null,"please connect Internet");
                         }
                   }
     
    
    void insertDoctor(String name,String gender,String phone,String spacification,String address)
    {
     try {
         String str="insert into DOCTOR(dr_name,dr_gender,dr_phone,dr_specialization,dr_address) values ('"+name+"','"+gender+"','"+phone+"','"+spacification+"','"+address+"')";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("Update Successful");
          } 
     catch (SQLException ex) 
                           {
         System.out.println("not Updated"+ex.toString());
                           }
           }
    
    
    ResultSet reteriveDataFromDoctor(){
        ResultSet rs=null;
       try{
        String str="select * from DOCTOR";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
          }
    
    void updateDoctorTable(String id,String name,String gender,String phone,String specialization,String address){   
                try {
         String str="UPDATE DOCTOR SET dr_name='"+name+"',dr_gender='"+gender+"',dr_phone='"+phone+"',`dr_specialization`='"+specialization+"',dr_address='"+address+"' WHERE dr_id='"+id+"'";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("value inserted");
          } 
     catch (SQLException ex) 
                           {
         System.out.println("values is not inserted"+ex.toString());
                           }
         }
    
    void DeleteDoctorFromTable(String dr_id){
        try {
         String str="delete from DOCTOR where dr_id='"+dr_id+"'";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("Delte Successful");
          } 
     catch (SQLException ex) 
                           {
         System.out.println("not Deleted"+ex.toString());
                           }
    }
    
    void insertIntoMainTestTable(String name,String units,String price,String normal_range,String note){
        try {
         String str="insert into TEST(tst_name,tst_units,tst_price,tst_normal_range,note) values ('"+name+"','"+units+"','"+price+"','"+normal_range+"','"+note+"')";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("Update Successful");
          } 
     catch(SQLIntegrityConstraintViolationException x){
         JOptionPane.showMessageDialog(null,"this value is also available in Main table");
     }  catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
           }
    
    ResultSet retriveDataFromMainTest(){
         ResultSet rs=null;
       try{
        String str="select * from TEST";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
      }
    
    void updateMainTestTable(String id,String name,String units,String price,String normal_range){
               try {
         String str="UPDATE TEST SET "+Utilities.TST_NAME+"='"+name+"',"+Utilities.TST_UNITS+"='"+units+"',"+Utilities.TST_PRICE+"='"+price+"',"+Utilities.TST_NORMALRANGE+"='"+normal_range+"' WHERE "+Utilities.TST_ID+"='"+id+"'";
         String str2="UPDATE TEST SET tst_name='"+name+"',tst_price='"+price+"',tst_normal_range='"+normal_range+"' WHERE tst_id='"+id+"'";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("UpDated");
          } 
     catch (SQLException ex) 
                           {
         System.out.println("not Updated"+ex.toString());
                           }
     }
    
    void deleteFromMainTestData(String id){
           try {
         String str="delete from TEST where tst_id='"+id+"'";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("Delte Successful");
          } 
     catch (SQLException ex) 
                           {
         System.out.println("not Deleted"+ex.toString());
                           }
    }
    
    ResultSet retriveDataFromChildTestWithTheHelpOfMainTable(String tst_id){
        System.out.println(tst_id);
        ResultSet rs=null;
        try{
        String str="SELECT  a.`tdet_id`,a.`tdet_name`,a.`tdet_units`,a.`tdet_price`,a.`tdet_normal_range`, b.`tst_id`,b.`tst_name` FROM TST_DETAILS a INNER JOIN TEST b ON a.tst_id = b.tst_id where a.tst_id='"+tst_id+"'";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
        }
    
    void insertIntoChildTable(String name,String units,String price,String normalRange,String std_id){
        try {
         String str="insert into TST_DETAILS ("+Utilities.TDET_NAME+","+Utilities.TDET_UNITS+","+Utilities.TDET_PRICE+","+Utilities.TDET_NORMALRANGE+","+Utilities.TST_ID+") values ('"+name+"','"+units+"','"+price+"','"+normalRange+"','"+std_id+"')";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("Update Successful");
          } 
     catch (NullPointerException ex) 
                           {
         System.out.println("not Updated"+ex.toString());
                           }
         catch(SQLIntegrityConstraintViolationException  px){
             System.out.println(px.toString()+"  \n"+px.getMessage());
         JOptionPane.showMessageDialog(null,"this item is already available in the child table");
     }  catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void deleteFromChildTable(String id){
            try {
         String str="delete from TST_DETAILS where "+Utilities.TDET_ID+"='"+id+"'";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("Delte Successful");
          } 
      catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void updateChildTestTable(String id,String name,String units,String price,String normal_range){
         try {
         String str="UPDATE TST_DETAILS SET "+Utilities.TDET_NAME+"='"+name+"',"+Utilities.TDET_UNITS+"='"+units+"',"+Utilities.TDET_PRICE+"='"+price+"',"+Utilities.TDET_NORMALRANGE+"='"+normal_range+"' WHERE "+Utilities.TDET_ID+"='"+id+"'";
         
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("UpDated");
          } 
     catch (SQLException ex) 
                           {
JOptionPane.showMessageDialog(null,"some error occure");                           }
    }

    ResultSet namAndIdFromDoctorTable(){
        ResultSet rs=null;
        try{
        String str="SELECT dr_id,dr_name from DOCTOR";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
    }
    
    void insertIntoPatientTable(String id,String age,String name,String gender,String address){
        try {
         String str="insert into PATIENT ("+Utilities.PTN_ID+","+Utilities.PTN_NAME+","+Utilities.PTN_AGE+","+Utilities.PTN_GENDER+","+Utilities.PTN_ADDRESS+") values ('"+id+"','"+name+"','"+age+"','"+gender+"','"+address+"')";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("Inserted in Patient");
          } 
     catch (Exception ex) 
                           {
         System.out.println("not Updated"+ex.toString());
                           }
    }
    ResultSet retrivePatientID(){
      ResultSet rs=null;
        try{
        String str="SELECT * FROM PATIENT WHERE ptn_id = (SELECT MAX(ptn_id) FROM PATIENT)";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;    
    }
    
    void InsertIntoAppointmentTable(String dr_id,String ptn_id){   
         try {
         String str="insert into APPOINTMENT ("+Utilities.REPORT_DATE_TIME+","+Utilities.ANNOCED_DATE_TIME+","+Utilities.STATE+","+Utilities.DR_ID+","+Utilities.PTN_ID+") values (NOw(),NULL,'APPENDING','"+dr_id+"','"+ptn_id+"')";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("Inserted in appointment");
          } 
     catch (Exception ex) 
                           {
         System.out.println("not Updated"+ex.toString());
                           }
    }
    
    ResultSet retriveDataFromAppointmentTableOfCurrentPatient(){
         ResultSet rs=null;
        try{
        String str="SELECT a.*,d.dr_name, p.ptn_name,p.ptn_age,p.ptn_address,p.ptn_gender FROM `APPOINTMENT` a \n" +
                    "left join DOCTOR d ON a.dr_id = d.dr_id  \n" +
                    "LEFT join PATIENT P ON a.ptn_id  =  p.ptn_id\n" +
                    "WHERE a.apm_id = (select max(`apm_id`) from APPOINTMENT)";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
    }

   ResultSet getDataFromchildTableWithHelpOfForeignKey(String tst_id){
        
         ResultSet rs=null;
        try{
        String str="select `tdet_id`,`tdet_name` from TST_DETAILS where tst_id='"+tst_id+"'";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
    } 
   
   void insertIntoAppDetailsTable(String apm_id,String tst_id,String result,String tdet_id){
       
       try {
         String str="insert into App_DETAIL ("+Utilities.APM_ID+","+Utilities.TST_ID+","+Utilities.RESULT+","+Utilities.TDET_ID+") values ('"+apm_id+"','"+tst_id+"','"+result+"','"+tdet_id+"')";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
         System.out.println("Update Successful");
          } 
     catch (NullPointerException ex) 
                           {
         System.out.println("not Updated"+ex.toString());
                           }
         catch(SQLIntegrityConstraintViolationException  px){
         JOptionPane.showMessageDialog(null,"this item is already available in the child table");
     }  catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   
      ResultSet retriveFromAppoitmentOfPendingPatient(String date,String apending){
        
         ResultSet rs=null;
        try{
        String str="select a.`apm_id`,a.`report_date_time`,a.`dr_id`,a.`ptn_id`,d.dr_name,p.ptn_name,ptn_age, p.ptn_gender from APPOINTMENT a left join DOCTOR d ON a.dr_id = d.dr_id LEFT join PATIENT P ON a.ptn_id  =  p.ptn_id WHERE state='"+apending+"' and CAST(report_date_time AS DATE) >='"+date+"'";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
    } 
      ResultSet retriveFromAppoitmentOfOldPatient(String date){
        
         ResultSet rs=null;
        try{
        String str="select a.`apm_id`,a.`report_date_time`,a.`dr_id`,a.`ptn_id`,d.dr_name,p.ptn_name,ptn_age, p.ptn_gender from APPOINTMENT a left join DOCTOR d ON a.dr_id = d.dr_id LEFT join PATIENT P ON a.ptn_id  =  p.ptn_id WHERE (state='APPENDING' or state='DONE') and CAST(report_date_time AS DATE) <'"+date+"'";
        Statement s=con.prepareStatement(str);
        rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
    } 
      
      
       ResultSet retriveFromAppoitment_Details(String apm_id){
         ResultSet rs=null;
        try{
        String str="select * from APP_DETAIL where apm_id='"+apm_id+"'";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
    } 
       
         String retriveResult(String apm_id,String tst_id,String tstde_id){
         String str2=null;
        try{        
        String str="select result from APP_DETAIL where (apm_id='"+apm_id+"' and tst_id='"+tst_id+"' and tdet_id='"+tstde_id+"')";
  
        Statement s=con.prepareStatement(str);
          ResultSet rs= s.executeQuery(str);
          rs.next();
          str2=rs.getString(Utilities.RESULT);
       }catch(Exception e){
            System.out.println(e.toString());
       }
        return str2;
    } 
            ResultSet retriveFromTEST(String tst_id){
         ResultSet rs=null;
        try{
        String str="select `tst_id`,UPPER(tst_name) as tst_name,`tst_units`,`tst_price`,`tst_normal_range`,`note` from TEST where tst_id='"+tst_id+"';";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
    } 
            
         ResultSet retriveFromTEST_DETAILS(String tst_id){
         ResultSet rs=null;
        try{
        String str="select `tdet_id`,tdet_name,`tdet_units`,`tdet_price`,`tdet_normal_range`,`tst_id` from TST_DETAILS WHERE tst_id='"+tst_id+"';";
        Statement s=con.prepareStatement(str);
        rs= s.executeQuery(str);
       }catch(Exception e){
           System.out.println(" retriving "+e.getMessage());
       }
        return rs;
    } 
               
             void updateApp_Details_toSetResult(String apm_id,String test_id,String tdet_id,String result){
                   try{
        String str="update  APP_DETAIL set result='"+result+"' where (apm_id='"+apm_id+"' and tst_id='"+test_id+"' and tdet_id='"+tdet_id+"')";
          Statement s=con.prepareStatement(str);
           s.execute(str);
           System.out.println( "update" + "  " +apm_id+"    "+test_id+"   "+tdet_id+"   "+result);
       }catch(Exception e){
    System.out.println(e.toString());          
       }
             }
             
             
           void upDateAppointmentTableChangeAppendingToDone(String amp_id,String value){
                            try{
        String str="update   APPOINTMENT set state='"+value+"' where apm_id='"+amp_id+"' ";
          Statement s=con.prepareStatement(str);
           s.execute(str);
       }catch(Exception e){
    System.out.println(e.toString());          
       }
             }

        void deleteFromAppointments(String apm_id,String ptn_id){
             try {
         String str="delete from APPOINTMENT where apm_id='"+apm_id+"'";
         Statement s;
         s=con.prepareStatement(str);
         s.execute(str);
          
         String str2="delete from APP_DETAIL where apm_id='"+apm_id+"'";
         Statement s2;
         s2=con.prepareStatement(str2);
         s2.execute(str2);
         
         String str3="delete from  PATIENT where ptn_id='"+ptn_id+"'";
         Statement s3;
         s3=con.prepareStatement(str3);
         s3.execute(str3);        
          } 
     catch (SQLException ex) 
                           {
         System.out.println("not Deleted"+ex.toString());
                           }
        }
        
            ResultSet retrivePerDoctoreCost(String date,String dr_id){
         ResultSet rs=null;
        try{
        String str="SELECT a.apm_id,`dr_id`,t.tst_id,tst_price,ptn_id FROM APPOINTMENT a left join APP_DETAIL d on a.`apm_id`  = d.`apm_id` left join TEST t on d.tst_id = t.tst_id where a.`dr_id` = '"+dr_id+"' and CAST(report_date_time AS DATE) = '"+date+"'";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
    } 
            
             ResultSet ReteriveDataFromAppointmentTable(){
        ResultSet rs=null;
        try{
        String str="SELECT  CAST( report_date_time AS DATE ) as report_date_time from `APPOINTMENT`  ORDER BY report_date_time asc";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
    }
             
         ResultSet retriveDataBettwenTwoDate(String start_date,String end_date){
         ResultSet rs=null;
        try{
        String str="SELECT a.apm_id,`dr_id`,t.tst_id,tst_price,ptn_id FROM APPOINTMENT a left join APP_DETAIL d on a.`apm_id`  = d.`apm_id` left join TEST t on d.tst_id = t.tst_id where  CAST(report_date_time AS DATE) >= '"+start_date+"' and CAST(report_date_time AS DATE) <='"+end_date+"'";
          Statement s=con.prepareStatement(str);
           rs= s.executeQuery(str);
       }catch(Exception e){
           
       }
        return rs;
    } 
        
} 
    

