//package VotingApp.driver;
//
//import VotingApp.*;
//import VotingApp.poll.Poll;
//import VotingApp.poll.PollDAO;
//import VotingApp.user.User;
//import VotingApp.user.UserDAO;
//import VotingApp.vote.Vote;
//import VotingApp.vote.VoteDAO;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//import java.sql.Timestamp;
//
//public class PopulateTestData {
//
//    public static class VotingAppMain {
//
//        static final String PERSISTENCE_UNIT_NAME = "VotingApp";
//
//        public static void main(String[] args) {
//            EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//            EntityManager em = factory.createEntityManager();
//
//            try {
//                em.getTransaction().begin();
//
//                PopulateTestData.addUsers(em);
//                PopulateTestData.addPolls(em);
//                PopulateTestData.addVotes(em);
//                PopulateTestData.addIoTDevices(em);
//                PopulateTestData.addNotifications(em);
//                PopulateTestData.addAnalytics(em);
//
//                em.getTransaction().commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//                em.getTransaction().rollback(); // Rollback in case of an error
//            } finally {
//                em.close();
//                factory.close();
//            }
//        }
//    }
//
//
//    public static void addUsers(EntityManager em) {
//
//        UserDAO userDAO = new UserDAO();
//        userDAO.setEntityManager(em);
//
//        User admin = new User();
//        admin.setName("Andre Normann");
//        admin.setEmail("andre.normann@hotmail.com");
//        admin.setPassword("abc123");
//        admin.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
//        admin.setLastLoginDate(new java.sql.Timestamp(System.currentTimeMillis()));
//        admin.setIsAdmin(true);
//
//        User user2 = new User();
//        user2.setName("Magnus Aune");
//        user2.setEmail("magnus.aune@hotmail.com");
//        user2.setPassword("123abc");
//        user2.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
//        user2.setLastLoginDate(new java.sql.Timestamp(System.currentTimeMillis()));
//        user2.setIsAdmin(false);
//
//        User user3 = new User();
//        user3.setName("Mathias Larsen");
//        user3.setEmail("Mathias.Larsen@hotmail.com");
//        user3.setPassword("passord123");
//        user3.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
//        user3.setLastLoginDate(new java.sql.Timestamp(System.currentTimeMillis()));
//        user3.setIsAdmin(false);
//
//        User user4 = new User();
//        user4.setName("Stian Munkejord");
//        user4.setEmail("stian.munkejord@hotmail.com");
//        user4.setPassword("123passord");
//        user4.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
//        user4.setLastLoginDate(new java.sql.Timestamp(System.currentTimeMillis()));
//        user4.setIsAdmin(false);
//
//        userDAO.addUser(admin);
//        userDAO.addUser(user2);
//        userDAO.addUser(user3);
//        userDAO.addUser(user4);
//    }
//
//    public static void addPolls(EntityManager em) {
//        PollDAO pollDAO = new PollDAO();
//        pollDAO.setEntityManager(em);
//
//        User admin = em.find(User.class, 1L);
//        User user2 = em.find(User.class, 2L);
//
//        Poll poll1 = new Poll();
//        poll1.setTitle("Arbeidsmengde DAT250");
//        poll1.setQuestion("Er det for mye å gjøre i DAT250?");
//        poll1.setCreatedBy(admin);
//        poll1.setStartDateTime(new Timestamp(System.currentTimeMillis()));
//        poll1.setEndDateTime(new Timestamp(System.currentTimeMillis() + 86400000));
//        poll1.setIsPublic(true);
//        poll1.setPollLink("https://pollapp.com/poll1");
//        poll1.setPollCode("POLL001");
//        pollDAO.addPoll(poll1);
//
//        Poll poll2 = new Poll();
//        poll1.setTitle("Arbeidsmengde DAT250");
//        poll1.setQuestion("Er det for mye å gjøre i DAT250?");
//        poll1.setCreatedBy(user2);
//        poll1.setStartDateTime(new Timestamp(System.currentTimeMillis()));
//        poll1.setEndDateTime(new Timestamp(System.currentTimeMillis() + 86400000));
//        poll1.setIsPublic(true);
//        poll1.setPollLink("https://pollapp.com/poll1");
//        poll1.setPollCode("POLL001");;
//        pollDAO.addPoll(poll2);
//    }
//
//    public static void addVotes(EntityManager em) {
//
//        VoteDAO voteDAO = new VoteDAO();
//        voteDAO.setEntityManager(em);
//
//        User user2 = em.find(User.class, 2L);
//        User user3 = em.find(User.class, 3L);
//        User user4 = em.find(User.class, 4L);
//        Poll poll1 = em.find(Poll.class, 1L);
//        Poll poll2 = em.find(Poll.class, 2L);
//
//        Vote vote1 = new Vote();
//        vote1.setPoll(poll1);
//        vote1.setUser(user2);
//        vote1.setChoice("Green");
//
//        Vote vote2 = new Vote();
//        vote2.setPoll(poll1);
//        vote2.setUser(user3);
//        vote2.setChoice("Green");
//
//        Vote vote3 = new Vote();
//        vote3.setPoll(poll1);
//        vote3.setUser(user4);
//        vote3.setChoice("Green");
//
//        Vote vote4 = new Vote();
//        vote4.setPoll(poll2);
//        vote4.setUser(user2);
//        vote4.setChoice("Red");
//
//        Vote vote5 = new Vote();
//        vote5.setPoll(poll2);
//        vote5.setUser(user3);
//        vote5.setChoice("Green");
//
//        Vote vote6 = new Vote();
//        vote6.setPoll(poll2);
//        vote6.setUser(user4);
//        vote6.setChoice("Red");
//
//        voteDAO.addVote(vote1);
//        voteDAO.addVote(vote2);
//        voteDAO.addVote(vote3);
//        voteDAO.addVote(vote4);
//        voteDAO.addVote(vote5);
//        voteDAO.addVote(vote6);
//    }
//
//    public static void addIoTDevices(EntityManager em) {
//
//        Poll poll1 = em.find(Poll.class, 1L);
//        Poll poll2 = em.find(Poll.class, 2L);
//
//        IoTDevice device1 = new IoTDevice();
//        device1.setDeviceType("Feedback");
//        device1.setLinkedPollID(poll1.getPollID());
//
//        IoTDevice device2 = new IoTDevice();
//        device2.setDeviceType("Display");
//        device2.setLinkedPollID(poll2.getPollID());
//
//        em.persist(device1);
//        em.persist(device2);
//    }
//    public static void addNotifications(EntityManager em) {
//
//        Poll poll1 = em.find(Poll.class, 1L);
//        Poll poll2 = em.find(Poll.class, 2L);
//
//        Notification notification1 = new Notification();
//        notification1.setMessage("Poll 'Er det for mye å gjøre i DAT250?' has started!");
//        notification1.setNotificationType("POLL_START");
//        notification1.setPoll(poll1);
//        notification1.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
//
//        Notification notification2 = new Notification();
//        notification2.setMessage("Poll 'Skal du være med ut i helgen?' will end in 1 hour!");
//        notification2.setNotificationType("POLL_END_SOON");
//        notification2.setPoll(poll2);
//        notification2.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
//
//        em.persist(notification1);
//        em.persist(notification2);
//    }
//
//    public static void addAnalytics(EntityManager em) {
//
//        Poll poll1 = em.find(Poll.class, 1L);
//        Poll poll2 = em.find(Poll.class, 2L);
//
//        Analytics analytics1 = new Analytics();
//        analytics1.setLinkedPollID(poll1.getPollID());
//        analytics1.setTotalGreenVotes(3);
//        analytics1.setTotalRedVotes(0);
//        analytics1.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
//
//        Analytics analytics2 = new Analytics();
//        analytics2.setLinkedPollID(poll2.getPollID());
//        analytics2.setTotalGreenVotes(1);
//        analytics2.setTotalRedVotes(2);
//        analytics2.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
//
//        em.persist(analytics1);
//        em.persist(analytics2);
//    }
//}