package com.example.leetcode_clone.codeupdatelistener;

import com.example.shared.kafka.KafkaConstant;
import com.example.shared.kafka.dto.RunCodeUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class CodeUpdateConsumer {
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(groupId = KafkaConstant.GROUP_ID_CODE_UPDATE_CONSUMER, topics = KafkaConstant.CODE_EXECUTION_UPDATE_TOPIC_NAME, containerFactory = KafkaConstant.KAFKA_CODE_UPDATE_CONSUMER_CONTAINER_FACTORY)
    public void receivedCodeUpdate(RunCodeUpdateDTO updateDTO) throws JsonProcessingException {
        System.out.println("Code Execution Update for: " + updateDTO.getUuid() + " of judgement: " + updateDTO.getJudgement());
        
        template.convertAndSend("/submittion_run/"+updateDTO.getUuid(), updateDTO);
    }
}
