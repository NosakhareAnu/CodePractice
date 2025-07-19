/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package shiftproj;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import static com.digitalpersona.onetouch.processing.DPFPTemplateStatus.TEMPLATE_STATUS_FAILED;
import static com.digitalpersona.onetouch.processing.DPFPTemplateStatus.TEMPLATE_STATUS_READY;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/*import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui; */

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.*;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author anuol
 */
public class adminIT extends javax.swing.JFrame {

    /**
     * Creates new form adminIT
     */
    
private byte[] photoData = null; 
DPFPTemplate leftTemplate;
DPFPTemplate rightTemplate;
DPFPCapture capture;
boolean capturingLeftThumb = true;



    public adminIT(int userId, String fullName, String email) {
        initComponents();
        fetchAndDisplayUsers();
        populateUnitComboBox();
        start();
        startDigitalPersonaEnrollment();
        
        loadRosterTable();
        this.adminId = userId;
        this.adminName = fullName;
        this.loggedInEmail = email;
        
        
           try (Connection conn = getConnection()) {
    System.out.println("SUCCESS: Connected to MySQL version " + conn.getMetaData().getDatabaseProductVersion());
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }
    


private DPFPFeatureSet extractFeatures(DPFPSample sample) {
    DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
    try {
        return extractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
    } catch (DPFPImageQualityException e) {
        return null;
    }
}



private void processCapture(DPFPSample sample) {
    DPFPFeatureSet features = extractFeatures(sample);

    if (features != null) {
    try {
        DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
        enroller.addFeatures(features);

        if (enroller.getFeaturesNeeded() != 0) {
            return; // More samples needed
        }

        DPFPTemplate template = enroller.getTemplate();

        if (capturingLeftThumb) {
            leftTemplate = template;
            lblLeftStatus.setText("Left thumb captured.");
        } else {
            rightTemplate = template;
            lblRightStatus.setText("Right thumb captured.");
        }

        capture.stopCapture();

    } catch (DPFPImageQualityException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Poor fingerprint quality. Try again.");
    }
}
}



private DPFPCapture Reader = DPFPGlobal.getCaptureFactory().createCapture();
private DPFPEnrollment Enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
private DPFPTemplate template;
public static String TEMPLATE_PROPERTY = "template";




private void start() {
    Reader.startCapture();
    showStatus("Place your finger on the scanner...");
}

private void stop() {
    Reader.stopCapture();
    showStatus("Capture stopped.");
}





private void startDigitalPersonaEnrollment() {
    Reader.addDataListener(new DPFPDataAdapter() {
        public void dataAcquired(final DPFPDataEvent e) {
            SwingUtilities.invokeLater(() -> {
                showStatus("Fingerprint sample acquired.");
                captureProcess(e.getSample());
            });
        }
    });

    Reader.addReaderStatusListener(new DPFPReaderStatusAdapter() {
        public void readerConnected(final DPFPReaderStatusEvent e) {
            SwingUtilities.invokeLater(() -> showStatus("Fingerprint Sensor connected."));
        }

        public void readerDisconnected(final DPFPReaderStatusEvent e) {
            SwingUtilities.invokeLater(() -> showStatus("Fingerprint Sensor disconnected."));
        }
    });

    Reader.addSensorListener(new DPFPSensorAdapter() {
        public void fingerTouched(final DPFPSensorEvent e) {
            SwingUtilities.invokeLater(() -> showStatus("Finger touched."));
        }

        public void fingerRemoved(final DPFPSensorEvent e) {
            SwingUtilities.invokeLater(() -> showStatus("Finger removed. Place again."));
        }
    });

    Reader.addErrorListener(new DPFPErrorAdapter() {
        public void errorReader(final DPFPErrorEvent e) {
            SwingUtilities.invokeLater(() -> showStatus("Error: " + e.getError()));
        }
    });
}


private void captureProcess(DPFPSample sample) {
    DPFPFeatureSet features = extractFeatures(sample);

    if (features != null) {
        try {
            Enroller.addFeatures(features);
            Image image = DPFPGlobal.getSampleConversionFactory().createImage(sample);
            displayFingerprint(image);

            showStatus("Keep tapping until enrolled.");

            switch (Enroller.getTemplateStatus()) {
                case TEMPLATE_STATUS_READY:
                    stop();
                    template = Enroller.getTemplate();
                    showStatus("Fingerprint captured.");
                    break;

                case TEMPLATE_STATUS_FAILED:
                    Enroller.clear();
                    stop();
                    start();
                    showStatus("Failed. Try again.");
                    break;
            }
        } catch (DPFPImageQualityException e) {
            showStatus("Bad quality. Try again.");
        }
    }
}

private void displayFingerprint(Image image) {
    lblPhoto.setIcon(new ImageIcon(image.getScaledInstance(
        lblPhoto.getWidth(),
        lblPhoto.getHeight(),
        Image.SCALE_SMOOTH)));
    repaint();
}

private void showStatus(String message) {
    lblFingerprintPreview.setText(message); // You can rename this if needed
}


private String generateRandomPassword() {
    String chars = "ABCGHIJKLMNOPQRSYZabcdefghijklmstuvwxyz012346789!@#$%";
    SecureRandom rnd = new SecureRandom();
    StringBuilder sb = new StringBuilder(8);
    for (int i = 0; i < 7; i++) {
        sb.append(chars.charAt(rnd.nextInt(chars.length())));
    }
    return sb.toString();
}




private void populateUnitComboBox() {
    try (Connection conn = getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery("SELECT unit_name FROM units")) {
        cmbUnit.removeAllItems();
        while (rs.next()) {
            cmbUnit.addItem(rs.getString("unit_name"));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Optionally show a popup if units cannot load
        JOptionPane.showMessageDialog(this,
            "Error loading units from database",
            "Database Error",
            JOptionPane.ERROR_MESSAGE);
    }
}   










private final String SMTP_HOST = "smtp.gmail.com";
private final String SMTP_PORT = "587";
private final String SMTP_USER = "gina.captain.briggs@gmail.com";
private final String SMTP_PASS = "nsgc lfam kwis mpyp"; // Use an app-specific password if using Gmail.

private void sendEmail(String toEmail, String subject, String body) {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", SMTP_HOST);
    props.put("mail.smtp.port", SMTP_PORT);

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(SMTP_USER, SMTP_PASS);
        }
    });
    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(body);
        Transport.send(message);
    } catch (MessagingException e) {
        e.printStackTrace();
    }
}






















    
        private Connection getConnection() throws SQLException {

   String url  = "jdbc:mysql://localhost:3306/shift"
                + "?allowPublicKeyRetrieval=true"
                + "&useSSL=false"
                + "&serverTimezone=UTC";

    String user = "root";
    String pass = "password";
    return DriverManager.getConnection(url, user, pass);
}
        

