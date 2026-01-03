/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package healthreport;

import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Tajmirkhan
 */
public class Dr_Commession extends javax.swing.JInternalFrame {

    /**
     * Creates new form Dr_Commession
     */
    String currentTimeDate;
    String current_date;
    Map param;
    
    long totalNumber_patient=0,totalNumber_test=0;
    long total_price=0,doctore_commession=0;
       Preferences preferences;
    DataBase db;
    DefaultTableModel model;
    String dr_id;
    ArrayList<DoctoreInformation_GetterSetter> arraylist;
    
    String start_date,end_date;
    public Dr_Commession() {
        param=new HashMap();
        initComponents();
        arraylist=new ArrayList<DoctoreInformation_GetterSetter>();
                 model =(DefaultTableModel)dr_table.getModel();
                 db=new DataBase();
                hideColum();
                showDataIntoDoctorTable();
                getCurrentTimeAndDate();
                 preferences = Preferences.userRoot().node("java-buddy");
                 fillStartDataComboBox();
                 
    }
      
           void fillStartDataComboBox(){
        ResultSet rs=db.ReteriveDataFromAppointmentTable();
     try {
         Set hh=new HashSet();
         while(rs.next()){
             if(hh.add(rs.getString(Utilities.REPORT_DATE_TIME))){
             dr_startDataCombo.addItem(rs.getString(Utilities.REPORT_DATE_TIME));
             endDataCombo.addItem(rs.getString(Utilities.REPORT_DATE_TIME));
             }
         }
     } catch (SQLException ex) {
         Logger.getLogger(Add_Patient.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
      
        void hideColum(){
            dr_table.getColumnModel().getColumn(0).setWidth(0);
         dr_table.getColumnModel().getColumn(0).setMinWidth(0);
         dr_table.getColumnModel().getColumn(0).setMaxWidth(0);  
      
        }
     private void showDataIntoDoctorTable(){
           model.setRowCount(0);
           ResultSet rs=db.reteriveDataFromDoctor();
        //dr_table.setModel(DbUtils.resultSetToTableModel(rs));
         
    try {    DoctoreInformation_GetterSetter data;
        while(rs.next()){
            data=new DoctoreInformation_GetterSetter();
            model.addRow(new Object[]{rs.getInt(Utilities.DR_ID),rs.getString(Utilities.DR_NAME),
                rs.getString(Utilities.DR_SPECIALIZATION),rs.getString(Utilities.DR_ADDRESS)});
            data.setDr_id(rs.getString(Utilities.DR_ID));
            data.setDr_Name(rs.getString(Utilities.DR_NAME));
            data.setDr_spacialization(rs.getString(Utilities.DR_SPECIALIZATION));
            data.setDr_address(rs.getString(Utilities.DR_ADDRESS));
            arraylist.add(data);
        }
//        dr_table.setRowSelectionAllowed(true);
        dr_table.getTableHeader().setReorderingAllowed(true);
    } catch (Exception ex) {
        System.out.println(ex.toString());
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        dr_search_tf = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        total_tests = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        total_pateint = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dr_table = new javax.swing.JTable();
        total_tests1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        endDataCombo = new javax.swing.JComboBox();
        total_amount_btwn_date = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dr_startDataCombo = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        total_pateint_btwn_date = new javax.swing.JLabel();
        total_tests_btwn_date = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        total_amount_btwn_date1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(173, 31, 31));

        jPanel2.setBackground(new java.awt.Color(173, 31, 31));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "DR Commission", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        dr_search_tf.setBackground(new java.awt.Color(109, 12, 12));
        dr_search_tf.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        dr_search_tf.setForeground(new java.awt.Color(255, 255, 255));
        dr_search_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dr_search_tfActionPerformed(evt);
            }
        });
        dr_search_tf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dr_search_tfKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total Pateint :");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Docoter Commission");

        total_tests.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        total_tests.setForeground(new java.awt.Color(255, 255, 255));
        total_tests.setText("       ");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Total Test    :");

        total_pateint.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        total_pateint.setForeground(new java.awt.Color(255, 255, 255));
        total_pateint.setText("      ");

