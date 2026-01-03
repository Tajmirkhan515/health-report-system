/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package healthreport;

import Toast.AndroidLikeToast;
import bean.Personbean_getter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javabeanset.TestFactory;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.PropertyConfigurator;
import pojo_classes.Child_SubReport;

/**
 *
 * @author Tajmirkhan
 */
public class Patient_Recored extends javax.swing.JInternalFrame {
DefaultTableModel model_pnd,model_tests;//pending
DefaultTableModel model_done,model_done_tests;//done
DefaultTableModel model_old_record,model_old_tests;//old tables
DataBase db;
String currentTimeDate;
String current_date;
ArrayList<AllPationAppointment_GetterS> arraylist;  //its store all information about patient

ArrayList<Personbean_getter> dataBeanList;
Map param;

boolean PrintWithPrice=false;

String apm_id_for_update;
Preferences preferences;          // its use for printer 
    /**
     * Creates new form Patient_Recored
     */
    public Patient_Recored() {
        initComponents();
        db=new DataBase();
        arraylist=new ArrayList();
        getCurrentTimeAndDate();
        dataBeanList=new ArrayList<Personbean_getter>();
        
        search_pending_tf.setUI(new JTextFieldHintUI("Search....", Color.white));
        search_test_tf.setUI(new JTextFieldHintUI("Search....", Color.white));
        
        search_done_tf.setUI(new JTextFieldHintUI("Search....", Color.white));
        search_test_done_tf.setUI(new JTextFieldHintUI("Search....", Color.white));
        
         search_old_tf.setUI(new JTextFieldHintUI("Search....", Color.white));
        search_old_test_tf.setUI(new JTextFieldHintUI("Search....", Color.white));
        
        model_pnd =(DefaultTableModel)pnd_recored_table.getModel();
        pnd_recored_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        model_tests=(DefaultTableModel)tests_table.getModel();
        tests_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        model_done=(DefaultTableModel)done_recored_table.getModel();
        done_recored_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        model_done_tests=(DefaultTableModel)tests_done_table.getModel();
        tests_done_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        model_old_record=(DefaultTableModel)old_recored_table.getModel();
        old_recored_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        model_old_tests=(DefaultTableModel)old_tests_table.getModel();
        old_tests_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        addDataIntoPndPatientTable(model_pnd,0);  // by default set the first tab value
       param=new HashMap();
       
       preferences = Preferences.userRoot().node("java-buddy");
       hideTheIDsColumn(0);                      // this function hide the column of test_id and test_detail_id
    
    getCheckPriceMethodAndCheckboxIsChecked(0);
    }
    
      void hideTheIDsColumn(int check){
          // hide the IDs
          if(check==0){
         tests_table.getColumnModel().getColumn(6).setWidth(0);
         tests_table.getColumnModel().getColumn(6).setMinWidth(0);
         tests_table.getColumnModel().getColumn(6).setMaxWidth(0);  
          // hide the test Details ids
         tests_table.getColumnModel().getColumn(7).setWidth(0);
         tests_table.getColumnModel().getColumn(7).setMinWidth(0);
         tests_table.getColumnModel().getColumn(7).setMaxWidth(0); 
         tests_table.getColumnModel().getColumn(8).setMaxWidth(0);
          }
          else if(check==1){   
         tests_done_table.getColumnModel().getColumn(6).setWidth(0);
         tests_done_table.getColumnModel().getColumn(6).setMinWidth(0);
         tests_done_table.getColumnModel().getColumn(6).setMaxWidth(0);  
           // hide the test Details ids
         tests_done_table.getColumnModel().getColumn(7).setWidth(0);
         tests_done_table.getColumnModel().getColumn(7).setMinWidth(0);
         tests_done_table.getColumnModel().getColumn(7).setMaxWidth(0); 
         tests_done_table.getColumnModel().getColumn(8).setMaxWidth(0);
            }else if(check==2){
                  old_tests_table.getColumnModel().getColumn(6).setWidth(0);
         old_tests_table.getColumnModel().getColumn(6).setMinWidth(0);
         old_tests_table.getColumnModel().getColumn(6).setMaxWidth(0);  
           // hide the test Details ids
         old_tests_table.getColumnModel().getColumn(7).setWidth(0);
         old_tests_table.getColumnModel().getColumn(7).setMinWidth(0);
         old_tests_table.getColumnModel().getColumn(7).setMaxWidth(0);
         old_tests_table.getColumnModel().getColumn(8).setMaxWidth(0);
            }
      }
        void getCurrentTimeAndDate(){
            //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE,0);
            Date date=cal.getTime();
            
            current_date=dateFormat.format(date); 
            currentTimeDate=dateFormat.format(cal.getTime());
            
                  }
        
