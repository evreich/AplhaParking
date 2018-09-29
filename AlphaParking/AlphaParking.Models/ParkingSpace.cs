using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace AlphaParking.DB.Models
{
    public class ParkingSpace
    {
        [Key]
        public int Number { get; set; }

        public List<ParkingSpaceCars> ParkingSpaceCars { get; set; } = new List<ParkingSpaceCars>();

        public ParkingSpace(int number, List<ParkingSpaceCars> parkingSpaceCars)
        {
            Number = number;
            ParkingSpaceCars = parkingSpaceCars;
        }
    }
}
