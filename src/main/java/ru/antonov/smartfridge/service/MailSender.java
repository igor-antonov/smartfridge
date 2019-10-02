package ru.antonov.smartfridge.service;

import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface MailSender {
    void send();
    List<SimpleMailMessage> getTextForEmail();
}
