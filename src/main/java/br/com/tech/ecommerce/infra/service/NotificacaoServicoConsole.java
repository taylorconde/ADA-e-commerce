package src.main.java.br.com.tech.ecommerce.infra.service;

import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;
import src.main.java.br.com.tech.ecommerce.domain.service.NotificacaoServico;

public class NotificacaoServicoConsole implements NotificacaoServico {

    @Override
    public void enviarNotificacao(Cliente cliente, String mensagem) {
        System.out.println("📢 Notificação para " + cliente.getNome() +
                " (" + cliente.getEmail() + "): " + mensagem);
    }
}