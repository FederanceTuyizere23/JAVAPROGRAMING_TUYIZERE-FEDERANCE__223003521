package com.test;

import javax.swing.SwingUtilities;
import com.form.LoginForm;

public class TestSMIS {
    public static void main(String[] args) {
        // Launch the login form on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm(); // opens login form
            }
        });
    }
}
