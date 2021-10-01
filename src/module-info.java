module MailSender {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires activation;
    requires java.mail;
    requires java.desktop;

    opens com.company;
    opens com.company.view;
    opens com.company.controller;
    opens com.company.model;
}