using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.ViewModels
{
    public class ParkingSpaceCarViewModel: IViewModel
    {
        public ParkingSpaceViewModel ParkingSpace { get; set; }

        public CarViewModel Car { get; set; }

        public TimeSpan StartParkingTime { get; set; }
        public TimeSpan EndParkingTime { get; set; }

        public bool IsMainParkingSpace { get; set; }
        public bool CheckIn { get; set; }

        //для возможности временной делеагции парк. места на другую машину
        public CarViewModel DelegatedCar { get; set; } = null;
        public DateTime? StartDelegateDate { get; set; }
        public DateTime? EndDelegateDate { get; set; }
    }
}
