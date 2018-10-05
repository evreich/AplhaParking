using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace AlphaParking.DB.Models
{
    public class TempOwnerParkingSpace
    {
        [Key]
        public Guid Id { get; set; }

        public string CarNumber { get; set; }
        [ForeignKey("CarNumber")]
        public Car Car { get; set; }
        
        public Guid ParkingSpaceCarId { get; set; }
        [ForeignKey("ParkingSpaceCarId")]
        public ParkingSpaceCar ParkingSpaceCar { get; set; }

        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
    }
}
