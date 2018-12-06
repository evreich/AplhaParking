using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.DTO
{
    public class ParkingSpaceCarDTO
    {
        public ParkingSpaceDTO ParkingSpace { get; set; }

        public CarDTO Car { get; set; }

        public TimeSpan StartParkingTime { get; set; }
        public TimeSpan EndParkingTime { get; set; }

        public bool IsMainParkingSpace { get; set; }
        public bool CheckIn { get; set; }
        
        //для возможности временной делеагции парк. места на другую машину
        public CarDTO DelegatedCar { get; set; } = null;
        public DateTime? StartDelegateDate { get; set; }
        public DateTime? EndDelegateDate { get; set; }
    }
}
