using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.Models.SeedData
{
    public static class CarConstants
    {
        public static Car Solaris { get; } = new Car {
            Number = "A111AA 36",
            Brand ="Hyndai",
            Model ="Solaris",
            UserId = UserConstants.Employee.Id
        };

        public static Car Priora { get; } = new Car {
            Number = "B111AA 36",
            Brand = "LADA",
            Model = "Priora",
            UserId = UserConstants.Manager.Id
        };
    }
}
