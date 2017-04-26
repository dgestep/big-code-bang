package ${topLevelDomain}.${companyName}.${productName}.model.exception;

import ${topLevelDomain}.${companyName}.${productName}.model.data.MessageData;
import junit.framework.TestCase;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(JMockit.class)
public class TestDataAccessException extends TestCase {
    private DataInputException sut;

    @Test
    public void testEmptyConstructor() {
        sut = new DataInputException();
        Assert.assertNull(sut.getMessage());
        Assert.assertNull(sut.getFirstMessageData());
        Assert.assertNull(sut.getMessageData());
        Assert.assertNull(sut.getMessageData(""));
        Assert.assertNull(sut.getMessagesAsString(System.getProperty("line.separator")));

        sut.setColumnNameToMessageData("409", "column-name");
        Assert.assertNull(sut.getMessageData());
    }

    @Test
    public void testConstructWithMessage() {
        sut = new DataInputException("failed");
        Assert.assertEquals("failed", sut.getMessage());
    }

    @Test
    public void testConstructWithMessageException() {
        Throwable thr = new RuntimeException("I want to fail");
        sut = new DataInputException("failed", thr);
        Assert.assertEquals("I want to fail", thr.getMessage());
        Assert.assertEquals(thr, sut.getCause());
    }

    @Test
    public void testConstructWithException() {
        Throwable thr = new RuntimeException("I want to fail");
        sut = new DataInputException(thr);
        Assert.assertEquals("I want to fail", thr.getMessage());
    }

    @Test
    public void testConstructWithMessageCode() {
        sut = new DataInputException("failed", "409");
        Assert.assertEquals("failed", sut.getMessage());

        MessageData message = new MessageData(sut.getMessage(), "409");
        Assert.assertEquals(message, sut.getFirstMessageData());
    }

    @Test
    public void testConstructWithMessageCodeColumn() {
        sut = new DataInputException("failed", "409", "column-name");
        Assert.assertEquals("failed", sut.getMessage());

        MessageData message = new MessageData(sut.getMessage(), "409");

        Assert.assertEquals(message, sut.getFirstMessageData());
        Assert.assertEquals("column-name", sut.getFirstMessageData().getColumnName());
    }

    @Test
    public void testConstructWithMessageData() {
        MessageData message = new MessageData("failed", "409");
        sut = new DataInputException(message);
        Assert.assertEquals("failed", sut.getMessage());

        MessageData expected = new MessageData(sut.getMessage(), "409");

        Assert.assertEquals(expected, sut.getFirstMessageData());
    }

    @Test
    public void testConstructWithListOfMessageData() {
        MessageData message1 = new MessageData("failed1", "409");
        MessageData message2 = new MessageData("failed2", "410");
        List<MessageData> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        sut = new DataInputException(messages);
        Assert.assertEquals(sut.getMessagesAsString(System.getProperty("line.separator")), sut.getMessage());

        List<MessageData> expected = sut.getMessageData();
        Assert.assertNotNull(expected);
        Assert.assertEquals(2, expected.size());

        MessageData expected1 = expected.get(0);
        Assert.assertEquals(message1, expected1);

        MessageData expected2 = expected.get(1);
        Assert.assertEquals(message2, expected2);
    }

    @Test
    public void testGetMessageDataByCodeExpectFound() {
        MessageData message = new MessageData("failed", "409");
        sut = new DataInputException(message);

        MessageData expected = sut.getMessageData("409");
        Assert.assertNotNull(expected);
        Assert.assertEquals(message, expected);
    }

    @Test
    public void testGetMessageDataByCodeExpectNotFound() {
        MessageData message = new MessageData("failed", "409");
        sut = new DataInputException(message);

        MessageData expected = sut.getMessageData("666");
        Assert.assertNull(expected);
    }

    @Test
    public void testSetColumnNameToMessageData() {
        MessageData message = new MessageData("failed", "409");
        sut = new DataInputException(message);

        Assert.assertNull(sut.getFirstMessageData().getColumnName());
        sut.setColumnNameToMessageData("409", "column-name");
        Assert.assertEquals("column-name", sut.getFirstMessageData().getColumnName());
    }
}