private static String hashPassword(String plain) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[]   digest = md.digest(plain.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException ex) {
        ex.printStackTrace();
        return null;
    }
}



    private int adminId;
    private int unitId;
    private String adminName;
    private String loggedInEmail;
    
    
    
        
        
        
        
        
        
        



   private void fetchAndDisplayUsers() {
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new String[] {
        "User ID", "Full Name", "Email", "Role", "Unit", "Gender", "DOB", "Phone"
    });

    try (Connection conn = getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(
             "SELECT u.user_id, u.full_name, u.email, u.role, un.unit_name, " +
                       "u.gender, u.dob, u.phone " +
                       "FROM users u " +
                       "LEFT JOIN units un ON u.unit_id = un.unit_id")) {

        while (rs.next()) {
            Object[] row = new Object[] {
                rs.getInt("user_id"),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("role"),
                rs.getString("unit_name"),
                rs.getString("gender"),
                rs.getDate("dob"),
                rs.getString("phone")
            };
            model.addRow(row);
        }

        jTable1.setModel(model);

    } catch (SQLException ex) {
        ex.printStackTrace();
        jLabel2.setText("Failed to load users.");
        jLabel2.setForeground(java.awt.Color.RED);
    }
}
   
   
   
   
  private void deleteSelectedUser() {
    int selectedRow = tblUsers.getSelectedRow();

    if (selectedRow == -1) {
        lblAdminMessage.setText("Please select a user to delete.");
        lblAdminMessage.setForeground(java.awt.Color.RED);
        return;
    }

    int userId = (int) tblUsers.getValueAt(selectedRow, 0);
    String fullName = (String) tblUsers.getValueAt(selectedRow, 1);

    int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to delete user \"" + fullName + "\"?",
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION);

    if (confirm != JOptionPane.YES_OPTION) return;

    try (Connection conn = getConnection();
         PreparedStatement pst = conn.prepareStatement("DELETE FROM users WHERE user_id = ?")) {

        pst.setInt(1, userId);
        int affected = pst.executeUpdate();

        if (affected > 0) {
            lblAdminMessage.setText("User deleted successfully.");
            lblAdminMessage.setForeground(new java.awt.Color(0, 128, 0));
            fetchAndDisplayUsers(); // Refresh table
        } else {
            lblAdminMessage.setText("User not found.");
            lblAdminMessage.setForeground(java.awt.Color.RED);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        lblAdminMessage.setText("Error deleting user.");
        lblAdminMessage.setForeground(java.awt.Color.RED);
    }
}

