using System;
using System.Threading.Tasks;
using AlphaParking.BLL.DTO;
using AlphaParking.BLL.EventBus.Abstractions;
using AlphaParking.BLL.EventBus.IntegrationEvents;
using AlphaParking.DAL;
using AlphaParking.DbContext.Models;
using AlphaParking.Models;
using AutoMapper;
using RabbitMQ.Client.Events;

namespace AlphaParking.BLL.EventBus.EventHandlers
{
    public class UserEditedEventHandler : IIntegrationEventHandler<UserEditedIntegrationEvent>
    {
        private readonly IMapper _mapper;
        private readonly Func<AlphaParkingDbContext> _dbContextFactory;

        public UserEditedEventHandler(Func<AlphaParkingDbContext> dbContextFactory, IMapper mapper)
        {
            _mapper = mapper;
            _dbContextFactory = dbContextFactory;
        }

        public async Task Handle(UserEditedIntegrationEvent @event)
        {
            using (var context = _dbContextFactory())
            {
                context.Users.Update(@event.UpdatedUser);
                await context.SaveChangesAsync();
            }
        }
    }
}