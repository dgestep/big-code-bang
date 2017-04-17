package com.${companyName}.${productName}.model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.${companyName}.${productName}.model.data.MessageData;
import com.${companyName}.${productName}.model.enumeration.message.GeneralMessage;
import com.${companyName}.${productName}.model.exception.SystemLoggedException;
import junit.framework.TestCase;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(JMockit.class)
public class TestJsonResponseData extends TestCase {

    @Test
    public void testConstructionStringMessageNullValue() {
        JsonResponseData sut = new JsonResponseData(500, "failed");
        Assert.assertNull(sut.getData());

        Assert.assertEquals(500, sut.getStatusCode());

        List<MessageData> messages = sut.getMessages();
        Assert.assertNotNull(messages);

        MessageData message = messages.get(0);
        Assert.assertEquals("failed", message.getMessage());
        Assert.assertEquals("500", message.getCode());
        Assert.assertNull(message.getColumnName());
    }

    @Test
    public void testConstructionMessageDataNullValue() {
        MessageData message = new MessageData("failed", "500");

        JsonResponseData sut = new JsonResponseData(500, message);
        Assert.assertNull(sut.getData());

        Assert.assertEquals(500, sut.getStatusCode());

        List<MessageData> messages = sut.getMessages();
        Assert.assertNotNull(messages);

        Assert.assertEquals(message, messages.get(0));
    }

    @Test
    public void testConstructionMessageListNullValue() {
        List<MessageData> messages = new ArrayList<>();
        MessageData message = new MessageData("failed", "500");
        messages.add(message);

        JsonResponseData sut = new JsonResponseData(500, messages);
        Assert.assertNull(sut.getData());

        Assert.assertEquals(500, sut.getStatusCode());

        Assert.assertNotNull(sut.getMessages());
        Assert.assertEquals(message, sut.getMessages().get(0));
    }

    @Test
    public void testConstructionServiceMessageNullValue() {
        JsonResponseData sut = new JsonResponseData(500, GeneralMessage.G001);
        Assert.assertNull(sut.getData());

        Assert.assertEquals(500, sut.getStatusCode());

        Assert.assertNotNull(sut.getMessages());
        Assert.assertEquals("G001", sut.getMessages().get(0).getCode());
    }

    @Test
    public void testEqualsHashCodeToString() {
        JsonResponseData sut = new JsonResponseData(500, GeneralMessage.G001);
        Assert.assertTrue(sut.equals(sut));

        Assert.assertTrue(sut.equals(new JsonResponseData(500, GeneralMessage.G001)));
        Assert.assertFalse(sut.equals("huh"));

        Assert.assertTrue(sut.hashCode() != 0);
        Assert.assertTrue(sut.toString().length() > 0);
    }

    @Test
    public void testToJsonNoExclusions() {
        MessageData message = new MessageData(GeneralMessage.G001, "my test case");

        JsonResponseData sut = new JsonResponseData(500, message);
        String json = JsonResponseData.toJson(sut);
        Assert.assertEquals("{\"statusCode\":500,\"messages\":[{\"code\":\"G001\",\"message\":\"A value is required "
                + "for my test case.\"}]}", json);
    }

    @Test
    public void testToJsonWithExclusions() {
        MessageData message = new MessageData(GeneralMessage.G001, "my test case");

        JsonResponseData sut = new JsonResponseData(500, message);
        String json = JsonResponseData.toJson(sut, new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().equals("statusCode");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        Assert.assertEquals("{\"messages\":[{\"code\":\"G001\",\"message\":\"A value is required "
                + "for my test case.\"}]}", json);
    }

    @Test
    public void testToJsonExpectionException() {
        try {
            MessageData message = new MessageData(GeneralMessage.G001, "my test case");
            JsonResponseData sut = new JsonResponseData(500, message);
            JsonResponseData.toJson(sut, new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    if (f != null) {
                        throw new RuntimeException("I want to fail");
                    }
                    return false;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            });
            fail("Should have failed");
        } catch (SystemLoggedException sle) {
            System.out.println(sle.getMessage());
        }
    }
}
