package ru.antonov.smartfridge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.antonov.smartfridge.domain.Product;
import ru.antonov.smartfridge.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class MailSenderExpiresSoon implements MailSender{

    private final Logger log = LoggerFactory.getLogger(MailSenderExpiresSoon.class);
    private final ProductService productService;
    private final JavaMailSender mailSender;
    private final String SUBJECT = "Срок годности продуктов подходит к концу";

    public MailSenderExpiresSoon(ProductService productService, JavaMailSender mailSender) {
        this.productService = productService;
        this.mailSender = mailSender;
    }

    @Override
    @Scheduled(cron = "0 0 10 * * ?") //At 10:00:00am every day
    //@Scheduled(fixedRate = 30000)
    public void send() {
        log.info("Начинается отправка сообщений.");
        getTextForEmail().forEach(mailSender::send);
        log.info("Отправка сообщений завершена.");
    }

    @Override
    public List<SimpleMailMessage> getTextForEmail(){
        Map<User, List<Product>> productsByUser =
                productService.findExpiresSoon().stream().collect(
                        Collectors.groupingBy(Product::getUser));
        List<SimpleMailMessage> messages = new ArrayList<>();
        for(Map.Entry<User, List<Product>> products : productsByUser.entrySet()){
            StringBuilder text = new StringBuilder();
            for(Product product : products.getValue()){
                text.append(String.format("%s (%s);\n", product.getProductDetails().getName(),
                        product.getCreationDate()));
            }
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(products.getKey().getEmail());
            message.setSubject(SUBJECT);
            log.info("user: " + products.getKey().getEmail()
                    + ", text: " + text.toString());
            message.setText(text.toString());
            messages.add(message);
        }
        return messages;
    }
}
