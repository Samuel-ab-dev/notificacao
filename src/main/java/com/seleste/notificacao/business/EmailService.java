package com.seleste.notificacao.business;

import com.seleste.notificacao.business.DTO.TarefasDTO;
import com.seleste.notificacao.infrastructure.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${envio.email.remetente}")
    public String remetente;

    @Value("${envio.email.nomeRemetente}")
    private String nomeRemetente;

    public void enviaEmail(TarefasDTO tarefasDTO) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                    true,
                    StandardCharsets.UTF_8.name());
            messageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            messageHelper.setTo(InternetAddress.parse(tarefasDTO.getEmailUsuario()));
            messageHelper.setSubject("Notificação de Tarefa");

            Context context = new Context();
            context.setVariable("nomeTarefa", tarefasDTO.getNomeTarefa());
            context.setVariable("dataEvento", tarefasDTO.getDataEvento());
            context.setVariable("descricao", tarefasDTO.getDescricao());
            String template = templateEngine.process("notificacao", context);
            messageHelper.setText(template, true);

            javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailException("Erro ao enviar email: " + e.getCause());
        }
    }
}
