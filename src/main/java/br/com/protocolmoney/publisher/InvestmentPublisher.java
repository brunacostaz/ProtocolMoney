package br.com.protocolmoney.publisher;

import br.com.protocolmoney.service.InvestmentService;

import javax.xml.ws.Endpoint;

public class InvestmentPublisher {

    public static void main(String[] args) {

        String url = "http://localhost:8080/investments";
        System.out.println("Subindo o servidor ProtocolMoney!");
        Endpoint.publish(url, new InvestmentService());

        System.out.println("Serviço publicado com sucesso e rodando em:");
        System.out.println(url + "?wsdl");
    }
}
