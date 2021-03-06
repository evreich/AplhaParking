﻿using AlphaParking.BLL.EventBus.Abstractions;
using AlphaParking.BLL.EventBus.EventHandlers;
using AlphaParking.BLL.EventBus;
using AlphaParking.BLL.EventBus.IntegrationEvents;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AlphaParking.DbContext.Models;

namespace AlphaParking.Web.Host.HostingServices
{
    public class UserEditedListenerBackgroundService : BackgroundService
    {
        public UserEditedListenerBackgroundService(IServiceScopeFactory serviceScopeFactory) : base(serviceScopeFactory)
        {
        }

        public override void ProcessInScope(IServiceProvider serviceProvider)
        {
            var listener = serviceProvider.GetService<EventBus>();
            var handler = serviceProvider.GetService<IIntegrationEventHandler<UserEditedIntegrationEvent>>();
            listener.Subscribe<UserEditedIntegrationEvent>(handler);
        }
    }
}
