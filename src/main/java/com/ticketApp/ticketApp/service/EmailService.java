package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.dto.CheckoutEmailDTO;
import com.ticketApp.ticketApp.dto.CheckoutEmailElement;
import com.ticketApp.ticketApp.entity.CartEntity;
import com.ticketApp.ticketApp.entity.UserEntity;
import com.ticketApp.ticketApp.repository.CartRepository;
import com.ticketApp.ticketApp.repository.EventRepository;
import com.ticketApp.ticketApp.repository.TicketRepository;
import com.ticketApp.ticketApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private MailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    MailProperties mailProperties;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Async
    public void sentEmail() {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo("ioana.miruna.ib@icloud.com");
        message.setText("Pizza");
        message.setSubject("Pizza or chikichips?");
        mailSender.send(message);
    }

//    public static void test()
//    {
//        Context context = new Context();
//        context.setVariable("link","http://xyz.com");
//        context.setVariable("name",setusername);
//        try {
//            mailService.sendHtmlMail(emailid,subject,"resetPassword",context);
//        } catch (MessagingException e) {
//            log.error("error in  sendForgotPasswordEmail"+e);
//        }
//    }


    public Context buildEmailContext(Integer userId)
    {
        Context context = new Context();
        CheckoutEmailDTO emailDto = new CheckoutEmailDTO();
        emailDto.checkoutElements= new ArrayList<>();
        List<CartEntity> cart = cartRepository.getCartByUserID(userId);
        UserEntity user = userRepository.getById(userId);
        emailDto.total = 0D;
        emailDto.name = user.getFirstName() + " " + user.getLastName();
        for (CartEntity ent : cart) {
            CheckoutEmailElement dto = new CheckoutEmailElement();
            dto.eventName = ent.getTicket().getEvent().getEventName();
            dto.ticket = ent.getNumberOfCartTickets() + " x " + ent.getTicket().getTicketType();
            emailDto.total = emailDto.total + (ent.getTicket().getPrice() * ent.getNumberOfCartTickets());

            emailDto.checkoutElements.add(dto);
        }

        context.setVariable("cart", emailDto);
        return context;
    }
    private static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
    @Async
    public void sendHtmlMail(Integer userID) throws MessagingException, UnsupportedEncodingException {
        Context context = buildEmailContext(userID);
        context.setVariable("purchaseNumber", getRandomNumberString());
        MimeMessage mail = javaMailSender.createMimeMessage();
        String body = templateEngine.process("checkoutTemplate.html", context);
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);

        String email = userRepository.getById(userID).getEmail();
        helper.setTo(email);
        helper.setSubject("Comanda ticket buddy");
        helper.setText(body, true);
        javaMailSender.send(mail);
    }
}
