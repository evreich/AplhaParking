using System;
using System.Threading.Tasks;
using System.Linq;
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
                User currentUser = context.Users.Single(user => user.Login == @event.OldUser.Login);

                currentUser.Login = @event.UpdatedUser.Login;
                currentUser.FIO = @event.UpdatedUser.FIO;
                currentUser.Phone = @event.UpdatedUser.Phone;

                context.Users.Update(currentUser);
                await context.SaveChangesAsync();
            }
        }
    }
}