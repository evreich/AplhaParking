using AlphaParking.BLL.EventBus.Abstractions;
using AlphaParking.Models;

namespace AlphaParking.BLL.EventBus.IntegrationEvents
{
    public class UserEditedIntegrationEvent: IntegrationEvent
    {
        public User OldUser { get; set; }
        public User UpdatedUser { get; set; }
        

        public UserEditedIntegrationEvent(User oldUser, User updatedUser) 
        {
            OldUser = oldUser;
            UpdatedUser = updatedUser;
        }
    }
}