package com.auth.event_bus_utils.integration_events;

import com.auth.models.User;

public class UserEditedIntegrationEvent extends IntegrationEvent {
    private User updatedUser;
    private User oldUser;

    public UserEditedIntegrationEvent(User updatedUser, User oldUser) {
        this.setUpdatedUser(updatedUser);
        this.setOldUser(oldUser);
    }

    /**
     * @return the oldUser
     */
    public User getOldUser() {
        return oldUser;
    }

    /**
     * @param oldUser the oldUser to set
     */
    public void setOldUser(User oldUser) {
        this.oldUser = oldUser;
    }

    /**
     * @return the updatedUser
     */
    public User getUpdatedUser() {
        return updatedUser;
    }

    /**
     * @param updatedUser the updatedUser to set
     */
    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

}