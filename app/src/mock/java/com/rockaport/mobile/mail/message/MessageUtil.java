package com.rockaport.mobile.mail.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.svenjacobs.loremipsum.LoremIpsum;

public class MessageUtil {
    private static Random random = new Random();

    public static Message generateRandomMessage() {
        Message message = new Message();

        message.setType(random.nextBoolean() ? Message.TYPE.INCOMING : Message.TYPE.OUTGOING);
        message.setStatus(random.nextBoolean() ? Message.STATUS.DRAFT : Message.STATUS.NONE);
        message.setMessage(new LoremIpsum().getParagraphs(random.nextInt(3) + 1));
        message.setDateTime(System.currentTimeMillis());

        int numAttachments = random.nextInt(10);
        for (int i = 0; i < numAttachments; i++) {
            message.getAttachments().add(generateRandomAttachment());
        }

        return message;
    }

    public static List<Message> generateRandomMessages() {
        return generateRandomMessages(10);
    }

    public static List<Message> generateRandomMessages(int count) {
        List<Message> messages = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            messages.add(generateRandomMessage());
        }

        return messages;
    }

    public static Attachment generateRandomAttachment() {
        Attachment attachment = new Attachment();

        attachment.setFileName(new LoremIpsum().getWords(1, random.nextInt(49)));
        attachment.setPath(new LoremIpsum().getWords(random.nextInt(5) + 1, random.nextInt(49)).replace(' ', '/'));
        attachment.setSize(random.nextInt());

        return attachment;
    }
}
