# ProtocolMoney 💸💰

O **ProtocolMoney** é uma aplicação responsável por gerenciar a carteira de ativos de um investidor, orquestrando as aplicações, resgates e consolidações de posição dos clientes. O objetivo dele é resolver a falta de padronização no tráfego de dados financeiros entre instituições, utilizando o protocolo SOAP para garantir contratos rígidos. Além disso, elimina erros humanos em processos de resgate ao automatizar a conferência de prazos de carência de títulos de renda fixa (CDB), garantindo que a instituição financeira mantenha sua liquidez conforme planejado.

---

**Problemas resolvidos:**
* **Risco de Liquidez:** Automatiza a conferência de prazos de carência, impedindo resgates antecipados indevidos que poderiam afetar o caixa da instituição.
* **Erros Operacionais:** Elimina cálculos manuais de saldo consolidado, oferecendo uma visão única e atualizada da carteira do investidor.
* **Padronização:** Utiliza XML estruturado para que qualquer sistema (Mobile, Web ou Desktop) consuma as regras de negócio de forma uniforme.

---

## 📈 Ativos Negociados

O sistema gerencia três categorias principais de ativos com regras distintas:

| Código | Nome do Ativo | Carência |
| :--- | :--- | :--- |
| **CBDI** | CDB Liquidez Diária | Resgate imediato (D+0) |
| **CBP3M** | CDB Pós-Fixado 90 Dias | Bloqueado por 3 meses |
| **CBPE1Y** | CDB Pré-Fixado 1 Ano | Bloqueado por 12 meses |

---

## 🛠 Tecnologias e Dependências

* **Linguagem:** Java 21 (LTS)
* **Framework SOAP:** JAX-WS (Java API for XML Web Services)
* **Mapeamento XML:** JAXB (Java Architecture for XML Binding)
* **Servidor:** Servidor HTTP interno do JAX-WS (`Endpoint.publish`)

---

## Contrato API SOAP

Diferente de arquiteturas REST convencionais, o ProtocolMoney utiliza o protocolo SOAP (Simple Object Access Protocol). Isso garante uma comunicação altamente estruturada e segura através de:

- Contrato WSDL (Web Services Description Language): Descreve todos os métodos, parâmetros e tipos de dados aceitos pelo servidor.

- Mensageria XML: Todas as informações são encapsuladas em um envelope, garantindo que os dados sejam lidos da mesma forma por qualquer sistema.

- Método POST: Seguindo o padrão do protocolo, todas as operações de escrita e leitura são realizadas via POST, enviando as instruções de execução dentro do corpo da requisição.

--- 

## 🚀 Operações Disponíveis (Endpoints)

As funcionalidades do serviço estão expostas através dos seguintes métodos:

### invest

Responsável por realizar a **aplicação** no ativo desejado.

| Paramêtros | Descrição |
| :--- | :--- |
| **personId** | Id único do investidor | 
| **assetCode** | Código do ativo que deseja aplicar | 
| **amount** | Valor a ser aplicado | 

Se o investidor ainda não possui cadastro na corretora, o serviço realiza automaticamente e conclui a aplicação.
Se ele já possui, o serviço verifica se tem posição no ativo recebido e incrementa o novo valor.

Exceções tratadas
- Se o código do ativo estiver incorreto

--- 

### redemption

Responsável por realizar o **resgate** no ativo desejado

| Paramêtros | Descrição |
| :--- | :--- |
| **personId** | Id único do investidor | 
| **assetCode** | Código do ativo que deseja resgatar | 
| **amount** | Valor a ser resgatado | 

Exceções tratadas:
- Se o investidor não é localizado pois não existe no banco de dados
- Se o investidor não possui o ativo que deseja resgatar
- Se o código do ativo está incorreto
- Se o valor a ser resgatado é maior do que a posição atual

---

### getPosition

Responsável por retornar a posição consolidada (total) e posição por ativo da carteira do investidor

| Paramêtros | Descrição |
| :--- | :--- |
| **personId** | Id único do investidor | 

Exceções tratadas:
- Se o investidor não for encontrado

---

## 🏛️ Boas Práticas Aplicadas

* **Domain-Driven Rules (Enum):** Regras de carência centralizadas no `AssetType`, um Enum com os tipos de ativo e regras individuais, facilitando a manutenção.
* **Response Wrapper Pattern:** Uso da classe `PositionResponse` para padronizar retornos, garantindo que o cliente receba status e mensagens claras mesmo em casos de erro.
* **Fail-Fast Validation:** Validações de segurança executadas no início de cada operação (verificação de CPF, saldo e datas).
* **Clean Code com Java Streams:** Processamento eficiente de listas para cálculos de saldo e filtros de busca.

---

## 🚀 Próximas Features (Roadmap)

* [ ] **Persistência Real:** Migração do HashMap para Banco de Dados Relacional (PostgreSQL ou Oracle, por exemplo).
* [ ] **Rendimento Dinâmico:** Integração com APIs de mercado para atualização de saldo baseada no CDI.
* [ ] **Auditoria:** Log completo de transações para histórico de movimentações.
* [ ] **Ampliação da carteira de ativos:** Introduzir novos ativos para negociação, como ações e FIIs.
      
---

## 💻 Como Rodar e Testar

### 1. Executando o Servidor
1. Certifique-se de ter o **JDK 21** instalado.
2. Execute a classe `InvestmentPublisher.java`.
3. O console indicará que o serviço está ativo em: `http://localhost:8080/investments?wsdl`

### 2. Configurando o Insomnia
1. Crie uma requisição **POST** para `http://localhost:8080/investments`.
2. Nos **Headers**, adicione:
   * `Content-Type`: `text/xml;charset=UTF-8`
   * `Accept`: `text/xml`
3. No **Body**, selecione o formato **XML** e utilize o envelope abaixo:

Enviar requisições para o método invest:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.protocolmoney.com.br/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:invest>
         <personId>AB123</personId>
         <assetCode>CBPE1Y</assetCode>
         <amount>10900</amount>
      </ser:invest>
   </soapenv:Body>
</soapenv:Envelope>
```

Enviar requisições para o método redemption:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.protocolmoney.com.br/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:redemption>
         <personId>AB123</personId>
         <assetCode>CBPE1Y</assetCode>
         <amount>578.50</amount>
      </ser:redemption>
   </soapenv:Body>
</soapenv:Envelope>
```

Enviar requisições para o método getPosition:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://service.protocolmoney.com.br/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:getPosition>
         <personId>AB123</personId>
      </ser:getPosition>
   </soapenv:Body>
</soapenv:Envelope>
```
