package br.com.challenge.desafiosangiorgio.service;

import br.com.challenge.desafiosangiorgio.model.dto.RequestDto;
import br.com.challenge.desafiosangiorgio.model.enums.StatusPagamento;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class SQSService {

    @Autowired
    private AmazonSQS amazonSQSClient;

    @Autowired
    private ExecutorService executorService;

    @Value("${aws.sqs.fila.parcial}")
    private String filaParcial;

    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.fila.total}")
    private String filaTotal;

    @Value("${aws.sqs.fila.excedente}")
    private String filaExcedente;

    public SQSService(ObjectMapper objectMapper, AmazonSQS amazonSQSClient) {
        this.objectMapper = objectMapper;
        this.amazonSQSClient = amazonSQSClient;
    }

    public void enviarParaFila(RequestDto requisicao, StatusPagamento status) {
        String filaUrl = switch (status) {
            case PARCIAL -> filaParcial;
            case TOTAL -> filaTotal;
            case EXCEDENTE -> filaExcedente;
            default -> throw new IllegalArgumentException("Status de pagamento inválido");
        };
        executorService.execute(() -> {
            try {
                SendMessageRequest sendMessageRequest = new SendMessageRequest()
                        .withQueueUrl(filaUrl)
                        .withMessageBody(objectMapper.writeValueAsString(requisicao));
                amazonSQSClient.sendMessage(sendMessageRequest);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e); //Em ambiente real, reprocessar a mensagem ou redirecionar para fila morta
            }
        });

    }
}
