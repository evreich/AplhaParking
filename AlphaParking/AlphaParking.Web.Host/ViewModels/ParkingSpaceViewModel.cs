using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.ViewModels
{
    public class ParkingSpaceViewModel : IViewModel
    {
        public int ParkingSpaceNumber { get; set; }

        List<ParkingSpaceCarViewModel> ParkingSpaceCars { get; set; }
    }
}
