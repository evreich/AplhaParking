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
                Id = Guid.NewGuid(),
                FIO = "Smith John Richard",
                Address = "Some address",
                Login = "manager123",
                Password = "manager"
            };
            Manager.Password = hasher.HashPassword(Manager, Manager.Password);

            Employee = new User
            {
                Id = Guid.NewGuid(),
                FIO = "Vlasov Max Sergeevich",
                Address = "Some address2",
                Login = "worker123",
                Password = "worker"
            };
            Employee.Password = hasher.HashPassword(Employee, Employee.Password);
        }
    }
}
