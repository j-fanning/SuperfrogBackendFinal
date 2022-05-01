package edu.tcu.cs.superfrogbackendfinal.Password;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    public void sendEmail(SimpleMailMessage email);
}
