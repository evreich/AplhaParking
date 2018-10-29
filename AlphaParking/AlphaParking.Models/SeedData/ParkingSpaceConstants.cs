﻿using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.DB.Models.SeedData
{
    public static class ParkingSpaceConstants
    {
        public static ParkingSpace ParkingSpaceOne { get; } = new ParkingSpace { Number = 1 };
        public static ParkingSpace ParkingSpaceTwo { get; } = new ParkingSpace { Number = 2 };
    }
}