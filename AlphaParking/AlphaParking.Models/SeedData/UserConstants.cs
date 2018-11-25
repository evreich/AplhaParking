using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.DB.Models.SeedData
{
    public static class UserConstants
    {
        public static User Manager { get; }
        public static User Employee { get; }

        static UserConstants()
        {
            var hasher = new PasswordHasher<User>();

            Manager = new User {
                Id = new Guid(1,0,0,new byte[8]),
                FIO = "Smith John Richard",
                Address = "Some address",
                Login = "manager123",
                Password = "manager",
                Phone = "89623288933",
                Email = "vlasov_ms@list.ru"
            };
            Manager.Password = hasher.HashPassword(Manager, Manager.Password);

            Employee = new User
            {
                Id = new Guid(2, 0, 0, new byte[8]),
                FIO = "Vlasov Max Sergeevich",
                Address = "Some address2",
                Login = "worker123",
                Password = "worker",
                Phone = "89623288934",
                Email = "vlasov_ms1@list.ru"
            };
            Employee.Password = hasher.HashPassword(Employee, Employee.Password);
        }
    }
}
