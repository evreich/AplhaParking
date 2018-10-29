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

        public TimeSpan StartParkingTime { get; set; }
        public TimeSpan EndParkingTime { get; set; }

        public bool IsMainParkingSpace { get; set; }
        public bool CheckIn { get; set; }

        //для возможности временной делеагции парк. места на другую машину
        public string DelegatedCarNumber { get; set; }
        public Car DelegatedCar { get; set; }

        public DateTime? StartDelegatedDate { get; set; }
        public DateTime? EndDelegatedDate { get; set; }
    }
}