        dr_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "spacilization", "address"
            }
        ));
        dr_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dr_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dr_table);

        total_tests1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        total_tests1.setForeground(new java.awt.Color(255, 255, 255));
        total_tests1.setText("       ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(total_pateint, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(total_tests1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(total_tests, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(dr_search_tf, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dr_search_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(total_pateint))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(total_tests))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(total_tests1))
                .addGap(28, 28, 28)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(173, 31, 31));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selected Test Amounts", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        endDataCombo.setBackground(new java.awt.Color(109, 12, 12));
        endDataCombo.setForeground(new java.awt.Color(255, 255, 255));
        endDataCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                endDataComboItemStateChanged(evt);
            }
        });
        endDataCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                endDataComboMouseClicked(evt);
            }
        });
        endDataCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endDataComboActionPerformed(evt);
            }
        });

        total_amount_btwn_date.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        total_amount_btwn_date.setForeground(new java.awt.Color(255, 255, 255));
        total_amount_btwn_date.setText("       ");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total Pateint :");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total Amount");

        dr_startDataCombo.setBackground(new java.awt.Color(109, 12, 12));
        dr_startDataCombo.setForeground(new java.awt.Color(255, 255, 255));
        dr_startDataCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dr_startDataComboItemStateChanged(evt);
            }
        });
        dr_startDataCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dr_startDataComboMouseClicked(evt);
            }
        });
        dr_startDataCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dr_startDataComboActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Total Tests   :");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Select Start Date");

        total_pateint_btwn_date.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        total_pateint_btwn_date.setForeground(new java.awt.Color(255, 255, 255));
        total_pateint_btwn_date.setText("      ");

        total_tests_btwn_date.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        total_tests_btwn_date.setForeground(new java.awt.Color(255, 255, 255));
        total_tests_btwn_date.setText("       ");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Select End Date");

        total_amount_btwn_date1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        total_amount_btwn_date1.setForeground(new java.awt.Color(255, 255, 255));
        total_amount_btwn_date1.setText("       ");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("WithOut Commission");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(dr_startDataCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(endDataCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(total_pateint_btwn_date, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(6, 6, Short.MAX_VALUE)
                            .addComponent(total_tests_btwn_date, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(total_amount_btwn_date1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(total_amount_btwn_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(46, 46, 46))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dr_startDataCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(endDataCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(total_pateint_btwn_date))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(total_tests_btwn_date))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(total_amount_btwn_date))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(total_amount_btwn_date1))
                .addContainerGap(211, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dr_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dr_tableMouseClicked
        // TODO add your handling code here:
         totalNumber_patient=0;totalNumber_test=0;
         total_price=0;doctore_commession=0;
        int i=dr_table.getSelectedRow();
        param.put("dr_id",arraylist.get(i).Dr_id);
        param.put("dr_name",arraylist.get(i).Dr_Name);
        param.put("dr_spacialization",arraylist.get(i).Dr_spacialization);
        param.put("dr_address",arraylist.get(i).Dr_address);
         Set<String> hash=new HashSet<>();
         Set<String> has_test=new HashSet<>();
      dr_id=dr_table.getModel().getValueAt(dr_table.convertRowIndexToModel(i),0).toString();
       System.out.println("dr_id "+dr_id+"current_date"+current_date);
        ResultSet rs=db.retrivePerDoctoreCost(current_date,dr_id);
        try {
            while(rs.next()){
                System.out.print("come loop");
                if(hash.add(rs.getString(Utilities.PTN_ID))){
                   totalNumber_patient++;
                }
                if(has_test.add(rs.getString(Utilities.TST_ID)+rs.getString(Utilities.PTN_ID))){
                    totalNumber_test++;
                }
                total_price=total_price+Long.parseLong(rs.getString(Utilities.TST_PRICE));
               
               } 
                total_tests1.setText(""+total_price/2);
                total_tests.setText(""+totalNumber_test);
                total_pateint.setText(""+totalNumber_patient);
            param.put("total_tests",""+totalNumber_test);
            param.put("total_patient",""+totalNumber_patient);
            param.put("total_price",""+total_price/2);
            
//        dr_name_tf.setText(dr_table.getModel().getValueAt(dr_table.convertRowIndexToModel(i),1).toString());
//        String str=dr_table.getModel().getValueAt(dr_table.convertRowIndexToModel(i),2).toString();
//        dr_phone_tf.setText(dr_table.getModel().getValueAt(dr_table.convertRowIndexToModel(i),3).toString());
//        dr_specialization_tf.setText(dr_table.getModel().getValueAt(dr_table.convertRowIndexToModel(i),4).toString());
//        dr_address_ta.setText(dr_table.getModel().getValueAt(dr_table.convertRowIndexToModel(i),5).toString());
        } catch (SQLException ex) {
            Logger.getLogger(Dr_Commession.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_dr_tableMouseClicked

    private void dr_search_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dr_search_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dr_search_tfActionPerformed

    private void dr_search_tfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dr_search_tfKeyReleased
        filterTable(dr_search_tf.getText().toLowerCase());
    }//GEN-LAST:event_dr_search_tfKeyReleased
String printer_Name;
void printPlaning(){
        
      try{  
            InputStream is = null;
           
            is=Patient_Recored.class.getClassLoader().getResourceAsStream(Utilities.DOCTORE_RECORED_PATH);
            JasperReport jasperReport = JasperCompileManager.compileReport(is);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    param,new JRTableModelDataSource(model));            
         
          //JasperViewer.viewReport(jasperPrint, true);
          PrintReportToPrinter(jasperPrint);//call method
        }catch(Exception e){
            System.out.println(e.toString());
               }
         }
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            String[] printerlist=new String[20];
              int i=0;
    for (PrintService printer : printServices)
    {
        printerlist[i]=printer.getName();
           i++;
    }      
     

     JComboBox petList = new JComboBox(printerlist);

      final JComponent[] inputs = new JComponent[] {
            new JLabel("Printers"),
            petList,
             };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Please Select Printer", JOptionPane.PLAIN_MESSAGE);
         if (result == JOptionPane.OK_OPTION) {
         printer_Name = petList.getSelectedItem().toString();
         System.out.println("without checkbox "+printer_Name);
                     printPlaning();
              } else {
                 JOptionPane.showMessageDialog(null,"Printer Muste be selected");
               }
             
                
                 System.out.println(printer_Name);
                     // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dr_startDataComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dr_startDataComboItemStateChanged
        String item=dr_startDataCombo.getSelectedItem().toString();
        start_date=item;
    }//GEN-LAST:event_dr_startDataComboItemStateChanged

    private void dr_startDataComboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dr_startDataComboMouseClicked

    }//GEN-LAST:event_dr_startDataComboMouseClicked

    private void dr_startDataComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dr_startDataComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dr_startDataComboActionPerformed
          void comboclick(){
               String itmee=endDataCombo.getSelectedItem().toString();        // TODO add your handling code here:
      end_date=itmee;
       if(start_date.equals("")){
        JOptionPane.showMessageDialog(null,"first select Start Date");
        return;
       }

        totalNumber_patient=0;totalNumber_test=0;
         total_price=0;doctore_commession=0;
        int i=dr_table.getSelectedRow();

         Set<String> hash=new HashSet<>();
         Set<String> has_test=new HashSet<>();

        ResultSet rs=db.retriveDataBettwenTwoDate(start_date,end_date);
        try {
            while(rs.next()){
                if(hash.add(rs.getString(Utilities.PTN_ID))){
                   totalNumber_patient++;
                }
                if(has_test.add(rs.getString(Utilities.TST_ID)+rs.getString(Utilities.PTN_ID))){
                    totalNumber_test++;
                }
                try{
                total_price=total_price+(long)rs.getDouble(Utilities.TST_PRICE);
                System.out.println("double "+rs.getDouble(Utilities.TST_PRICE));
                }catch(Exception e){
                    System.out.println("theer"+e.toString());
                }
               } 
                total_amount_btwn_date.setText(""+total_price);
                total_tests_btwn_date.setText(""+totalNumber_test);
                total_pateint_btwn_date.setText(""+totalNumber_patient);
                total_amount_btwn_date1.setText(""+total_price/2);
        }catch(Exception e){
                          
                        }
          }
    private void endDataComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_endDataComboItemStateChanged
        comboclick();
    }//GEN-LAST:event_endDataComboItemStateChanged

    private void endDataComboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_endDataComboMouseClicked
               comboclick();
 // TODO add your handling code here:
    }//GEN-LAST:event_endDataComboMouseClicked

    private void endDataComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endDataComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_endDataComboActionPerformed
 private void filterTable(String query){
         TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(model);
         dr_table.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)"+query));
     }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dr_search_tf;
    private javax.swing.JComboBox dr_startDataCombo;
    private javax.swing.JTable dr_table;
    private javax.swing.JComboBox endDataCombo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel total_amount_btwn_date;
    private javax.swing.JLabel total_amount_btwn_date1;
    private javax.swing.JLabel total_pateint;
    private javax.swing.JLabel total_pateint_btwn_date;
    private javax.swing.JLabel total_tests;
    private javax.swing.JLabel total_tests1;
    private javax.swing.JLabel total_tests_btwn_date;
    // End of variables declaration//GEN-END:variables
}
