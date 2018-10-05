using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.DB.Models.SeedData
{
    public static class RoleConstants {
        public const string EMPLOYEE = "Employee";
        public const string MANAGER = "Manager";

        public static Role Manager { get; } = new Role { Id = Guid.NewGuid(), Name = MANAGER };
        public static Role Employee { get; } = new Role { Id = Guid.NewGuid(), Name = EMPLOYEE };
    }
}
