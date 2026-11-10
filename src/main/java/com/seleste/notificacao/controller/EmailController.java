package com.seleste.notificacao.controller;

import com.seleste.notificacao.business.DTO.TarefasDTO;
import com.seleste.notificacao.business.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestBody TarefasDTO tarefasDTO) {
        emailService.enviaEmail(tarefasDTO);
        return ResponseEntity.ok().build();
    }
}
