package br.edu.utfpr.sofrimento.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AlertConsumerService {
    @RabbitListener(queues = "alertas")
    public void receiveMessage(String message) {
        System.out.println("[ALERTA RECEBIDO] " + message);
        // Aqui você pode acionar lógica de envio de e-mail, SMS, etc.
    }
}
