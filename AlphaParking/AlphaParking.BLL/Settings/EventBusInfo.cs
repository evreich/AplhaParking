using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Settings
{
    public static class EventBusInfo
    {
        public static readonly string topicExchangeNameAdd = "alphaparking_eventbus_exhange_add";
        public static readonly string queueNameAdd = "alphaparking_eventbus_queue_add";

        public static readonly string topicExchangeNameEdit = "alphaparking_eventbus_exhange_edit";
        public static readonly string queueNameEdit = "alphaparking_eventbus_queue_edit";

        public static readonly string topicExchangeNameDelete = "alphaparking_eventbus_exhange_delete";
        public static readonly string queueNameDelete = "alphaparking_eventbus_queue_delete";
    }
}
