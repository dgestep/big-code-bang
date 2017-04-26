package ${topLevelDomain}.${companyName}.${productName}.model.repository;

import ${topLevelDomain}.${companyName}.${productName}.model.log.LogFactory;
import ${topLevelDomain}.${companyName}.${productName}.model.log.Logger;
import junit.framework.TestCase;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(JMockit.class)
public class TestDataSet extends TestCase {
    @Tested
    private DataSet sut;

    @Injectable
    private ResultSet resultSet;

    @Mocked
    private ResultSetManager resultSetManager;

    @Mocked
    private Logger logger;

    @Mocked
    private LogFactory logFactory;

    @Before
    @Override
    public void setUp() {

        new Expectations() {
            {
                LogFactory.getLogger();
                result = logger;

                resultSetManager.hasColumn(anyString);
                result = true;
            }
        };

        sut = new DataSet();
    }

    @Test
    public void testSuperClassesAnnotationOnAccessorMethods() {
        new Expectations() {
            {
                resultSetManager.getObject("EMPLOYEE_NAME");
                result = "Gomer Pyle";

                resultSetManager.getObject("EMPLOYEE_ID");
                result = 20;

                resultSetManager.getObject("ACTIVE_FLG");
                result = true;

                resultSetManager.getObject("SUPERIOR_FLG");
                result = Boolean.TRUE;

                resultSetManager.getObject("MANAGER_BONUS");
                result = new BigDecimal(10000);
            }
        };

        ManagerBean manager = sut.instanceOf(ManagerBean.class, resultSet);

        Assert.assertEquals(20, manager.getId());
        Assert.assertEquals("Gomer Pyle", manager.getName());
        Assert.assertEquals(new BigDecimal(10000), manager.getBonus());
    }