        void addDataIntoPndPatientTable(DefaultTableModel model_pnd,int option){
             model_pnd.setRowCount(0);  // first show the empty data in a table
             
             ResultSet rs=null;
             if(option==0){
                    arraylist.clear();   // and then clear the arraylis where stored information of student
                 rs = db.retriveFromAppoitmentOfPendingPatient(current_date,"APPENDING");
                  }else if(option==1){
                    arraylist.clear();
                    rs = db.retriveFromAppoitmentOfPendingPatient(current_date,"DONE");
                    System.out.println("done method is called");
                  }else if(option==2){
                    arraylist.clear();
                    rs=db.retriveFromAppoitmentOfOldPatient(current_date);
                  }
                  
    try {
        while(rs.next()){
            AllPationAppointment_GetterS data=new AllPationAppointment_GetterS();
       
            data.setApm_id(rs.getString(Utilities.APM_ID));
            data.setReport_date_time(rs.getString(Utilities.REPORT_DATE_TIME));
            data.setDr_id(rs.getString(Utilities.DR_ID));
            data.setPtn_id(rs.getString(Utilities.PTN_ID));
            data.setDr_name(rs.getString(Utilities.DR_NAME));
            System.out.println("dr Name  "+rs.getString(Utilities.APM_ID));
            data.setPtn_name(rs.getString(Utilities.PTN_NAME));
            data.setPtn_age(rs.getString(Utilities.PTN_AGE));
            data.setPtn_gender(rs.getString(Utilities.PTN_GENDER));
            if(option==2){
                model_pnd.addRow(new Object[]{rs.getString(Utilities.APM_ID),rs.getString(Utilities.PTN_NAME),false});
                            arraylist.add(data);
            }else{ 
                model_pnd.addRow(new Object[]{rs.getString(Utilities.APM_ID),rs.getString(Utilities.PTN_NAME)});
                       arraylist.add(data);
            }
            
        }
    } catch (SQLException ex) {
        System.out.println("pleas connect Internet");
    }
             
         }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tests_table = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        paddingCheckbox = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        search_test_tf = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnd_recored_table = new javax.swing.JTable();
        search_pending_tf = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tests_done_table = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        search_test_done_tf = new javax.swing.JTextField();
        DoneCheckbox = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        done_recored_table = new javax.swing.JTable();
        search_done_tf = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        search_old_tf = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        old_recored_table = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        old_tests_table = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        search_old_test_tf = new javax.swing.JTextField();
        OldCheckBox = new javax.swing.JCheckBox();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        setBackground(new java.awt.Color(173, 31, 31));
        setResizable(true);
        setMaximumSize(new java.awt.Dimension(1200, 1000));
        setMinimumSize(new java.awt.Dimension(50, 30));
        setPreferredSize(new java.awt.Dimension(700, 450));

        jTabbedPane1.setBackground(new java.awt.Color(173, 31, 31));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(700, 400));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(173, 31, 31));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));

        jPanel4.setBackground(new java.awt.Color(173, 31, 31));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(173, 31, 31))); // NOI18N

        tests_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Units", "Normal Range", "Result", "Price", "TST_ID", "TSTD_ID", "Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tests_table);
        if (tests_table.getColumnModel().getColumnCount() > 0) {
            tests_table.getColumnModel().getColumn(0).setMinWidth(60);
            tests_table.getColumnModel().getColumn(0).setPreferredWidth(60);
            tests_table.getColumnModel().getColumn(0).setMaxWidth(70);
            tests_table.getColumnModel().getColumn(4).setMinWidth(60);
            tests_table.getColumnModel().getColumn(4).setPreferredWidth(60);
            tests_table.getColumnModel().getColumn(4).setMaxWidth(70);
            tests_table.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        jButton2.setText("Cancel Printer Selection");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        paddingCheckbox.setForeground(new java.awt.Color(255, 255, 255));
        paddingCheckbox.setText("Print With Price");
        paddingCheckbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paddingCheckboxMouseClicked(evt);
            }
        });
        paddingCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paddingCheckboxActionPerformed(evt);
            }
        });

        jButton3.setText("Pdf");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        search_test_tf.setBackground(new java.awt.Color(109, 12, 12));
        search_test_tf.setForeground(new java.awt.Color(255, 255, 255));
        search_test_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_test_tfActionPerformed(evt);
            }
        });
        search_test_tf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_test_tfKeyReleased(evt);
            }
        });

        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(search_test_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(paddingCheckbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(54, 54, 54)
                        .addComponent(jButton1)
                        .addGap(64, 64, 64))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(search_test_tf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(paddingCheckbox))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(173, 31, 31));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(173, 31, 31))); // NOI18N

        pnd_recored_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Patient ID", "Name"
            }
        ));
        pnd_recored_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnd_recored_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(pnd_recored_table);
        if (pnd_recored_table.getColumnModel().getColumnCount() > 0) {
            pnd_recored_table.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        search_pending_tf.setBackground(new java.awt.Color(109, 12, 12));
        search_pending_tf.setForeground(new java.awt.Color(255, 255, 255));
        search_pending_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_pending_tfActionPerformed(evt);
            }
        });
        search_pending_tf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_pending_tfKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(search_pending_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(search_pending_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pending Recored", jPanel1);

        jPanel2.setBackground(new java.awt.Color(173, 31, 31));
        jPanel2.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jPanel2.setPreferredSize(new java.awt.Dimension(700, 400));

        jPanel6.setBackground(new java.awt.Color(173, 31, 31));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(173, 31, 31))); // NOI18N

        jButton5.setText("Pdf");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        tests_done_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Units", "Normal Range", "Result", "Price", "TST_ID", "TSTD_ID", "Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tests_done_table);
        if (tests_done_table.getColumnModel().getColumnCount() > 0) {
            tests_done_table.getColumnModel().getColumn(0).setMinWidth(60);
            tests_done_table.getColumnModel().getColumn(0).setPreferredWidth(60);
            tests_done_table.getColumnModel().getColumn(0).setMaxWidth(70);
            tests_done_table.getColumnModel().getColumn(4).setMinWidth(60);
            tests_done_table.getColumnModel().getColumn(4).setPreferredWidth(60);
            tests_done_table.getColumnModel().getColumn(4).setMaxWidth(70);
            tests_done_table.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        jButton6.setText("Print");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton4.setText("Cancel printer selection");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        search_test_done_tf.setBackground(new java.awt.Color(109, 12, 12));
        search_test_done_tf.setForeground(new java.awt.Color(255, 255, 255));
        search_test_done_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_test_done_tfActionPerformed(evt);
            }
        });
        search_test_done_tf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_test_done_tfKeyReleased(evt);
            }
        });

        DoneCheckbox.setForeground(new java.awt.Color(255, 255, 255));
        DoneCheckbox.setText("Print With Price");
        DoneCheckbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DoneCheckboxMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addComponent(search_test_done_tf)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(DoneCheckbox)
                        .addGap(55, 55, 55)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addGap(0, 87, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search_test_done_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(DoneCheckbox))
                .addGap(5, 5, 5))
        );

        jPanel7.setBackground(new java.awt.Color(173, 31, 31));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(173, 31, 31))); // NOI18N

        done_recored_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Patient ID", "Name"
            }
        ));
        done_recored_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                done_recored_tableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(done_recored_table);
        if (done_recored_table.getColumnModel().getColumnCount() > 0) {
            done_recored_table.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        search_done_tf.setBackground(new java.awt.Color(109, 12, 12));
        search_done_tf.setForeground(new java.awt.Color(255, 255, 255));
        search_done_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_done_tfActionPerformed(evt);
            }
        });
        search_done_tf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_done_tfKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(search_done_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(search_done_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Done Recored", jPanel2);

        jPanel3.setBackground(new java.awt.Color(173, 31, 31));
        jPanel3.setMaximumSize(new java.awt.Dimension(1200, 700));
        jPanel3.setPreferredSize(new java.awt.Dimension(700, 500));

        jPanel8.setBackground(new java.awt.Color(173, 31, 31));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(173, 31, 31))); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(238, 455));

        search_old_tf.setBackground(new java.awt.Color(109, 12, 12));
        search_old_tf.setForeground(new java.awt.Color(255, 255, 255));
        search_old_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_old_tfActionPerformed(evt);
            }
        });
        search_old_tf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_old_tfKeyReleased(evt);
            }
        });

        old_recored_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient ID", "Name", "select"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        old_recored_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                old_recored_tableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(old_recored_table);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(search_old_tf, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(search_old_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(173, 31, 31));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(173, 31, 31))); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(700, 400));

        old_tests_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Units", "Normal Range", "Result", "Price", "TST_ID", "TSTD_ID", "Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(old_tests_table);
        if (old_tests_table.getColumnModel().getColumnCount() > 0) {
            old_tests_table.getColumnModel().getColumn(0).setMinWidth(60);
            old_tests_table.getColumnModel().getColumn(0).setPreferredWidth(60);
            old_tests_table.getColumnModel().getColumn(0).setMaxWidth(70);
            old_tests_table.getColumnModel().getColumn(4).setMinWidth(60);
            old_tests_table.getColumnModel().getColumn(4).setPreferredWidth(60);
            old_tests_table.getColumnModel().getColumn(4).setMaxWidth(70);
            old_tests_table.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        jButton7.setText("Cancel printer selection");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        search_old_test_tf.setBackground(new java.awt.Color(109, 12, 12));
        search_old_test_tf.setForeground(new java.awt.Color(255, 255, 255));
        search_old_test_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_old_test_tfActionPerformed(evt);
            }
        });
        search_old_test_tf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_old_test_tfKeyReleased(evt);
            }
        });

        OldCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        OldCheckBox.setText("Print With Price");
        OldCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OldCheckBoxMouseClicked(evt);
            }
        });

        jButton8.setText("Pdf");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Print");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Delete");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jButton10)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7)
                        .addGap(18, 18, 18)
                        .addComponent(OldCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(jButton8)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9))
                    .addComponent(search_old_test_tf)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search_old_test_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(OldCheckBox)
                    .addComponent(jButton10))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Old Recored", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void search_pending_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_pending_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_pending_tfActionPerformed

    private void search_pending_tfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_pending_tfKeyReleased
           // TODO add your handling code here:
        filterTable(search_pending_tf.getText().toLowerCase());
    }//GEN-LAST:event_search_pending_tfKeyReleased
 int i;
    private void pnd_recored_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnd_recored_tableMouseClicked
       
       int numbering=1;                    
       model_tests.setRowCount(0);
       i=pnd_recored_table.getSelectedRow();
