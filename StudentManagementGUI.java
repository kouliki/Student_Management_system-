import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Student {
    int rollNo;
    String name;
    int age;
    double marks;

    public Student(int rollNo, String name, int age, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.marks = marks;
    }
}

public class StudentManagementGUI {
    private final ArrayList<Student> students = new ArrayList<>();
    private final JFrame frame;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField rollField, nameField, ageField, marksField;

    public StudentManagementGUI() {
        frame = new JFrame("Student Management System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));
        
        formPanel.add(new JLabel("Roll No:"));
        rollField = new JTextField();
        formPanel.add(rollField);
        
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);
        
        formPanel.add(new JLabel("Marks:"));
        marksField = new JTextField();
        formPanel.add(marksField);
        
        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> addStudent());
        formPanel.add(addButton);
        
        JButton updateButton = new JButton("Update Student");
        updateButton.addActionListener(e -> updateStudent());
        formPanel.add(updateButton);

        // Table
        String[] columnNames = {"Roll No", "Name", "Age", "Marks"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Control Panel
        JPanel controlPanel = new JPanel();
        JButton searchButton = new JButton("Search Student");
        searchButton.addActionListener(e -> searchStudent());
        controlPanel.add(searchButton);

        JButton deleteButton = new JButton("Delete Student");
        deleteButton.addActionListener(e -> deleteStudent());
        controlPanel.add(deleteButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        controlPanel.add(exitButton);

        // Adding Components to Frame
        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addStudent() {
        try {
            int rollNo = Integer.parseInt(rollField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double marks = Double.parseDouble(marksField.getText());

            students.add(new Student(rollNo, name, age, marks));
            tableModel.addRow(new Object[]{rollNo, name, age, marks});

            clearFields();
            JOptionPane.showMessageDialog(frame, "Student Added Successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Input!");
        }
    }

    private void searchStudent() {
        try {
            int rollNo = Integer.parseInt(JOptionPane.showInputDialog("Enter Roll No to Search:"));
            for (Student s : students) {
                if (s.rollNo == rollNo) {
                    JOptionPane.showMessageDialog(frame, "Student Found:\n" +
                            "Roll No: " + s.rollNo + "\nName: " + s.name +
                            "\nAge: " + s.age + "\nMarks: " + s.marks);
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Student Not Found!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Input!");
        }
    }

    private void updateStudent() {
        try {
            int rollNo = Integer.parseInt(rollField.getText());
            for (Student s : students) {
                if (s.rollNo == rollNo) {
                    s.name = nameField.getText();
                    s.age = Integer.parseInt(ageField.getText());
                    s.marks = Double.parseDouble(marksField.getText());

                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(frame, "Student Updated Successfully!");
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Student Not Found!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Input!");
        }
    }

    private void deleteStudent() {
        try {
            int rollNo = Integer.parseInt(JOptionPane.showInputDialog("Enter Roll No to Delete:"));
            students.removeIf(s -> s.rollNo == rollNo);
            refreshTable();
            JOptionPane.showMessageDialog(frame, "Student Deleted Successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Input!");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.rollNo, s.name, s.age, s.marks});
        }
    }

    private void clearFields() {
        rollField.setText("");
        nameField.setText("");
        ageField.setText("");
        marksField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
    }
}
