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
                HostName = Environment.GetEnvironmentVariable("EVENTBUS_HOST"),
                Port = Int32.Parse(Environment.GetEnvironmentVariable("EVENTBUS_PORT")),
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