//       param.put("name","tajmirkhan");
//       param.put("age","23");
//       param.put("gender","male");
//       param.put("report_date_time","2/23/2018");
//       param.put("dr_name","DR romi");
//       param.put("Patient_ID","12333");
       
       param.put("name",arraylist.get(i).ptn_name);
       param.put("age",arraylist.get(i).ptn_age);
       param.put("gender",arraylist.get(i).ptn_gender);
       param.put("report_date_time",arraylist.get(i).report_date_time);
       param.put("dr_name",arraylist.get(i).dr_name);
       param.put("Patient_ID", arraylist.get(i).apm_id);
      
       apm_id_for_update=arraylist.get(i).apm_id;
       ResultSet rs=db.retriveFromAppoitment_Details(arraylist.get(i).apm_id);
        
    try {
        Set<String> hash=new HashSet<>();
         String result;
         
        while(rs.next()){
            if(hash.add(rs.getString(Utilities.TST_ID))){
                
                ResultSet rstst=db.retriveFromTEST(rs.getString(Utilities.TST_ID));
                   while(rstst.next()){
                       if(rstst.getString(Utilities.TST_NORMALRANGE).equals("") && rstst.getString(Utilities.TST_UNITS).equals("")){
                                   result="";
                                         }else{
                                      result = db.retriveResult(apm_id_for_update,rs.getString(Utilities.TST_ID),rs.getString(Utilities.TDET_ID));
                                    }
                            System.out.println("normal range  "+rstst.getString(Utilities.TST_NORMALRANGE));
                       model_tests.addRow(new Object[]{Integer.toString(numbering),rstst.getString(Utilities.TST_NAME),
                       rstst.getString(Utilities.TST_UNITS),rstst.getString(Utilities.TST_NORMALRANGE),
                       result,rstst.getString(Utilities.TST_PRICE),rstst.getString(Utilities.TST_ID),"",rstst.getString(Utilities.TST_NOTE)});
                       
                        numbering++;
                          ResultSet rststd=db.retriveFromTEST_DETAILS(rstst.getString(Utilities.TST_ID));
                            {
                                if(rststd.equals(0)){
                                System.out.println("value is null");
                            }
                                try{
                             while(rststd.next()){
                                      result = db.retriveResult(apm_id_for_update,rs.getString(Utilities.TST_ID),rststd.getString(Utilities.TDET_ID));
                                   // tests_table.setFont(new Font("Serif", Font.PLAIN, 12));
                                    model_tests.addRow(new Object[]{"\u2022",rststd.getString(Utilities.TDET_NAME),
                                     ""+rststd.getString(Utilities.TDET_UNITS),""+rststd.getString(Utilities.TDET_NORMALRANGE),
                                   result+"",""+rststd.getString(Utilities.TDET_PRICE),"",rststd.getString(Utilities.TDET_ID)});
                             }}catch(Exception e){
                                 System.out.println(e.toString());
                                  }
                             }
                   }
            }
        }
      // TODO add your handling code here:
    } catch (SQLException ex) {
        Logger.getLogger(Patient_Recored.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_pnd_recored_tableMouseClicked
String printer_Name;

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
             
            
              printer_Name= preferences.get(Utilities.PRINTER,"");
             String prefBoolean = preferences.get(Utilities.CHECK_BOX,"fasle");
             if(prefBoolean.equals("fasle")){
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            String[] printerlist=new String[20];
              int i=0;
    for (PrintService printer : printServices)
    {
        printerlist[i]=printer.getName();
           i++;
    }      
     JCheckBox checkbox = new JCheckBox("Use the selected printer always");

     JComboBox petList = new JComboBox(printerlist);

      final JComponent[] inputs = new JComponent[] {
            new JLabel("Printers"),
            petList,
            new JLabel(""),
            checkbox, 
             };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Please Select Printer", JOptionPane.PLAIN_MESSAGE);
         if (result == JOptionPane.OK_OPTION) {
         printer_Name = petList.getSelectedItem().toString();
         System.out.println("without checkbox "+printer_Name);
         printPlaning(0);
                     if (checkbox.isSelected()) {
                            preferences.put(Utilities.PRINTER, petList.getSelectedItem().toString());
                            preferences.put(Utilities.CHECK_BOX,"true");
                                } else {
                            }
              
         } else {
                 JOptionPane.showMessageDialog(null,"Printer Muste be selected");
               }
    
       }//pref booleans braces
             else{
                 printPlaning(0);
                 System.out.println(printer_Name);
             }
    }//GEN-LAST:event_jButton1ActionPerformed
 
           public void getDataFromTableModel(DefaultTableModel model){
               dataBeanList.clear();
               Personbean_getter   obj = null;
                 for(int i=0;i<model.getRowCount();i++){
                     
                      obj=new Personbean_getter();
                      
                      obj.setId(model.getValueAt(i,0).toString());
                      obj.setMain_test(model.getValueAt(i,1).toString());
                      obj.setUnits(model.getValueAt(i, 2).toString());
                      obj.setNormalRang(model.getValueAt(i,3).toString());
                      obj.setResult(model.getValueAt(i,4).toString());
                      obj.setPrice(model.getValueAt(i,5).toString());
                      obj.setNote(model.getValueAt(i,8).toString());
                      System.out.println("id m "+model.getValueAt(i, 0).toString());
                      String id=" ";
                      try{
                     id=model.getValueAt(i+1,0).toString();
                     System.out.println("id"+id);
                      }catch(Exception e){
                          id="ooo";
                      }
                if(id.equals("•")){
                 List<Child_SubReport> child_sub_lis=new ArrayList<Child_SubReport>();
                    for(int j=i+1;j<model.getRowCount();j++){
                       String idd=model.getValueAt(j,0).toString();
                       if(idd.equals("•")){
               Child_SubReport child_sub1=new Child_SubReport();
              //child_sub1.setId();
               
              child_sub1.setChild_test(model.getValueAt(j, 1).toString());
              child_sub1.setUnits(model.getValueAt(j, 2).toString());
              child_sub1.setNormalRang(model.getValueAt(j,3).toString());
              child_sub1.setResult(model.getValueAt(j,4).toString());
              child_sub_lis.add(child_sub1);
                     i=j;
                       }else{
                           //obj.setChild_surReport_list(child_sub_lis);
                           break;
                       }                                  
                    }
               obj.setChild_surReport_list(child_sub_lis);
                }
                                   
                     
                     // obj.setNote(model.getValueAt(i, 8).toString());
                     
//                      System.out.print("name  "+model.getValueAt(i, 1)+"   ");
//                     System.out.print("normal range  "+model.getValueAt(i, 2)+"   ");
//                     System.out.print("this nnnn   "+model.getValueAt(i, 8)+"   ");
//                     System.out.println("not kk  "+model.getValueAt(i, 7)+"   ");
                     
                     
                    dataBeanList.add(obj); 
                 }
                 
            TestFactory factory_obj=new TestFactory();
            factory_obj.initilaizedTheJasperData(dataBeanList);
               }
    
    void printPlaning(int check){
     ToastMessage("File Is Printing");
        JTable table=null;
      try{  
//            URL replace=Patient_Recored.class.getClassLoader().getResource(Utilities.PATH);
//          String url=replace.getPath().toString();
          File reportDir = new File("newReport_1"); //relative path
          String absolutePath = reportDir.getAbsolutePath() + File.separator; //Convert to absolute
          System.out.println("absolutePath  "+absolutePath);
         
          param.put("path", absolutePath);
                         
            InputStream is = null;
            System.out.println("path of "+Patient_Recored.class.getClassLoader().getResource(Utilities.PATH_JASPER));
           if(PrintWithPrice==true){
               System.out.println("print with price");
               is=Patient_Recored.class.getClassLoader().getResourceAsStream(Utilities.PATH_JASPER);
              
           }else{
               is=Patient_Recored.class.getClassLoader().getResourceAsStream(Utilities.PATH_JASPER_NOT_PRICE);
               //param.put("path", Patient_Recored.class.getClassLoader().getResource(Utilities.PATH_JASPER_NOT_PRICE));
           }
            JasperReport jasperReport = JasperCompileManager.compileReport(is);
           //JasperReport   jasperReport = (JasperReport) JRLoader.loadObjectFromFile(is);
          //  JasperReport  jasperReport = (JasperReport)JasperCompileManager.compileReport(is);

            DefaultTableModel model=null;
             
            if(check==0){
                 model = (DefaultTableModel) tests_table.getModel();
                  table=tests_table;
                     }else if(check==1){
                          model = (DefaultTableModel) tests_done_table.getModel();
                                            table=tests_done_table;
                       }else if(check==2){
                           model=(DefaultTableModel)old_tests_table.getModel();
                                             table=old_tests_table;
                       }
            getDataFromTableModel(model);
            
//       param.put("name","tajmirkhan");
//       param.put("age","23");
//       param.put("gender","male");
//       param.put("report_date_time","2/23/2018");
//       param.put("dr_name","DR romi");
//       param.put("Patient_ID","12333");
       
              JasperPrint jasperPrint  = JasperFillManager.fillReport(jasperReport, param, new JRBeanCollectionDataSource(javabeanset.TestFactory.generatCollection()));
 
            //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,param, new JRTableModelDataSource(model));            
         
           JasperViewer.viewReport(jasperPrint, true);
          //PrintReportToPrinter(jasperPrint);//call method
          updateDatabase(check,model,table);
          ToastMessage("File Printed Successfully");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
            ToastMessage("Occure The Error"+e.getMessage());
               }
         }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                        try{    preferences.put(Utilities.PRINTER,"");
                            preferences.put(Utilities.CHECK_BOX,"fasle");
                        }catch(Exception ex){
                            System.out.println("errror    "+ex.toString());
                        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void search_test_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_test_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_test_tfActionPerformed

    private void search_test_tfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_test_tfKeyReleased
        filterTestTable(search_test_tf.getText().toLowerCase());        // TODO add your handling code here:
    }//GEN-LAST:event_search_test_tfKeyReleased
 
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
          try{  
           InputStream is = null;
           if(PrintWithPrice==true){
            is=Patient_Recored.class.getClassLoader().getResourceAsStream(Utilities.PATH_JASPER);
           }else{
            is=Patient_Recored.class.getClassLoader().getResourceAsStream(""+Utilities.PATH_JASPER_NOT_PRICE);
           }
            JasperReport jasperReport = JasperCompileManager.compileReport(is);
            DefaultTableModel model = (DefaultTableModel) tests_table.getModel();
            getDataFromTableModel(model);
                          JasperPrint jasperPrint  = JasperFillManager.fillReport(jasperReport, param, new JRBeanCollectionDataSource(javabeanset.TestFactory.generatCollection()));
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
//                    param, new JRTableModelDataSource(model));            
          // JasperViewer.viewReport(jasperPrint, true);
            //String path=System.getProperty("user.dir");
            String userHomeFolder = System.getProperty("user.home");
            System.out.println(userHomeFolder);
          JasperExportManager.exportReportToPdfFile(jasperPrint, userHomeFolder +"\\"+arraylist.get(i).getPtn_name()+".pdf");
          updateDatabaseFromPDFbtn(model_tests,tests_table);
          ToastMessage("File Successfully Saved");
        }catch(Exception e){
            System.out.println(e.toString());
               } // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed
private void ToastMessage(String value){
        final JDialog dialog = new AndroidLikeToast(Patient_Recored.this, true, value);
                Timer timer = new Timer(AndroidLikeToast.LENGTH_SHORT, new ActionListener() {
 
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);
                        dialog.dispose();
                    }
                });
                timer.setRepeats(false);
                timer.start();
 
                dialog.setVisible(true); 
    }
    private void search_done_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_done_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_done_tfActionPerformed

    private void search_done_tfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_done_tfKeyReleased
        filterDoneTable(search_done_tf.getText().toLowerCase());
        // TODO add your handling code here:
    }//GEN-LAST:event_search_done_tfKeyReleased

    private void done_recored_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_done_recored_tableMouseClicked
        int numbering=1;                    
        model_done_tests.setRowCount(0);
      i=done_recored_table.getSelectedRow();
       param.put("name",arraylist.get(i).ptn_name);
       param.put("age",arraylist.get(i).ptn_age);
       param.put("gender",arraylist.get(i).ptn_gender);
       param.put("report_date_time",arraylist.get(i).report_date_time);
       param.put("dr_name",arraylist.get(i).dr_name);
       param.put("Patient_ID", arraylist.get(i).apm_id);
       apm_id_for_update=arraylist.get(i).apm_id;
       System.out.println("done recorde click  "+arraylist.get(i).apm_id);
        ResultSet rs=db.retriveFromAppoitment_Details(arraylist.get(i).apm_id);
        
    try {
        Set<String> hash=new HashSet<>();
        while(rs.next()){
                    
         System.out.println("abvoe "+rs.getString(Utilities.TST_ID)+" "+rs.getString(Utilities.TDET_ID)+" "+rs.getString(Utilities.RESULT));
         String result;
            if(hash.add(rs.getString(Utilities.TST_ID))){
                char number;
                
                ResultSet rstst=db.retriveFromTEST(rs.getString(Utilities.TST_ID));
                   while(rstst.next()){
                       number='a';
                               if(rstst.getString(Utilities.TST_NORMALRANGE).equals("") && rstst.getString(Utilities.TST_UNITS).equals("")){
                                   result="";
                                         }else{
                                      result = db.retriveResult(apm_id_for_update,rs.getString(Utilities.TST_ID),rs.getString(Utilities.TDET_ID));
                                    }
                       model_done_tests.addRow(new Object[]{Integer.toString(numbering),rstst.getString(Utilities.TST_NAME),
                       rstst.getString(Utilities.TST_UNITS),rstst.getString(Utilities.TST_NORMALRANGE),
                       result,rstst.getString(Utilities.TST_PRICE),rstst.getString(Utilities.TST_ID),"",rstst.getString(Utilities.TST_NOTE)});
                        numbering++;
                          ResultSet rststd=db.retriveFromTEST_DETAILS(rstst.getString(Utilities.TST_ID));
                            {
                                if(rststd.equals(0)){
                                System.out.println("value is null");
                            }
                                try{
                             while(rststd.next()){
                              result = db.retriveResult(apm_id_for_update,rs.getString(Utilities.TST_ID),rststd.getString(Utilities.TDET_ID));

                                   // tests_table.setFont(new Font("Serif", Font.PLAIN, 12));
                                    model_done_tests.addRow(new Object[]{"\u2022",rststd.getString(Utilities.TDET_NAME),
                                     ""+rststd.getString(Utilities.TDET_UNITS),""+rststd.getString(Utilities.TDET_NORMALRANGE),
                                   result+"",""+rststd.getString(Utilities.TDET_PRICE),"",rststd.getString(Utilities.TDET_ID)});
                                    number++;
                             }}catch(Exception e){
                                 System.out.println(e.toString());
                                  }
                             }
                   }
            }
        }
      // TODO add your handling code here:
    } catch (SQLException ex) {
        Logger.getLogger(Patient_Recored.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_done_recored_tableMouseClicked

    private void search_test_done_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_test_done_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_test_done_tfActionPerformed

    private void search_test_done_tfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_test_done_tfKeyReleased
        filterDoneTestTable(search_test_done_tf.getText().toLowerCase());        // TODO add your handling code here:
    }//GEN-LAST:event_search_test_done_tfKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
     try{   
               preferences.put(Utilities.PRINTER,"");
               preferences.put(Utilities.CHECK_BOX,"fasle");
                        }catch(Exception ex){
                            System.out.println("errror    "+ex.toString());
                        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
 try{  
           InputStream is = null;
           if(PrintWithPrice==true){
               
               System.out.println("print with price");
               is=Patient_Recored.class.getClassLoader().getResourceAsStream(Utilities.PATH_JASPER);
           }else{
               is=Patient_Recored.class.getClassLoader().getResourceAsStream(""+Utilities.PATH_JASPER_NOT_PRICE);
           }
            JasperReport jasperReport = JasperCompileManager.compileReport(is);
           // DefaultTableModel model = (DefaultTableModel) model_done.getModel();
            DefaultTableModel model = (DefaultTableModel) tests_done_table.getModel();
              getDataFromTableModel(model);
              JasperPrint jasperPrint  = JasperFillManager.fillReport(jasperReport, param, new JRBeanCollectionDataSource(javabeanset.TestFactory.generatCollection()));
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
//                    param, new JRTableModelDataSource(model_done_tests));            
         
             // JasperViewer.viewReport(jasperPrint, true);
            //String path=System.getProperty("user.dir");
            String userHomeFolder = System.getProperty("user.home");
            System.out.println(userHomeFolder);
          JasperExportManager.exportReportToPdfFile(jasperPrint, userHomeFolder +"\\"+arraylist.get(i).getPtn_name()+".pdf");
          updateDatabaseFromPDFbtn(model_done_tests,tests_done_table);
          ToastMessage("File Successfully Saved");
        }catch(Exception e){
            System.out.println(e.toString());
               }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
             printer_Name= preferences.get(Utilities.PRINTER,"");
             String prefBoolean = preferences.get(Utilities.CHECK_BOX,"fasle");
             if(prefBoolean.equals("fasle")){
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            String[] printerlist=new String[20];
              int i=0;
    for (PrintService printer : printServices)
    {
        printerlist[i]=printer.getName();
           i++;
    }      
     JCheckBox checkbox = new JCheckBox("Use the selected printer always");

     JComboBox petList = new JComboBox(printerlist);

      final JComponent[] inputs = new JComponent[] {
            new JLabel("Printers"),
            petList,
            new JLabel(""),
            checkbox, 
             };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Please Select Printer", JOptionPane.PLAIN_MESSAGE);
         if (result == JOptionPane.OK_OPTION) {
         printer_Name = petList.getSelectedItem().toString();
         System.out.println("without checkbox "+printer_Name);
         printPlaning(1);
                     if (checkbox.isSelected()) {
                            preferences.put(Utilities.PRINTER, petList.getSelectedItem().toString());
                            preferences.put(Utilities.CHECK_BOX,"true");
                                } else {
                            }
              } else {
                 JOptionPane.showMessageDialog(null,"Printer Muste be selected");
               }
    
       }//pref booleans braces
             else{
                 printPlaning(1);
                 System.out.println(printer_Name);
             }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void search_old_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_old_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_old_tfActionPerformed

    private void search_old_tfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_old_tfKeyReleased
filterOldTable(search_old_tf.getText().toLowerCase());        // TODO add your handling code here:
    }//GEN-LAST:event_search_old_tfKeyReleased
String ptn_id;
    private void old_recored_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_old_recored_tableMouseClicked
    int numbering=1;                    
        model_old_tests.setRowCount(0);
      i=old_recored_table.getSelectedRow();
       param.put("name",arraylist.get(i).ptn_name);
       param.put("age",arraylist.get(i).ptn_age);
       param.put("gender",arraylist.get(i).ptn_gender);
       param.put("report_date_time",arraylist.get(i).report_date_time);
       param.put("dr_name",arraylist.get(i).dr_name);
       param.put("Patient_ID", arraylist.get(i).apm_id);
       apm_id_for_update=arraylist.get(i).apm_id;
        ptn_id=arraylist.get(i).ptn_id;
       System.out.println("done recorde click  "+arraylist.get(i).apm_id);
        ResultSet rs=db.retriveFromAppoitment_Details(arraylist.get(i).apm_id);
        
    try {
                 String result;
        Set<String> hash=new HashSet<>();
        while(rs.next()){
            if(hash.add(rs.getString(Utilities.TST_ID))){
                char number;
                ResultSet rstst=db.retriveFromTEST(rs.getString(Utilities.TST_ID));
                   while(rstst.next()){
                       if(rstst.getString(Utilities.TST_NORMALRANGE).equals("") && rstst.getString(Utilities.TST_UNITS).equals("")){
                                   result="";
                                         }else{
                                      result = db.retriveResult(apm_id_for_update,rs.getString(Utilities.TST_ID),rs.getString(Utilities.TDET_ID));
                                    }

                       model_old_tests.addRow(new Object[]{Integer.toString(numbering),rstst.getString(Utilities.TST_NAME),
                       rstst.getString(Utilities.TST_UNITS),rstst.getString(Utilities.TST_NORMALRANGE),
                       result,rstst.getString(Utilities.TST_PRICE),rstst.getString(Utilities.TST_ID),"",rstst.getString(Utilities.TST_NOTE)});
                        numbering++;
                          ResultSet rststd=db.retriveFromTEST_DETAILS(rstst.getString(Utilities.TST_ID));
                            {
                                if(rststd.equals(0)){
                                System.out.println("value is null");
                            }
                                try{
                             while(rststd.next()){
                                    
                              result = db.retriveResult(apm_id_for_update,rs.getString(Utilities.TST_ID),rststd.getString(Utilities.TDET_ID));
                                   // tests_table.setFont(new Font("Serif", Font.PLAIN, 12));
                                    model_old_tests.addRow(new Object[]{"\u2022",""+rststd.getString(Utilities.TDET_NAME),
                                     ""+rststd.getString(Utilities.TDET_UNITS),""+rststd.getString(Utilities.TDET_NORMALRANGE),
                                   result+"",""+rststd.getString(Utilities.TDET_PRICE),"",rststd.getString(Utilities.TDET_ID)});
                             }}catch(Exception e){
                                 System.out.println(e.toString()+" kkkkkkk \n"+e.getMessage());
                                  }
                             }
                   }
            }
        }
      // TODO add your handling code here:
    } catch (SQLException ex) {
        Logger.getLogger(Patient_Recored.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("error "+ex.getMessage());
        }               // TODO add your handling code here:
    }//GEN-LAST:event_old_recored_tableMouseClicked

    private void search_old_test_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_old_test_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_old_test_tfActionPerformed

    private void search_old_test_tfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_old_test_tfKeyReleased
filterOldTestTable(search_old_test_tf.getText().toLowerCase());        // TODO add your handling code here:;
    }//GEN-LAST:event_search_old_test_tfKeyReleased

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
try{   
               preferences.put(Utilities.PRINTER,"");
               preferences.put(Utilities.CHECK_BOX,"fasle");
                        }catch(Exception ex){
                            System.out.println("errror    "+ex.toString());
                        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
 try{  
            InputStream is = null;
           if(PrintWithPrice==true){
               
               System.out.println("print with price");
               is=Patient_Recored.class.getClassLoader().getResourceAsStream(Utilities.PATH_JASPER);
           }else{
               is=Patient_Recored.class.getClassLoader().getResourceAsStream(""+Utilities.PATH_JASPER_NOT_PRICE);
           }
           
            JasperReport jasperReport = JasperCompileManager.compileReport(is);
          // DefaultTableModel model = (DefaultTableModel) model_done.getModel();
            
             getDataFromTableModel(model_old_tests);
             JasperPrint jasperPrint  = JasperFillManager.fillReport(jasperReport, param, new JRBeanCollectionDataSource(javabeanset.TestFactory.generatCollection()));
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
//                    param, new JRTableModelDataSource(model_old_tests));            
         
          // JasperViewer.viewReport(jasperPrint, true);
            //String path=System.getProperty("user.dir");
            String userHomeFolder = System.getProperty("user.home");
            System.out.println(userHomeFolder);
          JasperExportManager.exportReportToPdfFile(jasperPrint, userHomeFolder +"\\"+arraylist.get(i).getPtn_name()+".pdf");
          updateDatabaseFromPDFbtn(model_old_tests,old_tests_table);
          ToastMessage("File Successfully Saved");
        }catch(Exception e){
            System.out.println(e.toString());
               }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
             printer_Name= preferences.get(Utilities.PRINTER,"");
             String prefBoolean = preferences.get(Utilities.CHECK_BOX,"fasle");
             if(prefBoolean.equals("fasle")){
             PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
             String[] printerlist=new String[20];
              int i=0;
    for (PrintService printer : printServices)
    {
        printerlist[i]=printer.getName();
           i++;
    }      
     JCheckBox checkbox = new JCheckBox("Use the selected printer always");

     JComboBox petList = new JComboBox(printerlist);

      final JComponent[] inputs = new JComponent[] {
            new JLabel("Printers"),
            petList,
            new JLabel("check"),
            checkbox, 
             };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Please Select Printer", JOptionPane.PLAIN_MESSAGE);
         if (result == JOptionPane.OK_OPTION) {
         printer_Name = petList.getSelectedItem().toString();
         System.out.println("without checkbox "+printer_Name);
         printPlaning(2);
                     if (checkbox.isSelected()) {
                            preferences.put(Utilities.PRINTER, petList.getSelectedItem().toString());
                            preferences.put(Utilities.CHECK_BOX,"true");
                                } else {
                            }
              } else {
                 JOptionPane.showMessageDialog(null,"Printer Muste be selected");
               }
    
       }//pref booleans braces
             else{
                 printPlaning(2);
                 System.out.println(printer_Name);
             }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        JTabbedPane tabbedPane = (JTabbedPane)evt.getSource();
		   int tab = tabbedPane.getSelectedIndex();
		   if(tab==0){
                       hideTheIDsColumn(0);
                       addDataIntoPndPatientTable(model_pnd,0);
                       getCheckPriceMethodAndCheckboxIsChecked(0);
                   }else if(tab==1){
                       hideTheIDsColumn(1);
                       addDataIntoPndPatientTable(model_done,1);
                       getCheckPriceMethodAndCheckboxIsChecked(1);
                   }else if(tab==2){
                        hideTheIDsColumn(2);
                        addDataIntoPndPatientTable(model_old_record,2);
                        getCheckPriceMethodAndCheckboxIsChecked(2);
                   }
                                     
// TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void paddingCheckboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paddingCheckboxMouseClicked
     if(paddingCheckbox.isSelected()){
         PrintWithPrice=true;
         preferences.put("PRINTER_WITHPRICE_TRUE","YES");
       }else{
         PrintWithPrice=false;
         preferences.put("PRINTER_WITHPRICE_TRUE","NO");
        }// TODO add your handling code here:
     //getCheckPriceMethodAndCheckboxIsChecked(0);
    }//GEN-LAST:event_paddingCheckboxMouseClicked
  void getCheckPriceMethodAndCheckboxIsChecked(int a){
  String check=preferences.get("PRINTER_WITHPRICE_TRUE","YES");
  System.out.println(check);
      if(check.equals("YES")){
          PrintWithPrice=true;
          if(a==0){
          paddingCheckbox.setSelected(true);
              }else if(a==1){
                  DoneCheckbox.setSelected(true);
              }else if(a==2){
                  OldCheckBox.setSelected(true);
                 }
      }else{
          PrintWithPrice=false;
          if(a==0){
          paddingCheckbox.setSelected(false);
      }else if(a==1){
                  DoneCheckbox.setSelected(false);
              }else if(a==2){
                  OldCheckBox.setSelected(false);
                 }
      }
      
  }
    private void paddingCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paddingCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paddingCheckboxActionPerformed

    private void DoneCheckboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DoneCheckboxMouseClicked
     if(DoneCheckbox.isSelected()){
         PrintWithPrice=true;
         preferences.put("PRINTER_WITHPRICE_TRUE","YES");
         }else{
         PrintWithPrice=false;
         preferences.put("PRINTER_WITHPRICE_TRUE","NO");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_DoneCheckboxMouseClicked

    private void OldCheckBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OldCheckBoxMouseClicked
if(OldCheckBox.isSelected()){
         PrintWithPrice=true;
         preferences.put("PRINTER_WITHPRICE_TRUE","YES");
       }else{
         PrintWithPrice=false;
         preferences.put("PRINTER_WITHPRICE_TRUE","NO");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_OldCheckBoxMouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
   String tdet_id="0";
            System.out.println(old_recored_table.getRowCount());
         for (int i = 0; i < old_recored_table.getRowCount(); i++) {
     Boolean isChecked =(Boolean)old_recored_table.getValueAt(i, 2);
                  System.out.println(isChecked);
                  //String ptn_id=arraylist.get(i).getPtn_id();
     if (isChecked) {
         //select_test_table[i,0].setSelected(false);
         old_recored_table.setValueAt(false, i, 2); // unchecked the colum in Table
        //get the values of the columns you need.
         //System.out.println(select_test_table.getValueAt(i,1).toString());
          String tst_id=old_recored_table.getValueAt(i,0).toString(); 
          db.deleteFromAppointments(tst_id,arraylist.get(i).ptn_id);          // TODO add your handling code here:
            System.out.println(tst_id);
           System.out.println("ptn id delete  "+arraylist.get(i).ptn_id);
          //ResultSet rs=db.getDataFromchildTableWithHelpOfForeignKey(tst_id);
     }}
                        addDataIntoPndPatientTable(model_old_record,2);
    }//GEN-LAST:event_jButton10ActionPerformed
   private void PrintReportToPrinter(JasperPrint jp) throws JRException {
    // TODO Auto-generated method stub
    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
    // printRequestAttributeSet.add(MediaSizeName.ISO_A4); //setting page size
    printRequestAttributeSet.add(new Copies(1));

    PrinterName printerName = new PrinterName(printer_Name, null); //gets printer 

    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
    printServiceAttributeSet.add(printerName);

    JRPrintServiceExporter exporter = new JRPrintServiceExporter();

    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
    exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
    exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
    exporter.exportReport();
}
   void updateDatabaseFromPDFbtn(DefaultTableModel model,JTable table){
             String test_id="";

         for (int i = 0; i < model.getRowCount(); i++){
    
     String test_did=(String) table.getValueAt(i,7);
     String result=(String) table.getValueAt(i,4);
     
    if(test_did!=""){
         db.updateApp_Details_toSetResult(apm_id_for_update,test_id,test_did,result);
    }else{
        test_id=(String) table.getValueAt(i,6);
        test_did="0";
        db.updateApp_Details_toSetResult(apm_id_for_update,test_id,test_did,result);
        }
   }
   }
   void updateDatabase(int check,DefaultTableModel model,JTable table){
       
         String test_id="";

         for (int i = 0; i < model.getRowCount(); i++){
    
     String test_did=(String) table.getValueAt(i,7);
     String result=(String) table.getValueAt(i,4);
     System.out.println(test_did+"  "+test_id+"   "+result);
    if(test_did!=""){
         db.updateApp_Details_toSetResult(apm_id_for_update,test_id,test_did,result);
    }else{
        test_id=(String) table.getValueAt(i,6);
        test_did="0";
        db.updateApp_Details_toSetResult(apm_id_for_update,test_id,test_did,result);
        }
   }
          if(check==0){
       db.upDateAppointmentTableChangeAppendingToDone(apm_id_for_update,"DONE");  
       addDataIntoPndPatientTable(model_pnd,0);// Refresh the patient table
          }
              model.setRowCount(0);
   }
    private void filterTable(String query){
         TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(model_pnd);
         pnd_recored_table.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)"+query));
     }
   private void filterTestTable(String query){
        TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(model_tests);
         tests_table.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)"+query));
   }
    private void filterDoneTable(String query){
         TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(model_done);
         done_recored_table.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)"+query));
     }
    private void filterDoneTestTable(String query){
         TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(model_done_tests);
         tests_done_table.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)"+query));
     }
    
     private void filterOldTable(String query){
         TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(model_old_record);
         old_recored_table.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)"+query));
     }
     
      private void filterOldTestTable(String query){
         TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(model_old_tests);
         old_tests_table.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)"+query));
     }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox DoneCheckbox;
    private javax.swing.JCheckBox OldCheckBox;
    private javax.swing.JTable done_recored_table;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable old_recored_table;
    private javax.swing.JTable old_tests_table;
    private javax.swing.JCheckBox paddingCheckbox;
    private javax.swing.JTable pnd_recored_table;
    private javax.swing.JTextField search_done_tf;
    private javax.swing.JTextField search_old_test_tf;
    private javax.swing.JTextField search_old_tf;
    private javax.swing.JTextField search_pending_tf;
    private javax.swing.JTextField search_test_done_tf;
    private javax.swing.JTextField search_test_tf;
    private javax.swing.JTable tests_done_table;
    private javax.swing.JTable tests_table;
    // End of variables declaration//GEN-END:variables
}
