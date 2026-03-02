package com.example.tickets;

/**
 * Service layer that creates tickets.
 *
 * After refactor:
 * - No mutation
 * - No scattered validation
 * - Updates return NEW instances
 */
public class TicketService {

    public IncidentTicket createTicket(String id,
                                       String reporterEmail,
                                       String title) {

        return IncidentTicket.builder()
                .id(id)
                .reporterEmail(reporterEmail)
                .title(title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .addTag("NEW")
                .build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket ticket) {

        return ticket.toBuilder()
                .priority("CRITICAL")
                .addTag("ESCALATED")
                .build();
    }

    public IncidentTicket assign(IncidentTicket ticket,
                                 String assigneeEmail) {

        return ticket.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}