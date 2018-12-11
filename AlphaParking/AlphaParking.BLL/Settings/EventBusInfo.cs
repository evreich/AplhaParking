using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Settings
{
    public static class EventBusInfo
    {
        public static readonly string topicExchangeName = "alphaparking_eventbus_exhange";
	    public static readonly string queueName = "alphaparking_eventbus-queue";
    }
}
