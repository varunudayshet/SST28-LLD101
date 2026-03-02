import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Starter demo that shows why mutability is risky.
 *
 * After refactor:
 * - direct mutation should not compile (no setters)
 * - external modifications to tags should not affect the ticket
 * - service "updates" should return a NEW ticket instance
 */
public class TryIt {

public static void main(String[] args) {
    TicketService service = new TicketService();

    IncidentTicket original = service.createTicket(
            "TCK-1001",
            "reporter@example.com",
            "Payment failing on checkout"
    );

    System.out.println("Created: " + original);

    // Updates now return NEW instances
    IncidentTicket assigned = service.assign(original, "agent@example.com");
    IncidentTicket escalated = service.escalateToCritical(assigned);

    System.out.println("\nAfter updates (new object): " + escalated);

    // Demonstrate immutability
    try {
        escalated.getTags().add("HACKED_FROM_OUTSIDE");
    } catch (Exception e) {
        System.out.println("\nTags list is immutable. Cannot modify externally.");
    }

    System.out.println("\nOriginal ticket still unchanged: " + original);
}
}
