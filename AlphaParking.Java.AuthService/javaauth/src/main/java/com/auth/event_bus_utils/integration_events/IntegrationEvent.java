package com.auth.event_bus_utils.integration_events;

import java.util.Date;
import java.util.UUID;

public class IntegrationEvent
{
    private UUID id;
    private Date creationDate;

    public IntegrationEvent() {
        this.setId(UUID.randomUUID());
        this.setCreationDate(new Date());
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }
}