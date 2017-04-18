package com.${companyName}.${productName}.model.service;

import com.${companyName}.${productName}.model.data.MessageData;
import com.${companyName}.${productName}.model.exception.DataInputException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class TestEntityAssertion extends TestCase {
    private EntityAssertion sut;

    @Before
    @Override
    public void setUp() {
        sut = new EntityAssertion();
    }

    @Test
    public void testNullAndMaxStringAssertions() {
        MainEntityPk key = createMainEntityPk("123456789012345", null);
        MainEntity entity = createMainEntity(key, new Time(new Date().getTime()), null);

        EntityAssertion sut = new EntityAssertion();
        try {
            sut.evaluate(entity);
            fail("Should have failed!");
        }
        catch (DataInputException die) {
            List<MessageData> messages = die.getMessageData();
            int size = messages.size();
            Assert.assertEquals(2, size);
            for (MessageData message : messages) {
                String columnName = message.getColumnName();
                String messageText = message.getMessage();
                if (columnName.equalsIgnoreCase("KEY_1")) {
                    Assert.assertEquals("G005", message.getCode());
                    System.out.println(messageText);
                    Assert.assertTrue("Wrong message", messageText.startsWith("Value too large for column"));
                } else if (columnName.equalsIgnoreCase("KEY_2")) {
                    Assert.assertEquals("G001", message.getCode());
                    System.out.println(messageText);
                    Assert.assertTrue("Wrong message", messageText.startsWith("A value is required"));
                } else {
                    fail("Unaccounted for field: " + columnName + " - " + messageText);
                }
            }
        }
    }

    @Test
    public void testPassedAssertions() {
        MainEntityPk key = createMainEntityPk("1234567890", "12345");
        MainEntity entity = createMainEntity(key, new Time(new Date().getTime()), 51);

        EntityAssertion sut = new EntityAssertion();
        try {
            sut.evaluate(entity);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            fail(exception.getMessage());
        }
    }

    @Test
    public void testEntityClassWithNoColumnAnnotations() {
        EntityClassNoColumns entity = new EntityClassNoColumns();
        entity.setName("Test");
        EntityAssertion sut = new EntityAssertion();
        try {
            sut.evaluate(entity);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            fail(exception.getMessage());
        }
    }

    @Test
    public void testSuperClassAssertion() {
        ManagerBean manager = new ManagerBean();
        manager.setName("123456789012345678901");
        manager.setId(1);
        manager.setBonus(null);

        EntityAssertion sut = new EntityAssertion();
        try {
            sut.evaluate(manager);
            fail("Should have failed!");
        }
        catch (DataInputException die) {
            List<MessageData> messages = die.getMessageData();
            int size = messages.size();
            Assert.assertEquals(2, size);
            for (MessageData message : messages) {
                String columnName = message.getColumnName();
                String messageText = message.getMessage();
                if (columnName.equalsIgnoreCase("EMPLOYEE_NAME")) {
                    Assert.assertEquals("G005", message.getCode());
                    System.out.println(messageText);
                    Assert.assertTrue("Wrong message", messageText.startsWith("Value too large for column"));
                } else if (columnName.equalsIgnoreCase("MANAGER_BONUS")) {
                    Assert.assertEquals("G001", message.getCode());
                    System.out.println(messageText);
                    Assert.assertTrue("Wrong message", messageText.startsWith("A value is required"));
                } else {
                    fail("Unaccounted for field: " + columnName + " - " + messageText);
                }
            }
        }
    }

    @Test
    public void testEntityWithOptionalStringAttribute() {
        EntityDefaultString entity = new EntityDefaultString();
        entity.setName("James Tiberious Kirk");

        EntityAssertion sut = new EntityAssertion();
        try {
            sut.evaluate(entity);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            fail(exception.getMessage());
        }
    }

    private MainEntity createMainEntity(MainEntityPk key, Time time, Integer intt) {
        MainEntity entity = new MainEntity();
        entity.setMainKey(key);
        entity.setCovBeginFmt(intt);
        entity.setDateCreated(time);

        return entity;
    }

    private MainEntityPk createMainEntityPk(String key1, String key2) {
        MainEntityPk key = new MainEntityPk();
        key.setKey1(key1);
        key.setKey2(key2);
        return key;
    }

    @Entity
    public static class EntityDefaultString {
        @Column(name = "name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class NonEntityClass {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Entity
    public static class EntityClassNoColumns {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Entity
    @Table(name = "BOGUS_TABLE", schema = "TESTME", catalog = "")
    public static class MainEntity implements Serializable {
        private static final long serialVersionUID = -8164292230702467044L;

        @EmbeddedId
        private MainEntityPk mainKey;
        @Column(name = "DATE_CREATED", nullable = false)
        private Time dateCreated;
        @Column(name = "MY_INT", nullable = true, precision = 0)
        private Integer covBeginFmt;

        public MainEntityPk getMainKey() {
            return mainKey;
        }

        public void setMainKey(MainEntityPk mainKey) {
            this.mainKey = mainKey;
        }

        public Time getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(Time dateCreated) {
            this.dateCreated = dateCreated;
        }

        public Integer getCovBeginFmt() {
            return covBeginFmt;
        }

        public void setCovBeginFmt(Integer covBeginFmt) {
            this.covBeginFmt = covBeginFmt;
        }
    }

    @Embeddable
    public static class MainEntityPk {
        @Column(name = "KEY_1", nullable = false, length = 10)
        private String key1;
        @Column(name = "KEY_2", nullable = false, length = 5)
        private String key2;

        public String getKey1() {
            return key1;
        }

        public void setKey1(String key1) {
            this.key1 = key1;
        }

        public String getKey2() {
            return key2;
        }

        public void setKey2(String key2) {
            this.key2 = key2;
        }
    }

    @Entity
    public static class EmployeeBean {
        @Id
        @Column(name = "EMPLOYEE_ID", nullable = false)
        private int id;

        @Column(name = "EMPLOYEE_NAME", nullable = false, length = 20)
        private String name;

        public EmployeeBean() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Entity
    public static class ManagerBean extends EmployeeBean {
        @Column(name = "MANAGER_BONUS", nullable = false)
        private BigDecimal bonus;

        public ManagerBean() {
        }

        public BigDecimal getBonus() {
            return bonus;
        }

        public void setBonus(BigDecimal bonus) {
            this.bonus = bonus;
        }
    }
}
