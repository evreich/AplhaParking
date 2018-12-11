using AlphaParking.BLL.EventBus.Abstractions;
using AlphaParking.BLL.EventBus.IntegrationEvents;
using AlphaParking.BLL.Settings;
using AlphaParking.DbContext.Models;
using Newtonsoft.Json;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.EventBus
{
    public class EventBus: IDisposable
    {
        private readonly ConnectionFactory _connectionFactory;
        private readonly IConnection _connection;
        private readonly IModel _channel;

        public EventBus(ConnectionFactory connectionFactory)
        {
            _connectionFactory = connectionFactory;
            _connection = _connectionFactory.CreateConnection();
            _channel = _connection.CreateModel();
        }

        public void Subscribe<T>(IIntegrationEventHandler<T> eventHandler) where T: IntegrationEvent
        {
            _channel.ExchangeDeclare(
                exchange: EventBusInfo.topicExchangeName,
                type: "fanout",
                durable: true
            );
            _channel.QueueDeclare(queue: EventBusInfo.queueName,
                                    durable: true,
                                    exclusive: false,
                                    autoDelete: false,
                                    arguments: null);
            _channel.QueueBind(
                queue: EventBusInfo.queueName,
                exchange: EventBusInfo.topicExchangeName,
                routingKey: ""
            );
            var consumer = new EventingBasicConsumer(_channel);
            consumer.Received += async (model, args) =>
            {
                string message = Encoding.UTF8.GetString(args.Body);
                var @event = JsonConvert.DeserializeObject<T>(message);
                await eventHandler.Handle(@event);
            };
            _channel.BasicConsume(queue: EventBusInfo.queueName,
                                    autoAck: true,
                                    consumer: consumer);
        }

        public void Dispose()
        {
            _channel.Close();
            _connection.Close();
        }
    }
}
