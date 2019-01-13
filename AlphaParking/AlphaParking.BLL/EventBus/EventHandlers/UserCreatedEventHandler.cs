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
    public class UserCreatedEventHandler : IIntegrationEventHandler<UserCreatedIntegrationEvent>
    {
        private readonly IMapper _mapper;
        private readonly Func<AlphaParkingDbContext> _dbContextFactory;

        public UserCreatedEventHandler(Func<AlphaParkingDbContext> dbContextFactory, IMapper mapper)
        {
            _mapper = mapper;
            _dbContextFactory = dbContextFactory;
        }

        public async Task Handle(UserCreatedIntegrationEvent @event)
        {
            using (var context = _dbContextFactory())
            {
                await context.AddAsync(@event.User);
                await context.SaveChangesAsync();
            }
        }
    }
}