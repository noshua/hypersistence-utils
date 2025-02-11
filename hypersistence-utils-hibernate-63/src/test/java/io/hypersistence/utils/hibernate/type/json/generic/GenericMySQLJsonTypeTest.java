package io.hypersistence.utils.hibernate.type.json.generic;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.hypersistence.utils.hibernate.type.model.BaseEntity;
import io.hypersistence.utils.hibernate.type.model.Location;
import io.hypersistence.utils.hibernate.type.model.Ticket;
import io.hypersistence.utils.hibernate.util.AbstractMySQLIntegrationTest;
import io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.TypedParameterValue;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Vlad Mihalcea
 */
public class GenericMySQLJsonTypeTest extends AbstractMySQLIntegrationTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Event.class,
            Participant.class
        };
    }

    @Override
    protected String[] packages() {
        return new String[]{
            Location.class.getPackage().getName()
        };
    }

    private Event _event;

    private Participant _participant;

    @Override
    protected void afterInit() {

        doInJPA(entityManager -> {
            Event nullEvent = new Event();
            nullEvent.setId(0L);
            entityManager.persist(nullEvent);

            Location location = new Location();
            location.setCountry("Romania");
            location.setCity("Cluj-Napoca");

            Event event = new Event();
            event.setId(1L);
            event.setLocation(location);
            entityManager.persist(event);

            Ticket ticket = new Ticket();
            ticket.setPrice(12.34d);
            ticket.setRegistrationCode("ABC123");

            Participant participant = new Participant();
            participant.setId(1L);
            participant.setTicket(ticket);
            participant.setEvent(event);

            entityManager.persist(participant);

            _event = event;
            _participant = participant;
        });
    }

    @Test
    public void testLoad() {
        SQLStatementCountValidator.reset();

        doInJPA(entityManager -> {
            Event event = entityManager.find(Event.class, _event.getId());
            assertEquals("Romania", event.getLocation().getCountry());
            assertEquals("Cluj-Napoca", event.getLocation().getCity());
        });

        SQLStatementCountValidator.assertTotalCount(1);
        SQLStatementCountValidator.assertSelectCount(1);
        SQLStatementCountValidator.assertUpdateCount(0);
    }

    @Test
    public void test() {
        doInJPA(entityManager -> {
            Event event = entityManager.find(Event.class, _event.getId());
            assertEquals("Cluj-Napoca", event.getLocation().getCity());

            Participant participant = entityManager.find(Participant.class, _participant.getId());
            assertEquals("ABC123", participant.getTicket().getRegistrationCode());

            List<String> participants = entityManager.createNativeQuery(
                "select p.ticket -> \"$.registrationCode\" " +
                "from participant p " +
                "where JSON_EXTRACT(p.ticket, \"$.price\") > :price ")
            .setParameter("price", 1)
            .getResultList();

            event.getLocation().setCity("Constanța");
            entityManager.flush();

            assertEquals(1, participants.size());
        });
    }

    @Test
    public void testBulkUpdateNativeQuery() {
        doInJPA(entityManager -> {
            Location location = new Location();
            location.setCountry("Romania");
            location.setCity("Sibiu");

            entityManager.createNativeQuery(
                "update event " +
                "set location = :location " +
                "where id = :id")
            .setParameter("id", _event.getId())
            .unwrap(NativeQuery.class)
            .setParameter("location", location, new JsonType(Location.class))
            .executeUpdate();

            Event event = entityManager.find(Event.class, _event.getId());
            assertEquals("Sibiu", event.getLocation().getCity());
        });

        doInJPA(entityManager -> {
            Location location = new Location();
            location.setCountry("Romania");
            location.setCity("Sibiu");

            entityManager.createNativeQuery(
                "update event " +
                "set location = :location " +
                "where id = :id")
            .setParameter("id", _event.getId())
            .unwrap(NativeQuery.class)
            .setParameter("location", new TypedParameterValue<>(new JsonType(Location.class), location))
            .setParameter("location", location)
            .executeUpdate();

            Event event = entityManager.find(Event.class, _event.getId());
            assertEquals("Sibiu", event.getLocation().getCity());
        });
    }

    @Entity(name = "Event")
    @Table(name = "event")
    public static class Event extends BaseEntity {

        @Type(JsonType.class)
        @Column(columnDefinition = "json")
        private Location location;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }

    @Entity(name = "Participant")
    @Table(name = "participant")
    public static class Participant extends BaseEntity {

        @Type(JsonType.class)
        @Column(columnDefinition = "json")
        private Ticket ticket;

        @ManyToOne
        private Event event;

        public Ticket getTicket() {
            return ticket;
        }

        public void setTicket(Ticket ticket) {
            this.ticket = ticket;
        }

        public Event getEvent() {
            return event;
        }

        public void setEvent(Event event) {
            this.event = event;
        }
    }
}
