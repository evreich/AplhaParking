using AlphaParking.BLL.EventBus.Abstractions;
using AlphaParking.Models;

namespace AlphaParking.BLL.EventBus.IntegrationEvents
{
    public class UserRemovedIntegrationEvent: IntegrationEvent
    {
        public User User { get; set; }

        public UserRemovedIntegrationEvent(User user)
            => User = user;
    }
}