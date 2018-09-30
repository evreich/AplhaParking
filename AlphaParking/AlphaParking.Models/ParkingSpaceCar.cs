using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace AlphaParking.DB.Models
{
    public class ParkingSpaceCar
    {
        [Key]
        public Guid Id { get; set; }

        public int ParkingSpaceNumber { get; set; }
        public ParkingSpace ParkingSpace { get; set; }

        public string CarNumber { get; set; }
        public Car Car { get; set; }

        public DateTime StartParking { get; set; }
        public DateTime EndParking { get; set; }

        public bool IsMainParkingSpace { get; set; }
        public bool CheckIn { get; set; }

        public Guid? TempOwnerParkingSpaceId { get; set; }
        public TempOwnerParkingSpace TempOwnerParkingSpace { get; set; }
    }
}