    @Test
    public void testDeepDive() {
        final Date eventDate = Date
                .from(LocalDateTime.of(2016, 10, 7, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        final Date updateDate = Date
                .from(LocalDateTime.of(2016, 10, 8, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        new Expectations() {
            {
                resultSetManager.getObject("linkStdUid");
                result = 1000;

                resultSetManager.getObject("linkStdSeasonId");
                result = 71;

                resultSetManager.getObject("linkStdEventDate");
                result = eventDate;

                resultSetManager.getObject("linkStdUpdateDate");
                result = updateDate;

                resultSetManager.getObject("linkStdTeam1Score");
                result = 10;

                resultSetManager.getObject("linkStdTeam2Score");
                result = 3;

                resultSetManager.getObject("t1_tmTeamId");
                result = 17;

                resultSetManager.getObject("t1_tmTeamName");
                result = "Cincinnati Reds";

                resultSetManager.getObject("t1_tmAkaTeamName");
                result = "Reds";

                resultSetManager.getObject("t1_tmTagName");
                result = "CINC";

                resultSetManager.getObject("t1_sptSportId");
                result = 90;

                resultSetManager.getObject("t1_sptSportName");
                result = "MLB";

                resultSetManager.getObject("t1_sptGenderCode");
                result = "M";

                resultSetManager.getObject("t1_sptLevelCode");
                result = "P";

                resultSetManager.getObject("t1_sptFeedUrl");
                result = null;

                resultSetManager.getObject("t1_sptActiveFlag");
                result = "Y";

                resultSetManager.getObject("t1_cnfConferenceId");
                result = 1;

                resultSetManager.getObject("t1_cnfConferenceName");
                result = "National League";

                resultSetManager.getObject("t1_cnfTagName");
                result = "NL";

                resultSetManager.getObject("t1_cnfTrackScoresFlag");
                result = "Y";

                resultSetManager.getObject("t1_divDivisionId");
                result = 2;

                resultSetManager.getObject("t1_divDivisionName");
                result = "EAST";

                resultSetManager.getObject("t2_tmTeamId");
                result = 30;

                resultSetManager.getObject("t2_tmTeamName");
                result = "Cleveland Indians";

                resultSetManager.getObject("t2_tmAkaTeamName");
                result = "Indians";

                resultSetManager.getObject("t2_tmTagName");
                result = "CLEV";

                resultSetManager.getObject("t2_sptSportId");
                result = 90;

                resultSetManager.getObject("t2_sptSportName");
                result = "MLB";

                resultSetManager.getObject("t2_sptGenderCode");
                result = "M";

                resultSetManager.getObject("t2_sptLevelCode");
                result = "P";

                resultSetManager.getObject("t2_sptFeedUrl");
                result = null;

                resultSetManager.getObject("t2_sptActiveFlag");
                result = "Y";

                resultSetManager.getObject("t2_cnfConferenceId");
                result = 1;

                resultSetManager.getObject("t2_cnfConferenceName");
                result = "National League";

                resultSetManager.getObject("t2_cnfTagName");
                result = "NL";

                resultSetManager.getObject("t2_cnfTrackScoresFlag");
                result = "Y";

                resultSetManager.getObject("t2_divDivisionId");
                result = 2;

                resultSetManager.getObject("t2_divDivisionName");
                result = "EAST";
            }
        };

        Map<String, String> aliases = new HashMap<>();
        aliases.put("team1", "t1");
        aliases.put("team2", "t2");

        SeasonTeamDataBean data = sut.instanceOf(SeasonTeamDataBean.class, resultSet, aliases);

        Assert.assertEquals(1000, data.getUid());
        Assert.assertEquals(71, data.getSeasonId());
        Assert.assertEquals(eventDate, data.getEventDate());
        Assert.assertEquals(updateDate, data.getUpdateDate());
        Assert.assertEquals(10, data.getTeam1Score());
        Assert.assertEquals(3, data.getTeam2Score());

        TeamBean team1 = data.getTeam1();
        Assert.assertNotNull(team1);
        Assert.assertEquals(17, team1.getTeamId());
        Assert.assertEquals("Reds", team1.getAkaTeamName());
        Assert.assertEquals("Cincinnati Reds", team1.getTeamName());
        Assert.assertEquals("CINC", team1.getTagName());

        SportBean sport1 = team1.getSport();
        assertSport(sport1);

        ConferenceBean conference1 = team1.getConference();
        assertConference(conference1);

        DivisionBean division1 = team1.getDivision();
        assertDivision(division1);

        TeamBean team2 = data.getTeam2();
        Assert.assertNotNull(team2);
        Assert.assertEquals(30, team2.getTeamId());
        Assert.assertEquals("Indians", team2.getAkaTeamName());
        Assert.assertEquals("Cleveland Indians", team2.getTeamName());
        Assert.assertEquals("CLEV", team2.getTagName());

        SportBean sport2 = team2.getSport();
        assertSport(sport2);

        ConferenceBean conference2 = team2.getConference();
        assertConference(conference2);

        DivisionBean division2 = team2.getDivision();
        assertDivision(division2);

        System.out.println(data.toString());
    }

    private void assertSport(final SportBean sport) {
        Assert.assertNotNull(sport);

        Assert.assertEquals(90, sport.getSportId());
        Assert.assertEquals("MLB", sport.getSportName());
        Assert.assertEquals(SportLevel.PROFESSIONAL, sport.getLevel());
        Assert.assertNull(sport.getFeedUrl());
        Assert.assertEquals(SportGender.MALE, sport.getGender());
        Assert.assertTrue(sport.isActive());
    }

    private void assertConference(final ConferenceBean conference) {
        Assert.assertNotNull(conference);

        Assert.assertEquals(1, conference.getConferenceId());
        Assert.assertEquals("National League", conference.getConferenceName());
        Assert.assertEquals("NL", conference.getTagName());

        SportBean sport = conference.getSport();
        assertSport(sport);
    }

    private void assertDivision(final DivisionBean division) {
        Assert.assertNotNull(division);

        Assert.assertEquals(2, division.getDivisionId());
        Assert.assertEquals("EAST", division.getDivisionName());

        SportBean sport = division.getSport();
        assertSport(sport);
    }

    public enum SportGender {
        MALE("M", "Male", 1), FEMALE("F", "Female", 2), COED("C", "Coed", 3);

        private String code;
        private String displayName;
        private int displayOrder;

        SportGender(final String code, final String displayName, final int displayOrder) {
            this.code = code;
            this.displayName = displayName;
            this.displayOrder = displayOrder;
        }

        public static SportGender toSportGender(final String code) {
            if (StringUtils.isEmpty(code)) {
                return null;
            }

            SportGender e = null;
            if (code.equalsIgnoreCase("M")) {
                e = SportGender.MALE;
            } else if (code.equalsIgnoreCase("F")) {
                e = SportGender.FEMALE;
            } else if (code.equalsIgnoreCase("C")) {
                e = SportGender.COED;
            }
            return e;
        }

        public String getCode() {
            return code;
        }

        public String getDisplayName() {
            return displayName;
        }

        public int getDisplayOrder() {
            return displayOrder;
        }
    }

    public enum SportLevel {
        PROFESSIONAL("P", "Professional", 1);

        private String code;
        private String displayName;
        private int displayOrder;

        SportLevel(final String code, final String displayName, final int displayOrder) {
            this.code = code;
            this.displayName = displayName;
            this.displayOrder = displayOrder;
        }

        public static SportLevel toLevelCode(final String code) {
            if (StringUtils.isEmpty(code)) {
                return null;
            }

            SportLevel e = null;
            if (code.equalsIgnoreCase("P")) {
                e = SportLevel.PROFESSIONAL;
            }
            return e;
        }

        public String getCode() {
            return code;
        }

        public String getDisplayName() {
            return displayName;
        }

        public int getDisplayOrder() {
            return displayOrder;
        }
    }

    public static class EmployeeBean {
        private int id;
        private String name;
        private boolean active;
        private Boolean superiorFlg;

        public EmployeeBean() {
        }

        @Column(name = "EMPLOYEE_ID")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Column(name = "EMPLOYEE_NAME")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Column(name = "ACTIVE_FLG")
        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        @Column(name = "SUPERIOR_FLG")
        public Boolean getSuperiorFlg() {
            return superiorFlg;
        }

        public void setSuperiorFlg(Boolean superiorFlg) {
            this.superiorFlg = superiorFlg;
        }
    }

    public static class ManagerBean extends EmployeeBean {
        private BigDecimal bonus;

        public ManagerBean() {
        }

        @Column(name = "MANAGER_BONUS")
        public BigDecimal getBonus() {
            return bonus;
        }

        public void setBonus(BigDecimal bonus) {
            this.bonus = bonus;
        }
    }

    public static class ConferenceBean {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "cnfConferenceId", nullable = false)
        private int conferenceId;

        @Column(name = "cnfConferenceName")
        private String conferenceName;

        @Column(name = "cnfTagName")
        private String tagName;

        @Column(name = "cnfTrackScoresFlag")
        private String trackScoresFlag;

        @OneToOne(targetEntity = SportBean.class, cascade = CascadeType.ALL)
        @JoinColumn(name = "cnfSportId", referencedColumnName = "sptSportId")
        private SportBean sport;

        public ConferenceBean() {
        }

        public ConferenceBean(final int conferenceId) {
            setConferenceId(conferenceId);
        }

        public int getConferenceId() {
            return conferenceId;
        }

        public void setConferenceId(final int conferenceId) {
            this.conferenceId = conferenceId;
        }

        public String getConferenceName() {
            return conferenceName;
        }

        public void setConferenceName(final String conferenceName) {
            this.conferenceName = conferenceName;
        }

        public SportBean getSport() {
            return sport;
        }

        public void setSport(final SportBean sport) {
            this.sport = sport;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(final String tagName) {
            this.tagName = tagName;
        }

        public boolean isTrackScores() {
            return "Y".equals(trackScoresFlag);
        }

        public void setTrackScoresFlag(String trackStoresFlag) {
            this.trackScoresFlag = trackStoresFlag;
        }
    }

    public static class DivisionBean {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "divDivisionId", nullable = false)
        private int divisionId;

        @Column(name = "divDivisionName")
        private String divisionName;

        // JoinColumn says that this entity has the foreign key to the reference table/column
        @OneToOne(targetEntity = SportBean.class, fetch = FetchType.EAGER)
        @JoinColumn(name = "divSportId", referencedColumnName = "sptSportId")
        private SportBean sport;

        public DivisionBean() {
        }

        public DivisionBean(final int divisionId) {
            setDivisionId(divisionId);
        }

        public int getDivisionId() {
            return divisionId;
        }

        public void setDivisionId(final int divisionId) {
            this.divisionId = divisionId;
        }

        public String getDivisionName() {
            return divisionName;
        }

        public void setDivisionName(final String divisionName) {
            this.divisionName = divisionName;
        }

        public SportBean getSport() {
            return sport;
        }

        public void setSport(final SportBean sport) {
            this.sport = sport;
        }
    }

    private static class SportBean {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "sptSportId", nullable = false)
        private int sportId;

        @Column(name = "sptSportName", nullable = false)
        private String sportName;

        @Column(name = "sptGenderCode")
        private String genderCode;

        @Column(name = "sptLevelCode")
        private String levelCode;

        @Column(name = "sptFeedUrl")
        private String feedUrl;

        @Column(name = "sptActiveFlag")
        private String activeFlag;

        public SportBean() {
        }

        public SportBean(final int sportId) {
            setSportId(sportId);
        }

        public int getSportId() {
            return sportId;
        }

        public void setSportId(final int sportId) {
            this.sportId = sportId;
        }

        public String getSportName() {
            return sportName;
        }

        public void setSportName(final String sportName) {
            this.sportName = sportName;
        }

        public SportGender getGender() {
            return SportGender.toSportGender(genderCode);
        }

        public void setGender(final SportGender gender) {
            this.genderCode = gender.getCode();
        }

        public SportLevel getLevel() {
            return SportLevel.toLevelCode(levelCode);
        }

        public void setLevel(final SportLevel level) {
            this.levelCode = level.getCode();
        }

        public String getFeedUrl() {
            return feedUrl;
        }

        public void setFeedUrl(final String feedUrl) {
            this.feedUrl = feedUrl;
        }

        public boolean isActive() {
            return StringUtils.equalsIgnoreCase(activeFlag, "Y");
        }

        public void setActive(final boolean active) {
            this.activeFlag = active ? "Y" : "N";
        }
    }

    public static class TeamBean {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "tmTeamId", nullable = false)
        private int teamId;

        @Column(name = "tmTeamName", nullable = false)
        private String teamName;

        @Column(name = "tmAkaTeamName")
        private String akaTeamName;

        @OneToOne(targetEntity = SportBean.class, cascade = CascadeType.ALL)
        @JoinColumn(name = "tmSportId", referencedColumnName = "sptSportId")
        private SportBean sport;

        @OneToOne(targetEntity = ConferenceBean.class, cascade = CascadeType.ALL)
        @JoinColumn(name = "tmConferenceId", referencedColumnName = "cnfConferenceId")
        private ConferenceBean conference;

        @OneToOne(targetEntity = DivisionBean.class, cascade = CascadeType.ALL)
        @JoinColumn(name = "tmDivisionId", referencedColumnName = "divDivisionId")
        private DivisionBean division;

        @Column(name = "tmTagName")
        private String tagName;

        public TeamBean() {
        }

        public TeamBean(final int teamId) {
            setTeamId(teamId);
        }

        public String getConferenceAndDivision() {
            if (this.conference == null || this.division == null) {
                return "";
            }

            return StringUtils.defaultString(conference.getConferenceName()) + " / " + StringUtils
                    .defaultString(division.getDivisionName());
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(final int teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(final String teamName) {
            this.teamName = teamName;
        }

        public String getAkaTeamName() {
            return akaTeamName;
        }

        public void setAkaTeamName(final String akaTeamName) {
            this.akaTeamName = akaTeamName;
        }

        public SportBean getSport() {
            return sport;
        }

        public void setSport(final SportBean sport) {
            this.sport = sport;
        }

        public ConferenceBean getConference() {
            return conference;
        }

        public void setConference(final ConferenceBean conference) {
            this.conference = conference;
        }

        public DivisionBean getDivision() {
            return division;
        }

        public void setDivision(final DivisionBean division) {
            this.division = division;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(final String tagName) {
            this.tagName = tagName;
        }
    }

    public static class SeasonTeamDataBean {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "linkStdUid", nullable = false)
        private int uid;

        @Column(name = "linkStdSeasonId", nullable = false)
        private int seasonId;

        @OneToOne(targetEntity = TeamBean.class, fetch = FetchType.EAGER)
        @JoinColumn(name = "linkStdTeam1Id", referencedColumnName = "tmTeamId")
        private TeamBean team1;

        @OneToOne(targetEntity = TeamBean.class, fetch = FetchType.EAGER)
        @JoinColumn(name = "linkStdTeam2Id", referencedColumnName = "tmTeamId")
        private TeamBean team2;

        @Column(name = "linkStdEventDate")
        private Date eventDate;
        @Transient
        private String eventDisplayDate;

        @Column(name = "linkStdUpdateDate")
        private Date updateDate;

        @Column(name = "linkStdTeam1Score")
        private int team1Score;

        @Column(name = "linkStdTeam2Score")
        private int team2Score;

        public SeasonTeamDataBean() {
        }

        public SeasonTeamDataBean(final int uid) {
            setUid(uid);
        }

        public int getSeasonId() {
            return seasonId;
        }

        public void setSeasonId(final int seasonId) {
            this.seasonId = seasonId;
        }

        public Date getEventDate() {
            return eventDate;
        }

        public void setEventDate(final Date eventDate) {
            this.eventDate = eventDate;
        }

        public String getEventDisplayDate() {
            return eventDisplayDate;
        }

        public TeamBean getTeam1() {
            return team1;
        }

        public void setTeam1(final TeamBean team1) {
            this.team1 = team1;
        }

        public TeamBean getTeam2() {
            return team2;
        }

        public void setTeam2(final TeamBean team2) {
            this.team2 = team2;
        }

        public Date getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(final Date updateDate) {
            this.updateDate = updateDate;
        }

        public int getTeam1Score() {
            return team1Score;
        }

        public void setTeam1Score(final int team1Score) {
            this.team1Score = team1Score;
        }

        public int getTeam2Score() {
            return team2Score;
        }

        public void setTeam2Score(final int team2Score) {
            this.team2Score = team2Score;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(final int uid) {
            this.uid = uid;
        }
    }
}
