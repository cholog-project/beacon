package com.example.braveCoward.batchinsert.email;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.braveCoward.mock.MockEmailService;

@Component
@StepScope
public class EmailWriter implements ItemWriter<String> {

    private final MockEmailService mockEmailService;

    public EmailWriter(MockEmailService mockEmailService){
        this.mockEmailService = mockEmailService;
    }

    @Override
    public void write(Chunk<? extends String> emails){
        for (String email : emails){
            mockEmailService.sendMockEmail(email);
        }
    }
}
