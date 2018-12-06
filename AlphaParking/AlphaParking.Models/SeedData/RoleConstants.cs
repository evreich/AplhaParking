using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.Models.SeedData
{
    public static class RoleConstants {
        public const string EMPLOYEE = "Employee";
        public const string MANAGER = "Manager";

        public static Role Manager { get; } = new Role { Id = new Guid(1, 0, 0, new byte[8]), Name = MANAGER };
        public static Role Employee { get; } = new Role { Id = new Guid(2, 0, 0, new byte[8]), Name = EMPLOYEE };
    }
}
