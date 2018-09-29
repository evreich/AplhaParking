using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace AlphaParking.DB.Models
{
    public class ParkingSpaceCars
    {
        [Key]
        public int ParkingSpaceNumber { get; set; }
        public ParkingSpace ParkingSpace { get; set; }

        [Key]
        public string CarNumber { get; set; }
        public Car Car { get; set; }

        public DateTime StartParking { get; set; }
        public DateTime EndParking { get; set; }
    }
}
