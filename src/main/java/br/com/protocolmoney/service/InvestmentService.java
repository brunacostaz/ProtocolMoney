package br.com.protocolmoney.service;

import br.com.protocolmoney.model.AssetPosition;
import br.com.protocolmoney.model.AssetType;
import br.com.protocolmoney.model.InvestorPosition;
import br.com.protocolmoney.response.PositionResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/*
Serviço principal da aplicação. Ele possui 3 operações:
-> invest: responsável por realizar as aplicações dos clientes
-> redemption: responsável por realizar os resgates dos clientes
-> getPosition: responsável por consultar a posição do cliente
 */
@WebService
public class InvestmentService {

    private static Map<String, InvestorPosition> database = new HashMap<>();

    @WebMethod
    public String invest (
            @WebParam(name = "personId") String personId,
            @WebParam(name = "assetCode") String assetCode,
            @WebParam(name = "amount") double amount) {

        try {

            // Valida se o ativo recebido na requisição está dentro da lista de Enums
            AssetType type = AssetType.valueOf(assetCode);

            // Busca se o investidor já existe no banco de dados. Se não existe, ele cria um novo item
            InvestorPosition investor = database.getOrDefault(personId, new InvestorPosition(personId));

            AssetPosition existingAsset = investor.getInvestorAssets().stream()
                    .filter(a -> a.getAssetCode().equals(assetCode))
                    .findFirst()
                    .orElse(null);

            if (existingAsset != null) {
                // Soma a posição atual com o nosso aporte
                existingAsset.setBalance(existingAsset.getBalance() + amount);

            } else {

                // Define a data de vencimento do ativo com base nas regras do ativo
                LocalDate dueDate = type.calculatedDueDate(LocalDate.now());

                // Cria um novo ativo na carteira do investidor
                AssetPosition newAsset = new AssetPosition(
                        type.name(),
                        type.getDescription(),
                        amount,
                        dueDate.toString()
                );
                investor.getInvestorAssets().add(newAsset);
            }

            // Atualiza a posição consolidada no objeto do investidor e salva no HashMap
            investor.updateBalance();
            database.put(personId, investor);

            return "SUCESSO: R$ " + amount + " aplicado em " + type.getDescription();

        } catch (IllegalArgumentException e) {
            String assetsAvailable = "";
            for (AssetType a : AssetType.values()) {
                assetsAvailable = assetsAvailable + a.name() + "(" + a.getDescription() + ") | ";
            }
            return "ERRO: Código do ativo inválido! Ativos disponíveis: " + assetsAvailable;
        }
    }

    @WebMethod
    public PositionResponse getPosition(@WebParam(name = "personId") String personId ) {

        InvestorPosition investor = database.get(personId);

        if (investor == null) {
            return new PositionResponse("Erro 404: Investidor não encontrado!", false, null);
        }

        investor.updateBalance();
        return new PositionResponse("200: Investidor encontrado!", true, investor);
    }
}