private void resetPasswordForSelectedUser() {
    int selectedRow = tblUsers.getSelectedRow();

    if (selectedRow == -1) {
        lblAdminMessage.setText("Please select a user to reset password.");
        lblAdminMessage.setForeground(java.awt.Color.RED);
        return;
    }

    int userId = (int) tblUsers.getValueAt(selectedRow, 0);
    String fullName = (String) tblUsers.getValueAt(selectedRow, 1);
    String email = (String) tblUsers.getValueAt(selectedRow, 2);

    String newPlainPassword = generateRandomPassword();
    String hashedPassword = hashPassword(newPlainPassword);

    try (Connection conn = getConnection();
         PreparedStatement pst = conn.prepareStatement(
             "UPDATE users SET hashed_password = ? WHERE user_id = ?")) {

        pst.setString(1, hashedPassword);
        pst.setInt(2, userId);
        pst.executeUpdate();

        sendEmail(email,
            "Your password has been reset",
            "Hello " + fullName + ",\n\n"
          + "Your new login password is: " + newPlainPassword + "\n"
          + "Please log in and change it as soon as possible.\n\n"
          + "Regards,\nPAU Admin");

        lblAdminMessage.setText("Password reset and emailed to user.");
        lblAdminMessage.setForeground(new java.awt.Color(0, 128, 0));

    } catch (SQLException ex) {
        ex.printStackTrace();
        lblAdminMessage.setText("Error resetting password.");
        lblAdminMessage.setForeground(java.awt.Color.RED);
    } catch (Exception e) {
        e.printStackTrace();
        lblAdminMessage.setText("Failed to send email.");
        lblAdminMessage.setForeground(java.awt.Color.RED);
    }
}



