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
    public class EventBus : IDisposable
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

        public void Subscribe<T>(IIntegrationEventHandler<T> eventHandler) where T : IntegrationEvent
        {
            string exchangeName = "";
            string queueName = "";

            if (typeof(T) == typeof(UserCreatedIntegrationEvent))
            {
                exchangeName = EventBusInfo.topicExchangeNameAdd;
                queueName = EventBusInfo.queueNameAdd;
            }
            else
            if (typeof(T) == typeof(UserEditedIntegrationEvent))
            {
                exchangeName = EventBusInfo.topicExchangeNameEdit;
                queueName = EventBusInfo.queueNameEdit;
            }
            else
            if (typeof(T) == typeof(UserRemovedIntegrationEvent))
            {
                exchangeName = EventBusInfo.topicExchangeNameDelete;
                queueName = EventBusInfo.queueNameDelete;
            }

            _channel.ExchangeDeclare(
                exchange: exchangeName,
                    type: "fanout",
                    durable: true
                );
            _channel.QueueDeclare(queue: queueName,
                                    durable: true,
                                    exclusive: false,
                                    autoDelete: false,
                                    arguments: null);
            _channel.QueueBind(
                queue: queueName,
                exchange: exchangeName,
                routingKey: ""
            );
            var consumer = new EventingBasicConsumer(_channel);
            consumer.Received += async (model, args) =>
                {
                    try
                    {
                        string message = Encoding.UTF8.GetString(args.Body);
                        var @event = JsonConvert.DeserializeObject<T>(message);
                        await eventHandler.Handle(@event);
                    }
                    catch (Exception) { }
                };
            _channel.BasicConsume(queue: queueName,
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
