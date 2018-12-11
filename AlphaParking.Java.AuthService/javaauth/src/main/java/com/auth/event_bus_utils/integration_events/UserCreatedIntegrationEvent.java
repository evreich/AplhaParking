package com.auth.event_bus_utils.integration_events;

import com.auth.models.User;

public class UserCreatedIntegrationEvent extends IntegrationEvent{
    private User user;

    public UserCreatedIntegrationEvent(User user) {
        this.setUser(user);
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

}