DefaultTableModel model = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        capturebtn = new javax.swing.JButton();
        selectimgbtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        leftfingerbtn = new javax.swing.JButton();
        rightfingerbtn = new javax.swing.JButton();
        registerbtn = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        logoutbn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "User ID", "Full Name ", "Email", "Role", "Unit", "Gender", "DOB", "Phone"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton6.setText("Reset Password");

        jButton7.setText("Delete User");

        jLabel12.setText("jLabel12");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jButton6)
                .addGap(114, 114, 114)
                .addComponent(jButton7)
                .addGap(96, 96, 96)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jLabel12))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Manage Users", jPanel3);

        jPanel4.setPreferredSize(new java.awt.Dimension(820, 488));

        jLabel6.setText("Full Name");

        jTextField1.setText("jTextField1");

        jLabel7.setText("Email");

        jTextField2.setText("jTextField2");

        jLabel8.setText("Phone Number");

        jTextField3.setText("jTextField3");

        jLabel9.setText("Date of Birth (YYYY-MM-DD)");

        jTextField4.setText("jTextField4");

        jLabel10.setText("Role");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Staff", "Supervisor", "IT Administrator ", "Head of IT ", "Director of Services", " " }));

        jLabel2.setText("Unit");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Security", "Horticulture", "Facility", "Cafeteria", "Maintenance ", "None" }));

        jLabel3.setText("Gender");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shiftproj/Screenshot 2025-06-20 215428.png"))); // NOI18N
        jLabel4.setText("jLabel4");

        capturebtn.setText("Capture");

        selectimgbtn.setText("Select Image");
        selectimgbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectimgbtnActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shiftproj/Screenshot 2025-06-20 215829.png"))); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shiftproj/Screenshot 2025-06-20 215829.png"))); // NOI18N

        leftfingerbtn.setText("Left Finger ");
        leftfingerbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftfingerbtnActionPerformed(evt);
            }
        });

        rightfingerbtn.setText("Right Finger");
        rightfingerbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightfingerbtnActionPerformed(evt);
            }
        });

        registerbtn.setText("Register User");
        registerbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerbtnActionPerformed(evt);
            }
        });

        jLabel13.setText("jLabel13");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(leftfingerbtn)
                        .addGap(239, 239, 239))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(capturebtn)
                                                .addGap(60, 60, 60))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(47, 47, 47)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(rightfingerbtn)
                                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(selectimgbtn)))
                                                .addGap(0, 45, Short.MAX_VALUE))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(registerbtn)
                                .addGap(157, 157, 157)))))
                .addGap(86, 86, 86))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(capturebtn)
                        .addGap(28, 28, 28)
                        .addComponent(selectimgbtn)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                                    .addComponent(jComboBox1))
                                .addGap(17, 17, 17)
                                .addComponent(jLabel3)))
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                .addGap(14, 14, 14)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rightfingerbtn)
                            .addComponent(leftfingerbtn))
                        .addGap(18, 18, 18)
                        .addComponent(registerbtn)
                        .addGap(3, 3, 3))))
        );

        jTabbedPane2.addTab("Register Users", jPanel4);

        logoutbn.setText("Log Out ");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("IT ADMINISTRATOR DASHBOARD");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutbn)
                .addGap(63, 63, 63))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(logoutbn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rightfingerbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightfingerbtnActionPerformed
        // TODO add your handling code here:
        
         capturingLeftThumb = false;
        Enroller.clear();
        start();
    }//GEN-LAST:event_rightfingerbtnActionPerformed

    private void registerbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerbtnActionPerformed
        // TODO add your handling code here:
  String fullName = jTextField1.getText().trim();
        String dobText  = jTextField4.getText().trim();
        String phone    = jTextField3.getText().trim();
        String email    = jTextField2.getText().trim();
        String gender   = (String) jComboBox3.getSelectedItem();
        String unitName = (String) jComboBox2.getSelectedItem();
        String role     = (String) jComboBox1.getSelectedItem();
        String generatedPassword = generateRandomPassword(); // 👈 helper defined below
        String hashedPassword = hashPassword(generatedPassword); //

        if (fullName.isEmpty() || dobText.isEmpty() ||
            phone.isEmpty() || email.isEmpty() ||
            gender == null || unitName == null || role == null ||
            photoData == null || leftTemplate == null || rightTemplate == null) {

            jLabel3.setForeground(Color.RED);
            jLabel3.setText("All fields, photo & both fingerprints are required.");
            return;
        }

        // 2) Parse DOB
        java.sql.Date dob;
        try {
            dob = java.sql.Date.valueOf(dobText); // format “YYYY-MM-DD”
        } catch (IllegalArgumentException ex) {
            jLabel3.setForeground(Color.RED);
            jLabel3.setText("DOB must be in YYYY-MM-DD format.");
            return;
        }

        // 3) Generate random password
        String generatedPlainPassword = generateRandomPassword();

        // 4) Hash it
        String hashedPwd = hashPassword(generatedPlainPassword);
        if (hashedPwd == null) {
            jLabel3.setForeground(Color.RED);
            jLabel3.setText("Error hashing password.");
            return;
        }

        byte[] photoBytes = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Image img = ((ImageIcon) lblPhoto.getIcon()).getImage();
            BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bImage.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            ImageIO.write(bImage, "jpg", bos);
            photoBytes = bos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not convert image.");
            return;
        }

        // 5) Lookup unit_id
        int unitId = -1;
        try (Connection conn = getConnection();
            PreparedStatement pst1 = conn.prepareStatement(
                "SELECT unit_id FROM units WHERE unit_name = ?")) {

            pst1.setString(1, unitName);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                unitId = rs.getInt("unit_id");
            } else {
                jLabel3.setForeground(Color.RED);
                jLabel3.setText("Selected unit not found.");
                return;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            jLabel3.setForeground(Color.RED);
            jLabel3.setText("Database error (unit lookup).");
            return;
        }

        // 6) Insert into users
        String insertSql = "INSERT INTO users (full_name, dob, phone, email, gender, photo, left_thumb, right_thumb, role, unit_id, hashed_password) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insertSql)) {

            pst2.setString(1, fullName);
            pst2.setDate(2, dob);
            pst2.setString(3, phone);
            pst2.setString(4, email);
            pst2.setString(5, gender);
            pst2.setBytes(6, photoBytes);
            pst2.setBytes(7, leftTemplate.serialize());
            pst2.setBytes(8, rightTemplate.serialize());
            pst2.setString(9, role);
            pst2.setInt(10, unitId);
            pst2.setString(11, hashedPassword);

            // Fingerprint templates
            pst2.setBytes(7, (leftTemplate != null) ? leftTemplate.serialize() : null);
            pst2.setBytes(8, (rightTemplate != null) ? rightTemplate.serialize() : null);

            pst2.setString(9, cmbRole.getSelectedItem().toString()); // ADMIN / STAFF / SUPERVISOR
            pst2.setInt(10, cmbUnit.getSelectedIndex());             // adjust this as per unit IDs
            pst2.setString(11, hashPassword(generatedPassword));     // hashed password function
            pst2.executeUpdate();
            int rows = pst2.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException icv) {
            // This happens if email is already in use (unique constraint)
            jLabel3.setForeground(Color.RED);
            jLabel3.setText("Email already registered.");
            return;
        } catch (SQLException ex) {
            ex.printStackTrace();
            jLabel3.setForeground(Color.RED);
            jLabel3.setText("Database error (registration).");
            return;
        }

        // 7) Send email with credentials
        String subject = "Your PAU Attendance System Credentials";
        String body = "Hello " + fullName + ",\n\n"
        + "Your account has been created in the PAU Attendance System.\n"
        + "Username: " + email + "\n"
        + "Password: " + generatedPlainPassword + "\n\n"
        + "Please log in and change your password immediately.\n\n"
        + "Regards,\nPAU IT Team";
        try {
            sendEmail(email, subject, body);
        } catch (Exception e) {
            // If your sendEmail throws, catch it but don’t block registration
            e.printStackTrace();
        }

        jLabel3.setForeground(new Color(0, 128, 0)); // dark green
        jLabel3.setText("Registration successful. Check your email.");

        // Optionally: clear fields so the form is ready for the next registration
        jTextField1.setText("");
        jTextField4.setText("");
        jTextField3.setText("");
        jTextField2.setText("");
        jComboBox3.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox1.setSelectedIndex(0);
        lblPhotoPreview.setIcon(null);
        lblFingerprintPreview.setText("");
        photoData = null;
        fingerprintData = null;      
    }//GEN-LAST:event_registerbtnActionPerformed

    private void selectimgbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectimgbtnActionPerformed
        // TODO add your handling code here:
        
                JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                // Read file bytes:
                photoData = Files.readAllBytes(selectedFile.toPath());
                // Display a scaled‐down preview:
                BufferedImage img = ImageIO.read(selectedFile);
                ImageIcon icon = new ImageIcon(
                    img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                lblPhotoPreview.setIcon(icon);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Failed to load image",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_selectimgbtnActionPerformed

    private void leftfingerbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftfingerbtnActionPerformed
        // TODO add your handling code here:
         capturingLeftThumb = true;
        Enroller.clear();
        start();
    }//GEN-LAST:event_leftfingerbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(adminIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminIT().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton capturebtn;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JButton leftfingerbtn;
    private javax.swing.JButton logoutbn;
    private javax.swing.JButton registerbtn;
    private javax.swing.JButton rightfingerbtn;
    private javax.swing.JButton selectimgbtn;
    // End of variables declaration//GEN-END:variables

    private String hashPassword(String generatedPassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
