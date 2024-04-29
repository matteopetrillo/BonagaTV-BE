package it.petrillo.bonagatv.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(String userEmail, String psw, String nomeEvento) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,"UTF-8");
            messageHelper.setFrom("no-reply@bonagacommunication.tv");
            messageHelper.setTo(userEmail);
            messageHelper.setSubject("Your "+nomeEvento+" streaming credentials");
            String htmlMessage = buildMail(userEmail, psw);
            messageHelper.setText(htmlMessage, true);
            mailSender.send(message);

        } catch (Exception e) {
            log.error("Errore nell'invio della mail");
            e.printStackTrace();
        }


    }

    private String buildMail(String user, String psw) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"it\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Grazie per l'acquisto del nostro servizio streaming</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #fff;\n" +
                "            padding: 40px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        p {\n" +
                "            color: #666;\n" +
                "        }\n" +
                "        .credentials {\n" +
                "            margin-top: 30px;\n" +
                "            padding: 20px;\n" +
                "            background-color: #f9f9f9;\n" +
                "            border-radius: 8px;\n" +
                "        }\n" +
                "        .credentials p {\n" +
                "            margin: 5px 0;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Ecco le tue credenziali!</h1>\n" +
                "        <p>Caro cliente,</p>\n" +
                "        <p>Di seguito trovi le credenziali per accedere alla visione del live streaming:</p>\n" +
                "        <div class=\"credentials\">\n" +
                "            <p>Email: <span>"+user+"</span></p>\n" +
                "            <p>Password: <span>"+psw+"</span></p>\n" +
                "        </div>\n" +
                "        <p>Se hai bisogno di assistenza puoi contattarci a help@bonagacommunication.tv .</p>\n" +
                "        <p>Grazie, </p>\n" +
                "        <p>Il team di Bonaga Communication</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";


    }

}
