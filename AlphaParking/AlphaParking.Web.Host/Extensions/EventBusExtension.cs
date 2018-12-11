using AlphaParking.BLL.EventBus.Abstractions;
using AlphaParking.BLL.EventBus.EventHandlers;
using AlphaParking.BLL.EventBus;
using AlphaParking.BLL.EventBus.IntegrationEvents;
using AlphaParking.Web.Host.HostingServices;
using Microsoft.Extensions.DependencyInjection;
using RabbitMQ.Client;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.Extensions
{
    public static class EventBusExtension
    {
        public static void AddEventBus(this IServiceCollection services)
        {
            services.AddSingleton<ConnectionFactory>(s => new ConnectionFactory()
            {
                HostName = "192.168.99.100",
                Port = 5672,
                UserName = "guest",
                Password ="guest",
                VirtualHost = "/"
            });
            services.AddSingleton<EventBus>();
            services.AddScoped<IIntegrationEventHandler<UserCreatedIntegrationEvent>, UserCreatedEventHandler>();
            services.AddHostedService<UserCreatedListenerBackgroundService>();
        }
    }
}
