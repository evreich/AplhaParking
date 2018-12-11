using AlphaParking.BLL.EventBus.Abstractions;
using AlphaParking.Models;

namespace AlphaParking.BLL.EventBus.IntegrationEvents
{
    public class UserCreatedIntegrationEvent: IntegrationEvent
    {
        public User User { get; set; }

        public UserCreatedIntegrationEvent(User user)
            => User = user;
    }
}