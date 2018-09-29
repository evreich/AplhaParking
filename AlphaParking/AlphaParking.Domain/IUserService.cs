using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Interfaces
{
    interface IUserService
    {
        bool IsRegistered(User user);
        User Registration(User user);
        void Login(User user);
    }
}
