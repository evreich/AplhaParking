using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.Models.SeedData
{
    public static class CarConstants
    {
        public static Car Solaris { get; } = new Car
        {
            Number = "A111AA 36",
            Brand = "Hyndai",
            Model = "Solaris",
            UserId = 11
        };

        public static Car Accept { get; } = new Car
        {
            Number = "A112AA 36",
            Brand = "Hyndai",
            Model = "Accept",
            UserId = 12
        };

        public static Car Rapid { get; } = new Car
        {
            Number = "A113AA 36",
            Brand = "Skoda",
            Model = "Rapid",
            UserId = 13
        };
    }
}
