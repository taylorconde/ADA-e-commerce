package src.main.java.br.com.tech.ecommerce.domain.service;

import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;

public interface NotificacaoServico {
    void enviarNotificacao(Cliente cliente, String s);
}
