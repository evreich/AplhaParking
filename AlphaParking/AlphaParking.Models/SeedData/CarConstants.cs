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
            UserId = 1
        };
    }
}
